package citivelociti.backend.Controllers;

import citivelociti.backend.Enums.OrderStatus;
import citivelociti.backend.Models.Order;
import citivelociti.backend.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/getById/{id}")
    public @ResponseBody
    Order getAllOrdersById(@PathVariable int id) {
        return orderService.findById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping(value = "/getAllByStrategyId/{strategyId}")
    public @ResponseBody List<Order> getAllOrdersByStrategyId(@PathVariable Integer strategyId) {
        return orderService.findAllByStrategyId(strategyId);
    }

    @GetMapping(value = "/getAllByBuyOrSell/{buy}")
    public @ResponseBody List<Order> getAllOrderByBuyOrSell(@PathVariable Boolean buy) {
        return orderService.findAllByBuy(buy);
    }

    @GetMapping(value = "/getAllByDateAsc")
    public @ResponseBody List<Order> findAllByOrderByDateAsc() {
        return orderService.findAllByOrderByDateAsc();
    }

    @GetMapping(value = "/getAllByDateDesc")
    public @ResponseBody List<Order> getAllByDateDesc() {
        return orderService.findAllByOrderByDateDesc();
    }

    @GetMapping(value = "/getByIdDesc/{id}")
    public @ResponseBody List<Order> getByIdDesc(@PathVariable int id){
        List<Order> orders = orderService.findAllByStrategyId((Integer)id);
        //TODO: CHANGE REVERSE
        //Collections.reverse(orders);
        return orders;
    }
    @GetMapping(value = "/getByIdAsc/{id}")
    public @ResponseBody List<Order> getByIdAsc(@PathVariable int id){
        List<Order> orders = orderService.findAllByStrategyId((Integer)id);
        return orders;
    }

    @GetMapping(value = "/getAllByStatus/{status}")
    public @ResponseBody List<Order> getAllOrdersByStatus(@PathVariable OrderStatus status) {
        return orderService.findAllByStatus(status);
    }
    
}
