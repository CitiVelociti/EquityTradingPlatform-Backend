package citivelociti.backend.JMS;

import citivelociti.backend.Enums.OrderStatus;
import citivelociti.backend.Models.Order;
import citivelociti.backend.Models.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.io.File;
import java.util.Calendar;

@Component
public class OrderBroker {
    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = "OrderBroker", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(Message message) {
        try {
            System.out.println("======= YOUR ORDER  RECEIVED ========");
            MapMessage mapMessage = (MapMessage)message;
            Boolean buy = mapMessage.getBoolean("buy");
            Double price = mapMessage.getDouble("price");
            Integer size = mapMessage.getInt("size");
            String stock = mapMessage.getString("stock");
            String whenAsDate = mapMessage.getString("whenAsDate");
            int correlationID = Integer.parseInt(mapMessage.getJMSCorrelationID());

            System.out.println(buy);
            System.out.println(price);
            System.out.println(price);
            System.out.println(size);
            sendMessageBack(correlationID, buy, price,  size,  stock, whenAsDate);
            //PUT THIS BACK IN LATER WHEN GET MOCKED RESPONSE FROM BROKER
            // if(result.equals("FILLED")){


        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void sendMessageBack(int correlationID, boolean buy, double price, int size, String stock, String whenAsDate){
        System.out.println("SEND A NEW MESSAGE");
        jmsTemplate.send("OrderBroker_Reply", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                MapMessage message = session.createMapMessage();
                message.setBoolean("buy",buy);
                message.setDouble("price",price);
                message.setInt("size",size);
                message.setString("stock", stock);
                message.setString("whenAsDate", whenAsDate);
                message.setJMSCorrelationID(correlationID + "");
                message.setString("result", "FILLED");
                return message;
            }
        });
    }
}
