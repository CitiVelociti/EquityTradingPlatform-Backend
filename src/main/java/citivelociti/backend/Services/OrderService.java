package citivelociti.backend.Services;

import citivelociti.backend.Models.Order;
import citivelociti.backend.Repositories.StrategyRepo;
import citivelociti.backend.Repositories.OrderRepo;
<<<<<<< HEAD
import citivelociti.backend.Enums.OrderStatus;
=======
import citivelociti.backend.Enums.TradeStatus;
>>>>>>> master
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    StrategyRepo strategyRepo;

    public Order save(Order t) {
        return orderRepo.save(t);
    }

    public Order findById(int id) {
        return orderRepo.findById(id);
    }

    public void deleteById(Integer id) {
        orderRepo.deleteById(id);
    }

    public List<Order> findAllByStrategyId(Integer strategyId) {
        return orderRepo.findAllByStrategyId(strategyId);
    }

<<<<<<< HEAD
    public List<Order> findAllByStrategyIdOrderByDateDesc(Integer strategyId) {
        return findAllByStrategyIdOrderByDateDesc(strategyId);
    }

=======
>>>>>>> master
    public List<Order> findAllByBuy(Boolean buy) {
        return orderRepo.findAllByBuy(buy);
    }

    public List<Order> findAllByOrderByDateAsc() {
        return orderRepo.findAllByOrderByDateAsc();
    }

    public List<Order> findAllByOrderByDateDesc() {
        return orderRepo.findAllByOrderByDateDesc();
    }


<<<<<<< HEAD
    public List<Order> findAllByStatus(OrderStatus status) {
=======
    public List<Order> findAllByStatus(TradeStatus status) {
>>>>>>> master
        return orderRepo.findAllByStatus(status);
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }
<<<<<<< HEAD
=======
    /*
    
    public Double getProfit(Order order) {
        return (order.getClosePrice() - order.getOpenPrice()) * strategyRepo.findById((int) order.getId()).getQuantity();
    }
>>>>>>> master

    /*
     * Calculates the pnl of the trade. If the trade has a close date, the
     * close price will be used to calculate the pnl. Otherwise, method will
     * return the current pnl.
     * 
     * @parameter id    The id of the trade which will determine the pnl
     * @return          
     */
<<<<<<< HEAD
    // public Double getProfitById(int id) {
    //     Order order = orderRepo.findById(id);
    //     if(order == null) {
    //         return null;
    //     } else if (order.getStatus() == OrderStatus.FILLED) {
    //         try {
    //             Order recentOrder = orderRepo.findAllByOrderStrategyIdByDateDesc(order.getStrategyId()).get(0);
    //             return (order.getPrice() - recentOrder.getPrice()) * strategyRepo.findAllById(order.getStrategyId()).getQuantity();
    //         } catch(Exception ex) {
    //             // Need to change the exception later.
    //             // Exception should be when tradeRepo.findAllByOrderByDateDesc() returns null or get(0) is not valid
    //         }
    //     }
    //     return null;
    // }
=======
    /*
    public Double getProfitById(int id) {
        Order order = tradeRepo.findById(id);
        if(order == null) {
            return null;
        } else if (order.getCloseDate() != null && order.getStatus() == TradeStatus.OPEN) {
            return getProfit(order);
        }

        // order has not been closed. Find the previous closed order and get that value.
        try {
            return getProfit(tradeRepo.findAllByOrderByCloseDateDesc().get(0));
        } catch(Exception ex) {
            // Need to change the exception later.
            // Exception should be when tradeRepo.findAllByOrderByCloseDateDesc() returns null or get(0) is not valid
            return null;
        }
    }
    */
>>>>>>> master

}

