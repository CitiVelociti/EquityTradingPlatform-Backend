package citivelociti.backend.Controllers;

import citivelociti.backend.Models.TestModel;
// import citivelociti.backend.Models.Strategy;
// import citivelociti.backend.Models.Trade;

import citivelociti.backend.Services.TestService;
// import citivelociti.backend.Services.StrategyService;
// import citivelociti.backend.Services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    TestService testService;
    // @Autowired
    // StrategyService strategyService;
    // @Autowired
    // TradeService tradeService;

    @RequestMapping("/")
    public String helloWorld(){
        TestModel test = new TestModel("aasdfas");
        // Strategy s = new Strategy("GOOG",5,5,5);
        // Trade t = new Trade(s.getId(), true, 5);

        testService.save(test);
        // strategyService.save(s);
        return "helloWorld";
    }
}
