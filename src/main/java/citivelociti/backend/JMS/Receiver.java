package citivelociti.backend.JMS;

import java.io.File;
import java.util.Calendar;

import citivelociti.backend.Enums.Position;
import citivelociti.backend.Enums.TradeStatus;
import citivelociti.backend.Models.Order;
import citivelociti.backend.Models.Strategy;
import citivelociti.backend.Services.OrderService;
import citivelociti.backend.Services.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import javax.jms.MapMessage;
import javax.jms.Message;

@Component
public class Receiver {

    @Autowired
    OrderService orderService;

    @Autowired
    StrategyService strategyService;

    @JmsListener(destination = "OrderBroker_Reply", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage)message;
            System.out.println("======= TRADE MESSAGE RECEIVED ========");
            System.out.println(mapMessage.getString("buy"));
            System.out.println(mapMessage.getString("price"));
            System.out.println(mapMessage.getString("stock"));
            System.out.println(mapMessage.getString("whenAsDate"));
            System.out.println(mapMessage.getJMSCorrelationID());
            String result = mapMessage.getString("result");
            //PUT THIS BACK IN LATER WHEN GET MOCKED RESPONSE FROM BROKER
           // if(result.equals("FILLED")){
            if(mapMessage.getBoolean("buy")){
                Order order = orderService.findById(Integer.parseInt(mapMessage.getJMSCorrelationID()));
                order.setStatus(TradeStatus.FILLED);
                orderService.save(order);

                Strategy strategy = strategyService.findById(order.getStrategyId());

                /*
                if(strategy.getCurrentPosition() == Position.CLOSED){
                    strategy.setCurrentPosition(Position.OPEN);
                    strategyService.save(strategy);
                } else if (strategy.getCurrentPosition() == Position.OPEN){
                    strategy.setCurrentPosition(Position.CLOSED);
                    strategyService.save(strategy);
                }*/

            } else if (!mapMessage.getBoolean("buy")){
                Order order = orderService.findById(Integer.parseInt(mapMessage.getJMSCorrelationID()));
                order.setPrice(mapMessage.getDouble("price"));
                order.setDate(Calendar.getInstance().getTime());
                order.setStatus(TradeStatus.FILLED);
                orderService.save(order);
            }
            mapMessage.getString("result");
            FileSystemUtils.deleteRecursively(new File("activemq-data"));
        } catch (Exception e) {

        }
    }
}