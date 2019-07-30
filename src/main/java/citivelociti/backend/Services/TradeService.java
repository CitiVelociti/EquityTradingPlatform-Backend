package citivelociti.backend.Services;

import citivelociti.backend.Models.Trade;
import citivelociti.backend.Repositories.TradeRepo;
import citivelociti.backend.Enums.TradeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    @Autowired
    TradeRepo tradeRepo;

    public void save(Trade t) {
        tradeRepo.save(t);
    }

    public Trade findById(int id) {
        return tradeRepo.findById(id);
    }

    // public void deleteById(Integer id) {
    //     tradeRepo.deleteById(id);
    // }

    public List<Trade> findAllByStrategyId(Integer strategyId) {
        return tradeRepo.findAllByStrategyId(strategyId);
    }

    public List<Trade> findAllByBuy(Boolean buy) {
        return tradeRepo.findAllByBuy(buy);
    }

    public List<Trade> findAllByOpenDateByDateDesc() {
        return tradeRepo.findAllByOpenDateByDateDesc();
    }

    public List<Trade> findAllByCloseDateByDateDesc() {
        return tradeRepo.findAllByCloseDateByDateDesc();
    }

    public List<Trade> findAllByStatus(TradeStatus status) {
        return tradeRepo.findAllByStatus(status);
    }

    public List<Trade> findAll() {
        return tradeRepo.findAll();
    }
    
}

