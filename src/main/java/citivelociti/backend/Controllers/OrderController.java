package citivelociti.backend.Controllers;

<<<<<<< HEAD
import citivelociti.backend.Enums.OrderStatus;
=======
import citivelociti.backend.Enums.TradeStatus;
>>>>>>> master
import citivelociti.backend.Models.Order;
import citivelociti.backend.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
<<<<<<< HEAD
@RequestMapping("/order")
=======
@RequestMapping("/trade")
>>>>>>> master
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/getById/{id}")
    public @ResponseBody
<<<<<<< HEAD
    Order getAllOrderById(@PathVariable int id) {
=======
    Order getAllTradesById(@PathVariable int id) {
>>>>>>> master
        return orderService.findById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
<<<<<<< HEAD
    public @ResponseBody List<Order> getAllOrders() {
=======
    public @ResponseBody List<Order> getAllTrades() {
>>>>>>> master
        return orderService.findAll();
    }

    @GetMapping(value = "/getAllByStrategyId/{strategyId}")
<<<<<<< HEAD
    public @ResponseBody List<Order> getAllOrdersByStrategyId(@PathVariable Integer strategyId) {
=======
    public @ResponseBody List<Order> getAllTradesByStrategyId(@PathVariable Integer strategyId) {
>>>>>>> master
        return orderService.findAllByStrategyId(strategyId);
    }

    @GetMapping(value = "/getAllByBuyOrSell/{buy}")
<<<<<<< HEAD
    public @ResponseBody List<Order> getAllOrderByBuyOrSell(@PathVariable Boolean buy) {
        return orderService.findAllByBuy(buy);
    }

    @GetMapping(value = "/getAllByDateAsc")
=======
    public @ResponseBody List<Order> getAllTradeByBuyOrSell(@PathVariable Boolean buy) {
        return orderService.findAllByBuy(buy);
    }

    @GetMapping(value = "/getAllByOpenDateAsc")
>>>>>>> master
    public @ResponseBody List<Order> findAllByOrderByDateAsc() {
        return orderService.findAllByOrderByDateAsc();
    }

    @GetMapping(value = "/getAllByDateDesc")
<<<<<<< HEAD
    public @ResponseBody List<Order> getAllByDateDesc() {
=======
    public @ResponseBody List<Order> getAllByOpenDateDesc() {
>>>>>>> master
        return orderService.findAllByOrderByDateDesc();
    }

    @GetMapping(value = "/getAllByStatus/{status}")
<<<<<<< HEAD
    public @ResponseBody List<Order> getAllOrdersByStatus(@PathVariable OrderStatus status) {
=======
    public @ResponseBody List<Order> getAllTradesByStatus(@PathVariable TradeStatus status) {
>>>>>>> master
        return orderService.findAllByStatus(status);
    }
    
}
