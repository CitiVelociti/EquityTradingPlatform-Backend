package citivelociti.backend.Services;

import citivelociti.backend.Enums.Position;
import citivelociti.backend.Models.OrderTransaction;
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
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.MessageCreator;

@Service
public class EngineService {


    @Autowired
    StrategyService strategyService;

    @Autowired
    private JmsTemplate jmsTemplate;

    private List<Strategy> activeStrategies;

    @Scheduled(fixedRate=1000)
    public void readFeed() {
        //Eventually we want to fetch all types of strategies
        activeStrategies = strategyService.findAllByType("TMAStrategy");

        activeStrategies.parallelStream().forEach((strategy)->{
            Boolean signal = calculate(strategy);


            if(signal && strategy.getCurrentPosition() == Position.CLOSED) {
                System.out.println("OPEN THE POSITION");
                strategy.setCurrentPosition(Position.OPEN);
                strategyService.save(strategy);
            } else if(signal && strategy.getCurrentPosition() == Position.OPEN) {
                System.out.println("CLOSE THE POSITION");
                strategy.setCurrentPosition(Position.CLOSED);
                strategyService.save(strategy);
            }
        });
    }

    @Async
    public Boolean calculate(Strategy strategy) {
        if(strategy.getType().equals("TMAStrategy")) {
            TMAStrategy tmaStrategy = (TMAStrategy)strategy;
            double slowSMAValue = simpleMovingAverage(tmaStrategy.getTicker(), tmaStrategy.getSlowAvgIntervale());
            double fastSMAValue = simpleMovingAverage(tmaStrategy.getTicker(), tmaStrategy.getFastAvgIntervale());
            System.out.println("Checking Strategy " + strategy.getName() + ":");
            System.out.println("Slow SMA: " + slowSMAValue);
            System.out.println("Fast SMA: " + fastSMAValue);
            sendMessage();

            //Initialize strategy shortBelowOrAbove
            if(tmaStrategy.getShortBelow() == null && slowSMAValue < fastSMAValue){
                tmaStrategy.setShortBelow(true);
                strategyService.save(strategy);
            } else if(tmaStrategy.getShortBelow() == null && slowSMAValue > fastSMAValue){
                tmaStrategy.setShortBelow(false);
                strategyService.save(strategy);
            }

            if(tmaStrategy.getShortBelow() && (slowSMAValue > fastSMAValue)){
                tmaStrategy.setShortBelow(false);
                strategyService.save(strategy);
                System.out.println("Signal: true");
                return true;
            } else if(!tmaStrategy.getShortBelow() && (slowSMAValue < fastSMAValue)) {
                tmaStrategy.setShortBelow(true);
                strategyService.save(strategy);
                System.out.println("Signal: true");
                return true;
            }

        }
        System.out.println("Signal: false");
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

    public String requestData(String urlString){
        //urlString = "http://nyc31.conygre.com:31/Stock/getStockPriceList/msft?howManyValues=100";
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
        } catch(Exception e){}
        return response;
    }



        public void sendMessage(){
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(new OrderTransaction(true, 2, 2.0,2,"ap","date"));
            }
        };
        //System.out.println("Sending a new message.");
        jmsTemplate.send("brokerReplyListener", messageCreator);

    }
    /*
    @Scheduled(fixedRate=1000)
    public String requestData(){

        String urlString = "http://nyc31.conygre.com:31/Stock/getStockPriceList/msft?howManyValues=100";
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
                JSONArray jsonArray = new JSONArray(response);
                for (Object obj:jsonArray) {
                    JSONObject jsonObject = new JSONObject(obj.toString());
                    System.out.println(jsonObject.get("price").toString());
                }
            }
            in.close();
            con.disconnect();
        } catch(Exception e){

        }
        return response;
    }
    */

}
