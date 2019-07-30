package citivelociti.backend.Controllers;

<<<<<<< HEAD
=======
import citivelociti.backend.Models.BBStrategy;
import citivelociti.backend.Models.Strategy;
>>>>>>> master
import citivelociti.backend.Models.TMAStrategy;
import citivelociti.backend.Models.Trade;
import citivelociti.backend.Services.StrategyService;
import citivelociti.backend.Services.TradeService;
<<<<<<< HEAD

=======
>>>>>>> master
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class Controller {

    @Autowired
    StrategyService strategyService;

    @Autowired
    TradeService tradeService;

    @RequestMapping("/")
<<<<<<< HEAD
    public String helloWorld() {
        TMAStrategy newTMA = new TMAStrategy("GOOG", 5.0, 5.0, 5.0, 1, 10);
        strategyService.save(newTMA);

        Trade t = new Trade(newTMA.getId(), true, Math.floor(Math.random()*5));
        tradeService.save(t);

        return "Hello world";
=======
    public String helloWorld(HttpServletResponse response) {
        TMAStrategy newTMA = new TMAStrategy("My new Strat", "GOOG", 5.0, 5.0, 5.0, 1, 10);

        BBStrategy newBB = new BBStrategy("My bollinger strat", "AAPL", 5.0, 5.0, 5.0, 2);
        Strategy s = strategyService.save(newTMA);
        Trade t = new Trade(s.getId(), true, 5);
        strategyService.save(newBB);
        tradeService.save(t);

        return "Added Dummy Strategies";
>>>>>>> master
    }
}
