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

    @Autowired
    StrategyRepo strategyRepo;

    public Trade save(Trade t) {
        return tradeRepo.save(t);
    }

    public Trade findById(int id) {
        return tradeRepo.findById(id);
    }

    public void deleteById(Integer id) {
        tradeRepo.deleteById(id);
    }

    public List<Trade> findAllByStrategyId(Integer strategyId) {
        return tradeRepo.findAllByStrategyId(strategyId);
    }

    public List<Trade> findAllByBuy(Boolean buy) {
        return tradeRepo.findAllByBuy(buy);
    }

    public List<Trade> findAllByOrderByOpenDateAsc() {
        return tradeRepo.findAllByOrderByOpenDateAsc();
    }

    public List<Trade> findAllByOrderByCloseDateAsc() {
        return tradeRepo.findAllByOrderByCloseDateAsc();
    }

    public List<Trade> findAllByOrderByOpenDateDesc() {
        return tradeRepo.findAllByOrderByOpenDateDesc();
    }

    public List<Trade> findAllByOrderByCloseDateDesc() {
        return tradeRepo.findAllByOrderByCloseDateDesc();
    }

    public List<Trade> findAllByStatus(TradeStatus status) {
        return tradeRepo.findAllByStatus(status);
    }

    public List<Trade> findAll() {
        return tradeRepo.findAll();
    }
    
    public Double getProfit(Trade trade) {
        return (trade.getClosePrice() - trade.getOpenPrice()) * strategyRepo.findById(trade.getStrategyId()).getQuantity();
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
        Trade trade = tradeRepo.findById(id);
        if(trade == null) {
            return null;
        } else if (trade.getCloseDate() != null && trade.getStatus() == TradeStatus.OPEN) {
            return getProfit(trade);
        }

        // trade has not been closed. Find the previous closed trade and get that value.
        try {
            return getProfit(tradeRepo.findAllByOrderByCloseDateDesc().get(0));
        } catch(Exception ex) {
            // Need to change the exception later.
            // Exception should be when tradeRepo.findAllByOrderByCloseDateDesc() returns null or get(0) is not valid
            return null;
        }
    }

}

