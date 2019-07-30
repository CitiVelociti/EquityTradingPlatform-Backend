package citivelociti.backend.Controllers;

import citivelociti.backend.Models.TMAStrategy;
import citivelociti.backend.Models.Trade;
import citivelociti.backend.Services.StrategyService;
import citivelociti.backend.Services.TradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    StrategyService strategyService;

    @Autowired
    TradeService tradeService;

    @RequestMapping("/")
    public String helloWorld() {
        TMAStrategy newTMA = new TMAStrategy("GOOG", 5.0, 5.0, 5.0, 1, 10);
        strategyService.save(newTMA);

        Trade t = new Trade(newTMA.getId(), true, Math.floor(Math.random()*5));
        tradeService.save(t);

        return "Hello world";
    }
}
