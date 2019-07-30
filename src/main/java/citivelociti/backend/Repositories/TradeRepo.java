package citivelociti.backend.Repositories;

import citivelociti.backend.Models.Trade;
import citivelociti.backend.Enums.TradeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepo extends JpaRepository<Trade, Integer> {

    public Trade findById(int Id);

    public void deleteById(Integer id);

    // List of trades given a strategy id
    public List<Trade> findAllByStrategyId(Integer strategyId);

    // Returns a list of all buys or sells depending on the parameter.
    public List<Trade> findAllByBuy(Boolean buy);


    public List<Trade> findAllByOrderByOpenDateAsc();

    public List<Trade> findAllByOrderByOpenDateDesc();


    // Returns a list of all the trades of a specific status
    public List<Trade> findAllByStatus(TradeStatus status);

    public List<Trade> findAll();
}