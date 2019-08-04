package citivelociti.backend.Controllers;

import citivelociti.backend.Enums.OrderStatus;
import citivelociti.backend.Models.Order;
import citivelociti.backend.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    @GetMapping(value = "/getById/{id}")
    public @ResponseBody
    Order getAllOrdersById(@PathVariable int id) {
        LOGGER.info("Get order by id: " + id);
        return orderService.findById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Order> getAllOrders() {
        LOGGER.info("Get all order");
        return orderService.findAll();
    }

    @GetMapping(value = "/getAllByStrategyId/{strategyId}")
    public @ResponseBody List<Order> getAllOrdersByStrategyId(@PathVariable Integer strategyId) {
        LOGGER.info("Get all orders by strategy id: " + strategyId);
        return orderService.findAllByStrategyId(strategyId);
    }

    @GetMapping(value = "/getAllByBuyOrSell/{buy}")
    public @ResponseBody List<Order> getAllOrderByBuyOrSell(@PathVariable Boolean buy) {
        LOGGER.info("Get all orders by buy|sell: " + buy);
        return orderService.findAllByBuy(buy);
    }

    @GetMapping(value = "/getAllByDateAsc")
    public @ResponseBody List<Order> findAllByOrderByDateAsc() {
        LOGGER.info("Get all orders by date ascending");
        return orderService.findAllByOrderByDateAsc();
    }

    @GetMapping(value = "/getAllByDateDesc")
    public @ResponseBody List<Order> getAllByDateDesc() {
        LOGGER.info("Get all orders by date descending");
        return orderService.findAllByOrderByDateDesc();
    }

    @GetMapping(value = "/getByIdDesc/{id}")
    public @ResponseBody List<Order> getByIdDesc(@PathVariable int id) {
        // LOGGER.info("Get all orders by id descending");
        List<Order> orders = orderService.findAllByStrategyId((Integer)id);
        //TODO: CHANGE REVERSE
        //Collections.reverse(orders);
        return orders;
    }

    @GetMapping(value = "/getByIdAsc/{id}")
    public @ResponseBody List<Order> getByIdAsc(@PathVariable int id) {
        LOGGER.info("Get all orders by id ascending");
        List<Order> orders = orderService.findAllByStrategyId((Integer)id);
        return orders;
    }

    @GetMapping(value = "/getAllByStatus/{status}")
    public @ResponseBody List<Order> getAllOrdersByStatus(@PathVariable OrderStatus status) {
        LOGGER.info("Get all orders by status: " + status);
        return orderService.findAllByStatus(status);
    }
    
}
