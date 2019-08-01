package citivelociti.backend.Repositories;

import citivelociti.backend.Models.Order;
<<<<<<< HEAD
import citivelociti.backend.Enums.OrderStatus;
=======
import citivelociti.backend.Enums.TradeStatus;
>>>>>>> master
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    public Order findById(int Id);

    public void deleteById(Integer id);

    // List of trades given a strategy id
    public List<Order> findAllByStrategyId(Integer strategyId);

<<<<<<< HEAD
    public List<Order> findAllByStrategyIdOrderByDateDesc(Integer strategyId);

=======
>>>>>>> master
    // Returns a list of all buys or sells depending on the parameter.
    public List<Order> findAllByBuy(Boolean buy);


    public List<Order> findAllByOrderByDateAsc();

    public List<Order> findAllByOrderByDateDesc();


    // Returns a list of all the trades of a specific status
<<<<<<< HEAD
    public List<Order> findAllByStatus(OrderStatus status);
=======
    public List<Order> findAllByStatus(TradeStatus status);
>>>>>>> master

    public List<Order> findAll();
}