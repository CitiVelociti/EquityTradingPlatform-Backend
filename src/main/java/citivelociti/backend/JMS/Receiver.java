package citivelociti.backend.JMS;

import java.io.File;
import java.util.Enumeration;

import citivelociti.backend.Models.OrderTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import javax.jms.Message;

@Component
public class Receiver {


    /**
     * When you receive a message, print it out, then shut down the application.
     * Finally, clean up any ActiveMQ server stuff.
     */
    @JmsListener(destination = "brokerReplyListener", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(Message message) {
        System.out.println(message.toString());
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }
}