package citivelociti.backend.Controllers;

import citivelociti.backend.Models.TMAStrategy;
import citivelociti.backend.Services.StrategyService;
import citivelociti.backend.Services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class Controller {

    @Autowired
    StrategyService strategyService;

    @Autowired
    OrderService orderService;

    @RequestMapping("/")
    public String helloWorld(HttpServletResponse response) {

        for(int i = 0 ; i < 30; i++) {
            TMAStrategy newTMA = new TMAStrategy("Strategy: " + i, "GOOG", 5.0, 5.0, 5.0, 1, 10);
            strategyService.save(newTMA);
        }

       // BBStrategy newBB = new BBStrategy("My bollinger strat", "AAPL", 5.0, 5.0, 5.0, 2);
        //Strategy s = strategyService.save(newTMA);
        //Order t = new Order(s.getId(), true, 5);
       // strategyService.save(newBB);
        //orderService.save(t);

        return "Hello World!";
    }
    
}
