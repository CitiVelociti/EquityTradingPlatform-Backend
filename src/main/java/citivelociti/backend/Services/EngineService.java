package citivelociti.backend.Services;

import citivelociti.backend.Enums.Position;
import citivelociti.backend.Enums.OrderStatus;
import citivelociti.backend.Enums.Status;
import citivelociti.backend.Models.BBStrategy;
import citivelociti.backend.Models.Order;
import citivelociti.backend.Models.Strategy;
import citivelociti.backend.Models.TMAStrategy;

import org.graalvm.compiler.nodes.ReturnNode;
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
import java.util.logging.Logger;

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
    private static final Logger LOGGER = Logger.getLogger(EngineService.class.getName());

    /*
     * Checks (every second) to see if the limits were reached. In Strategy object, there is a limit & stop
     * which checks when to stop the strategy. If the stop is reached, you are losing money
     * and do not want to lose anymore. If the limit is reached, you are making a profit
     * (hopefully the max).
     */
    @Scheduled(fixedRate=100)
    public void checkLimits() {
        List<Strategy> strategies = strategyService.findAll();
        strategies.parallelStream().forEach((strategy)->{
            if(strategy.getTotalPnlPercent()*100 <= -10.0 || strategy.getTotalPnlPercent()*100 >= strategy.getLimits()) {
                strategy.setStatus(Status.EXITED);
                strategyService.save(strategy);
                // LOGGER.info("BUY/SELL");
            }
        });
    }

    /* ###################################################################################################
     * 
     ################################################################################################### */
     public String requestData(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

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

    public double simpleMovingAverage(String ticker, int interval) {
        String url = "http://nyc31.conygre.com:31/Stock/getStockPriceList/" + ticker + "?howManyValues=" + interval;
        String response = requestData(url);
        JSONArray jsonArray = new JSONArray(response);
        double smaSum = 0.0;
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
            smaSum += Double.parseDouble(jsonObject.get("price").toString());
        }
        return smaSum/interval;
    }

    /*
     * Initialzes the boolean value in TMAStrategy depending on the specified SMAValue and fastSMAValue
     */
    public void initializeTMABooleans(TMAStrategy tmaStrategy, double slowSMAValue, double fastSMAValue){
        if(tmaStrategy.getShortBelow() == null && slowSMAValue < fastSMAValue) {
            tmaStrategy.setShortBelow(true);
            strategyService.save(tmaStrategy);
        } else if(tmaStrategy.getShortBelow() == null && slowSMAValue > fastSMAValue) {
            tmaStrategy.setShortBelow(false);
            strategyService.save(tmaStrategy);
        }
    }

    public Object getCurrentMarketData(String ticker, String dataField) {
        String response = requestData("http://nyc31.conygre.com:31/Stock/getStockPrice/" + ticker);
        JSONObject jsonObject;
        jsonObject = new JSONObject(response);
        if(dataField.equals("price")) {
            return Double.parseDouble(jsonObject.get("price").toString());
        } else if(dataField.equals("time")) {
            return jsonObject.get("theTime").toString();
        }
        return null;
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

    public void makeOrderAndPingBroker(Strategy strategy, Boolean buy){
        String ticker = strategy.getTicker();
        String currentTime = (String)getCurrentMarketData(ticker, "time");
        double currentPrice = (double)getCurrentMarketData(ticker, "price");
        Order order = new Order(strategy.getId(), buy, currentPrice);
        order = orderService.save(order);
        sendMessageToBroker(order.getId(), buy, currentPrice, (int) strategy.getQuantity().doubleValue(), ticker, currentTime);
        strategy.setCurrentPosition(buy?Position.OPEN:Position.CLOSED);
        strategyService.save(strategy);
    }

    /*
     * Does the calculation for Two moving averages strategy.
     * @tmaStrategy
     */
    public void doTMACalculation(TMAStrategy tmaStrategy) {
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

    static double mean(double a[], int n) {
        double sum = 0.0;
        for(int i = 0; i < n; i++)
            sum += a[i];
        return (double)sum / n;
    }

    static double variance(double a[], int n) {
        double mean = mean(a, n);

        // Compute sum squared differences with mean.
        double sqDiff = 0;
        for(int i = 0; i < n; i++)
            sqDiff += (a[i] - mean) * (a[i] - mean);
        return (double)sqDiff / n;
    }

    static double standardDeviation(double arr[]) {
        int n = arr.length;
        return Math.sqrt(variance(arr, n));
    }

    public void doBBCalculation(BBStrategy bbStrategy) {
        int timeSpan = bbStrategy.getTimeSpan();
        String ticker = bbStrategy.getTicker();
        double sma = simpleMovingAverage(ticker, timeSpan);
        double standardDeviation = standardDeviation(valuesOverTimeSpan(ticker, timeSpan));
        standardDeviation *= bbStrategy.getStd();
        double top_bb = sma + standardDeviation;
        double bottom_bb = sma - standardDeviation;
        double currentPrice = (double)getCurrentMarketData(ticker, "price");

        if(currentPrice > top_bb && bbStrategy.getCurrentPosition() == Position.CLOSED) {
            LOGGER.info("PRICE BELOW STD, BUY");
            makeOrderAndPingBroker(bbStrategy, true);
        } else if(currentPrice < bottom_bb && bbStrategy.getCurrentPosition() == Position.OPEN) {
            LOGGER.info("PRICE OVER STD, SELL");
            makeOrderAndPingBroker(bbStrategy, false);
        }
    }

    void initializeInitialBalance(Strategy strategy){
        double currentPrice = (double)getCurrentMarketData(strategy.getTicker(), "price");
        if(strategy.getInitialCapital() == null) {
            strategy.setInitialCapital(currentPrice*strategy.getQuantity());
            strategyService.save(strategy);
        }
    }
    
    /*
     * Checks to see which strategy to calculate
     *
     * @stategy         Strategy object which we want to find the type of
     * @precondition    strategy must be active.
     */
    @Async
    public void calculate(Strategy strategy) {
        if(strategy.getType().equals("TMAStrategy")) {
            TMAStrategy tmaStrategy = (TMAStrategy)strategy;
            initializeInitialBalance(tmaStrategy);
            doTMACalculation(tmaStrategy);
        } else if(strategy.getType().equals("BBStrategy")) {
            BBStrategy bbStrategy = (BBStrategy)strategy;
            initializeInitialBalance(bbStrategy);
            doBBCalculation(bbStrategy);
        }
    }

    /*
     * Checks (every 5 seconds) for all active strategies and calculate the profits.
     */
    @Scheduled(fixedRate=500)
    public void runActiveStrats() {
        //Eventually we want to fetch all types of strategies
        activeStrategies = strategyService.findAllByStatus(Status.ACTIVE);
        activeStrategies.parallelStream().forEach((strategy)->{
            calculate(strategy);
        });
    }

    /* ###################################################################################################
    * 
    ################################################################################################### */
    @Scheduled(fixedRate=100)
    public void closeAllPausedTrades() {
        List<Strategy> pausedStrategies = strategyService.findAllByStatus(Status.PAUSED);
        pausedStrategies.parallelStream().forEach((strategy)->{
            if(strategy.getCurrentPosition() == Position.OPEN) {
                makeOrderAndPingBroker(strategy, false);
            }
        });
    }
}
