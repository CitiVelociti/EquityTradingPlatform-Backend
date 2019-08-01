package citivelociti.backend.JMS;

import java.io.File;
import java.util.Calendar;

import citivelociti.backend.Enums.Position;
import citivelociti.backend.Enums.OrderStatus;
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
public class OrderListener {

    @Autowired
    OrderService orderService;

    @Autowired
    StrategyService strategyService;

    @JmsListener(destination = "OrderBroker_Reply", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage)message;

            System.out.println("======= BROKER REPLY RECEIVED ========");
            System.out.println(mapMessage.getString("result"));

            //PUT THIS BACK IN LATER WHEN GET MOCKED RESPONSE FROM BROKER
           // if(result.equals("FILLED")){
            if(mapMessage.getString("result").equals("FILLED")){
                Order order = orderService.findById(Integer.parseInt(mapMessage.getJMSCorrelationID()));
                order.setStatus(OrderStatus.FILLED);
                orderService.save(order);
                Strategy strategy = strategyService.findById(order.getStrategyId());
                if(!order.getBuy()){

                    //TRADE CLOSED
                    double pnl = orderService.getProfitById(order.getId());
                    order.setPnl(pnl);
                    orderService.save(order);
                    Strategy strat = strategyService.findById(order.getStrategyId());
                    strat.addPnl(pnl);
                    strat.setTotalPnlPercent(strat.getTotalPnl()/strat.getInitialCapital()*100);
                    //Double balance = strat.getInitialCapital() + pnl;
                    //strat.setInitialCapital(balance);
                    strategyService.save(strat);
                }

            } else if (!mapMessage.getString("result").equals("REJECTED")){
                Order order = orderService.findById(Integer.parseInt(mapMessage.getJMSCorrelationID()));
                order.setPrice(mapMessage.getDouble("price"));
                order.setDate(Calendar.getInstance().getTime());
                order.setStatus(OrderStatus.REJECTED);
                orderService.save(order);
            }

            mapMessage.getString("result");
            FileSystemUtils.deleteRecursively(new File("activemq-data"));
        } catch (Exception e) {

        }
    }
}