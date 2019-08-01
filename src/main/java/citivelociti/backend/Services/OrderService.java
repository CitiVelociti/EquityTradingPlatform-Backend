package citivelociti.backend.Services;

import citivelociti.backend.Models.Order;
import citivelociti.backend.Models.Strategy;
import citivelociti.backend.Repositories.StrategyRepo;
import citivelociti.backend.Repositories.OrderRepo;
import citivelociti.backend.Enums.OrderStatus;
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

    public List<Order> findAllByStrategyIdOrderByDateDesc(Integer strategyId) {
        return findAllByStrategyIdOrderByDateDesc(strategyId);
    }

    public List<Order> findAllByBuy(Boolean buy) {
        return orderRepo.findAllByBuy(buy);
    }

    public List<Order> findAllByOrderByDateAsc() {
        return orderRepo.findAllByOrderByDateAsc();
    }

    public List<Order> findAllByOrderByDateDesc() {
        return orderRepo.findAllByOrderByDateDesc();
    }


    public List<Order> findAllByStatus(OrderStatus status) {
        return orderRepo.findAllByStatus(status);
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    /*
     * Calculates the pnl of the trade. If the trade has a close date, the
     * close price will be used to calculate the pnl. Otherwise, method will
     * return the current pnl.
     * 
     * @parameter id    The id of the trade which will determine the pnl
     * @return          
     */
    public Double getProfitById(int id) {
        Order order = orderRepo.findById(id);
        if(order == null) {
            return null;
        } else if (order.getStatus() == OrderStatus.FILLED) {
            try {
                Order recentOrder = orderRepo.findAllByStrategyIdOrderByDateDesc(order.getStrategyId()).get(1);;
                return (order.getPrice() - recentOrder.getPrice()) * strategyRepo.findById(order.getStrategyId()).get().getQuantity();
            } catch(Exception ex) {
                // Need to change the exception later.
                // Exception should be when tradeRepo.findAllByOrderByDateDesc() returns null or get(0) is not valid
            }
        }
        return null;
    }

}

