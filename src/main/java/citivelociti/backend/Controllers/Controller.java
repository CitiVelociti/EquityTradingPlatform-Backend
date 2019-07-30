package citivelociti.backend.Controllers;

import citivelociti.backend.Models.BBStrategy;
import citivelociti.backend.Models.Strategy;
import citivelociti.backend.Models.TMAStrategy;
import citivelociti.backend.Services.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class Controller {

    @Autowired
    StrategyService strategyService;

    @RequestMapping("/")
    public String helloWorld(HttpServletResponse response) {
        TMAStrategy newTMA = new TMAStrategy("My new Strat", "GOOG", 5.0, 5.0, 5.0, 1, 10);
        // Trade t = new Trade(s.getId(), true, 5);
        BBStrategy newBB = new BBStrategy("My bollinger strat", "AAPL", 5.0, 5.0, 5.0, 2);
        strategyService.save(newTMA);
        strategyService.save(newBB);

        return "Added Dummy Strategies";
    }
}
