package citivelociti.backend.Services;

import citivelociti.backend.Enums.Status;
import citivelociti.backend.Models.Order;
import citivelociti.backend.Models.Strategy;
import citivelociti.backend.Repositories.OrderRepo;
import citivelociti.backend.Repositories.StrategyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class StrategyService {

    @Autowired
    StrategyRepo strategyRepo;

    @Autowired
    OrderRepo orderRepo;

    public Strategy save(Strategy s) {
        return strategyRepo.save(s);
    }

    public Strategy findById(int id) {
        return strategyRepo.findById(id);
    }

    // public void deleteById(Integer id) {
    //     strategyRepo.deleteById(id);
    // }

    public List<Strategy> findAllByType(String type) {
        return strategyRepo.findAllByType(type);
    }

    public List<Strategy> findAllByStatus(Status status) {
        return strategyRepo.findAllByStatus(status);
    }

    public List<Strategy> findAll() {
        return strategyRepo.findAll();
    }

    public Strategy startById(int id) {
        Strategy strategy = strategyRepo.findById(id);
        if (strategy.getStatus() == Status.PAUSED) {
            strategy.setStatus(Status.ACTIVE);
            strategyRepo.save(strategy);
        }
        return strategy;
    }

    public Strategy stopById(int id) {
        Strategy strategy = strategyRepo.findById(id);
        strategy.setStatus(Status.PAUSED);
        strategyRepo.save(strategy);
        return strategy;
    }

    public Double getTotalPnlById(int id) {
        double totalPnl = 0;
        List <Order> orders = orderRepo.findAllByStrategyId(id);
        for(Order order: orders){
            if(!order.getBuy()){
                totalPnl += order.getPnl();
            }
        }
        return totalPnl;
    }
}
