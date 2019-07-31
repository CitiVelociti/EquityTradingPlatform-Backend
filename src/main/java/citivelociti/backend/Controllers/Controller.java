package citivelociti.backend.Controllers;

import citivelociti.backend.Models.BBStrategy;
import citivelociti.backend.Models.Strategy;
import citivelociti.backend.Models.TMAStrategy;
import citivelociti.backend.Models.Trade;
import citivelociti.backend.Services.StrategyService;
import citivelociti.backend.Services.TradeService;

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
    public String helloWorld(HttpServletResponse response) {

        for(int i = 0 ; i < 30; i++) {
            TMAStrategy newTMA = new TMAStrategy("Strategy: " + i, "GOOG", 5.0, 5.0, 5.0, 1, 10);
            strategyService.save(newTMA);
        }

       // BBStrategy newBB = new BBStrategy("My bollinger strat", "AAPL", 5.0, 5.0, 5.0, 2);
        //Strategy s = strategyService.save(newTMA);
        //Trade t = new Trade(s.getId(), true, 5);
       // strategyService.save(newBB);
        //tradeService.save(t);

        return "Hello World!";
    }
    
}
