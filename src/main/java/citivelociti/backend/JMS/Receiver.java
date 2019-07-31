package citivelociti.backend.JMS;

import java.io.File;
import java.util.Enumeration;

import citivelociti.backend.Enums.TradeStatus;
import citivelociti.backend.Models.OrderTransaction;
import citivelociti.backend.Models.Trade;
import citivelociti.backend.Services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import javax.jms.MapMessage;
import javax.jms.Message;

@Component
public class Receiver {

    @Autowired
    TradeService tradeService;

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
            if(result.equals("FILLED")){
                Trade trade = tradeService.findById(Integer.parseInt(mapMessage.getJMSCorrelationID()));
                trade.setStatus(TradeStatus.FILLED);
                tradeService.save(trade);
            }
            mapMessage.getString("result");
            FileSystemUtils.deleteRecursively(new File("activemq-data"));
        } catch (Exception e) {

        }
    }
}