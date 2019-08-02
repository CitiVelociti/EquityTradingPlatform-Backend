package citivelociti.backend.Services;

import citivelociti.backend.Enums.Position;
import citivelociti.backend.Enums.OrderStatus;
import citivelociti.backend.Enums.Status;
import citivelociti.backend.Models.BBStrategy;
import citivelociti.backend.Models.Order;
import citivelociti.backend.Models.Strategy;
import citivelociti.backend.Models.TMAStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.MessageCreator;

@Service
public class EngineService {

    @Autowired
    StrategyService strategyService;

    @Autowired
    OrderService orderService;

    @Autowired
    private JmsTemplate jmsTemplate;

    private List<Strategy> activeStrategies;

    @Scheduled(fixedRate =100)
    public void checkLimits(){
        List<Strategy> strategies = strategyService.findAll();
        strategies.parallelStream().forEach((strategy) -> {
            if(strategy.getTotalPnlPercent()<= -10.0){
                strategy.setStatus(Status.EXITED);
            } else if(strategy.getTotalPnlPercent() >= strategy.getLimits()){
                strategy.setStatus(Status.EXITED);
            }
            strategyService.save(strategy);
        });

    }
    @Scheduled(fixedRate=500)
    public void runActiveStrats() {
        //Eventually we want to fetch all types of strategies
        activeStrategies = strategyService.findAllByStatus(Status.ACTIVE);
        activeStrategies.parallelStream().forEach((strategy)->{
            calculate(strategy);
        });
    }

    @Scheduled(fixedRate = 100)
    public void closeAllPausedTrades(){
        List<Strategy> pausedStrategies = strategyService.findAllByStatus(Status.PAUSED);
        pausedStrategies.parallelStream().forEach((strategy)->{
            if(strategy.getCurrentPosition() == Position.OPEN){
                String currentTime = (String)getCurrentMarketData(strategy.getTicker(), "time");
                double currentPrice = (double)getCurrentMarketData(strategy.getTicker(), "price");
                Order order = new Order(strategy.getId(), false, currentPrice);
                order = orderService.save(order);
                sendMessageToBroker(order.getId(), false, currentPrice, (int) strategy.getQuantity().doubleValue(),strategy.getTicker(), currentTime);
                strategy.setCurrentPosition(Position.CLOSED);
                strategyService.save(strategy);
            }
        });
    }



    @Async
    public void calculate(Strategy strategy) {
        if(strategy.getType().equals("TMAStrategy")) {
            TMAStrategy tmaStrategy = (TMAStrategy)strategy;
            initializeInitialBalance(tmaStrategy);
            doTMACalculation(tmaStrategy);
        } else if (strategy.getType().equals("BBStrategy")){
            BBStrategy bbStrategy = (BBStrategy)strategy;
            initializeInitialBalance(bbStrategy);
            doBBCalculation(bbStrategy);
        }
    }
    public void doBBCalculation(BBStrategy bbStrategy){
        int timeSpan = bbStrategy.getTimeSpan();
        String ticker = bbStrategy.getTicker();
        double sma = simpleMovingAverage(ticker, timeSpan);
        double standardDeviation = standardDeviation(valuesOverTimeSpan(ticker, timeSpan));
        standardDeviation *= bbStrategy.getStd();
        double top_bb = sma + standardDeviation;
        double bottom_bb = sma - standardDeviation;
        double currentPrice = (double)getCurrentMarketData(ticker, "price");
        System.out.println("TOPBB:" + top_bb);
        System.out.println("cur:" +currentPrice);
        System.out.println("BOTTOMBB:" +bottom_bb);
        if(currentPrice > top_bb && bbStrategy.getCurrentPosition() == Position.CLOSED){
            System.out.println("PRICE BELOW STD, BUY");
            makeOrderAndPingBroker(bbStrategy, true);
        } else if (currentPrice < bottom_bb && bbStrategy.getCurrentPosition() == Position.OPEN){
            System.out.println("PRICE OVER STD, SELL");
            makeOrderAndPingBroker(bbStrategy, false);

        }


    }
    void initializeInitialBalance(Strategy strategy){
        double currentPrice = (double)getCurrentMarketData(strategy.getTicker(), "price");
        if(strategy.getInitialCapital() == null){
            strategy.setInitialCapital(currentPrice*strategy.getQuantity());
            strategyService.save(strategy);
        }
    }
    static double variance(double a[],
                           int n)
    {
        // Compute mean (average
        // of elements)
        double sum = 0;

        for (int i = 0; i < n; i++)
            sum += a[i];
        double mean = (double)sum /
                (double)n;

        // Compute sum squared
        // differences with mean.
        double sqDiff = 0;
        for (int i = 0; i < n; i++)
            sqDiff += (a[i] - mean) *
                    (a[i] - mean);

        return (double)sqDiff / n;
    }
    static double standardDeviation(double arr[])
    {
        int n = arr.length;
        return Math.sqrt(variance(arr, n));
    }
    public static double calculateSD(double numArray[])
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;
        for(double num : numArray) {
            sum += num;
        }
        double mean = sum/length;
        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation/length);
    }
    public void doTMACalculation(TMAStrategy tmaStrategy){
        String ticker = tmaStrategy.getTicker();
        Strategy bufferStrategy;
        double slowSMAValue = simpleMovingAverage(ticker, tmaStrategy.getSlowAvgIntervale());
        double fastSMAValue = simpleMovingAverage(ticker, tmaStrategy.getFastAvgIntervale());
        initializeTMABooleans(tmaStrategy, slowSMAValue, fastSMAValue);
        if(tmaStrategy.getShortBelow() && (slowSMAValue > fastSMAValue)) {
            tmaStrategy.setShortBelow(false);
             bufferStrategy = strategyService.save(tmaStrategy);
            if(bufferStrategy.getCurrentPosition() == Position.CLOSED) {
                makeOrderAndPingBroker(tmaStrategy, true);
            }
        } else if(!tmaStrategy.getShortBelow() && (slowSMAValue < fastSMAValue)) {
            tmaStrategy.setShortBelow(true);
            bufferStrategy = strategyService.save(tmaStrategy);
            if(bufferStrategy.getCurrentPosition() == Position.OPEN) {
                makeOrderAndPingBroker(tmaStrategy, false);
            }
        }
    }

    public void initializeTMABooleans(TMAStrategy tmaStrategy, double slowSMAValue, double fastSMAValue){
        if(tmaStrategy.getShortBelow() == null && slowSMAValue < fastSMAValue) {
            tmaStrategy.setShortBelow(true);
            strategyService.save(tmaStrategy);
        } else if(tmaStrategy.getShortBelow() == null && slowSMAValue > fastSMAValue) {
            tmaStrategy.setShortBelow(false);
            strategyService.save(tmaStrategy);
        }
    }

    public void makeOrderAndPingBroker(Strategy strategy, Boolean buy){

        String ticker = strategy.getTicker();
        double currentPrice = (double)getCurrentMarketData(ticker, "price");
        String currentTime = (String)getCurrentMarketData(ticker, "time");
        Order order = new Order(strategy.getId(), buy, currentPrice);
        order = orderService.save(order);
        sendMessageToBroker(order.getId(), buy, currentPrice, (int) strategy.getQuantity().doubleValue(), ticker, currentTime);
        if(buy){
            strategy.setCurrentPosition(Position.OPEN);
        } else { strategy.setCurrentPosition(Position.CLOSED); }

        strategyService.save(strategy);

    }

    public double simpleMovingAverage(String ticker, int interval) {
        String url = "http://nyc31.conygre.com:31/Stock/getStockPriceList/" + ticker + "?howManyValues=" + interval;
        String response = requestData(url);
        JSONArray jsonArray = new JSONArray(response);
        double smaSum = 0.0;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
            smaSum += Double.parseDouble(jsonObject.get("price").toString());
        }
        return smaSum/interval;
    }

    public double[] valuesOverTimeSpan(String ticker, int interval) {
        String url = "http://nyc31.conygre.com:31/Stock/getStockPriceList/" + ticker + "?howManyValues=" + interval;
        String response = requestData(url);
        JSONArray jsonArray = new JSONArray(response);
        double[] values = new double[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
            values[i] = Double.parseDouble(jsonObject.get("price").toString());
        }
        return values;
    }

    public Object getCurrentMarketData(String ticker, String dataField) {
        String response = requestData("http://nyc31.conygre.com:31/Stock/getStockPrice/" + ticker);
        JSONObject jsonObject = new JSONObject(response);
        if(dataField.equals("price")) {
            return Double.parseDouble(jsonObject.get("price").toString());
        } else if(dataField.equals("time")) {
            return jsonObject.get("theTime").toString();
        }
        return null;
    }

    public String requestData(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response = inputLine;
            }
            in.close();
            con.disconnect();
        } catch(Exception e) {

        }
        return response;
    }

    public void sendMessageToBroker(int tradeId, boolean buy, double price, int size, String stock, String whenAsDate) {
        int correlationID = tradeId;
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setBoolean("buy",buy);
                message.setString("stock", stock);
                message.setDouble("price",price);
                message.setInt("size",size);
                message.setString("whenAsDate", whenAsDate);
                message.setJMSCorrelationID(correlationID + "");
                return message;
            }
        };
        jmsTemplate.send("OrderBroker", messageCreator);

    }
}
