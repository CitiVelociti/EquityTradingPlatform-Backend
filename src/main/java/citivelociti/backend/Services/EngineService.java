package citivelociti.backend.Services;

import citivelociti.backend.Enums.Position;
import citivelociti.backend.Enums.OrderStatus;
import citivelociti.backend.Enums.Status;
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

    @Scheduled(fixedRate =1000)
    public void checkLimits(){
        List<Strategy> strategies = strategyService.findAll();
        System.out.println(strategies);
        strategies.parallelStream().forEach((strategy) -> {
            if(strategy.getTotalPnlPercent()<= -10.0){
                strategy.setStatus(Status.EXITED);
            } else if(strategy.getTotalPnlPercent() >= strategy.getLimits()){
                strategy.setStatus(Status.EXITED);
            }
            strategyService.save(strategy);
        });

    }
    @Scheduled(fixedRate=100)
    public void runActiveStrats() {
        //Eventually we want to fetch all types of strategies
        activeStrategies = strategyService.findAllByStatus(Status.ACTIVE);
        activeStrategies.parallelStream().forEach((strategy)->{
            //long start = System.currentTimeMillis();
            calculate(strategy);
           // long elapsed = System.currentTimeMillis() - start;
           // System.out.println(elapsed);
        });
    }

    @Async
    public Boolean calculate(Strategy strategy) {
        if(strategy.getType().equals("TMAStrategy")) {
            TMAStrategy tmaStrategy = (TMAStrategy)strategy;
            String ticker = tmaStrategy.getTicker();
            double currentPrice = (double)getCurrentMarketData(ticker, "price");
            String currentTime = (String)getCurrentMarketData(ticker, "time");
            double slowSMAValue = simpleMovingAverage(ticker, tmaStrategy.getSlowAvgIntervale());
            double fastSMAValue = simpleMovingAverage(ticker, tmaStrategy.getFastAvgIntervale());
            System.out.println("Running Strategies... ");

            //Initialize strategy shortBelowOrAbove
            if(tmaStrategy.getShortBelow() == null && slowSMAValue < fastSMAValue) {
                tmaStrategy.setShortBelow(true);
                strategyService.save(tmaStrategy);
            } else if(tmaStrategy.getShortBelow() == null && slowSMAValue > fastSMAValue) {
                tmaStrategy.setShortBelow(false);
                strategyService.save(tmaStrategy);
            }
            if(tmaStrategy.getShortBelow() && (slowSMAValue > fastSMAValue)) {
                tmaStrategy.setShortBelow(false);
                strategy = strategyService.save(tmaStrategy);
                if(strategy.getCurrentPosition() == Position.CLOSED) {
                    Order order = new Order(strategy.getId(), true, currentPrice);
                    order = orderService.save(order);
                    sendMessageToBroker(order.getId(), true, currentPrice, (int) strategy.getQuantity().doubleValue(), ticker, currentTime);
                    strategy.setCurrentPosition(Position.OPEN);
                    strategyService.save(strategy);
                }
                return true;
            } else if(!tmaStrategy.getShortBelow() && (slowSMAValue < fastSMAValue)) {
                tmaStrategy.setShortBelow(true);
                strategyService.save(tmaStrategy);
                if(strategy.getCurrentPosition() == Position.OPEN) {
                    Order order = new Order(strategy.getId(), false, currentPrice);
                    order = orderService.save(order);
                    sendMessageToBroker(order.getId(), false, currentPrice, (int) strategy.getQuantity().doubleValue(), ticker, currentTime);
                    strategy.setCurrentPosition(Position.CLOSED);
                    strategyService.save(strategy);
                }
                //sendMessageToBroker();
                return true;
            }
        }
        return false;
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
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
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
