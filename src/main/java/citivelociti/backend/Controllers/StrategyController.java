package citivelociti.backend.Controllers;

import citivelociti.backend.Enums.Status;
import citivelociti.backend.Models.BBStrategy;
import citivelociti.backend.Models.Strategy;
import citivelociti.backend.Models.TMAStrategy;
import citivelociti.backend.Services.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/strategy")
public class StrategyController {

    @Autowired
    StrategyService strategyService;

    @GetMapping(value = "/getById/{id}")
    public @ResponseBody Strategy getAllStrategiesById(@PathVariable Integer id) {
        return strategyService.findById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Strategy> getAllStrategies() {
        return strategyService.findAll();
    }

    @GetMapping(value = "/getAllByType/{type}")
    public @ResponseBody List<Strategy> getAllStrategiesByType(@PathVariable String type) {
        return strategyService.findAllByType(type);
    }

    @GetMapping(value = "/pauseAll")
    public @ResponseBody List<Strategy> pauseAll(){
        List<Strategy> strats = strategyService.findAll();
        for(Strategy strat : strats){
            strat.setStatus(Status.PAUSED);
            strategyService.save(strat);
        }
        return strategyService.findAll();

    }

    @GetMapping(value = "/getAllByStatus/{status}")
    public @ResponseBody List<Strategy> getAllStrategiesByStatus(@PathVariable Status status) {
        return strategyService.findAllByStatus(status);
    }

    @GetMapping(value = "/startById/{id}")
    public @ResponseBody Strategy startStrategyById(@PathVariable int id) {
        return strategyService.startById(id);
    }

    @GetMapping(value = "/stopById/{id}")
    public @ResponseBody Strategy stopStrategyById(@PathVariable int id) {
        return strategyService.stopById(id);
    }

    @PostMapping(value = "/create")
    public Strategy createStrategy(HttpServletRequest request, @RequestBody Map<String, String> payload) {
        String type = payload.get("type");
        String name = payload.get("name");
        String ticker = payload.get("ticker");
        Double quantity = Double.parseDouble(payload.get("quantity"));
        Double limit = Double.parseDouble(payload.get("limit"));
        Double stop = Double.parseDouble(payload.get("stop"));

        if(type.equals("TMAStrategy")) {
            Integer slowAvgIntervale = Integer.parseInt(payload.get("slowAvgIntervale"));
            Integer fastAvgIntervale = Integer.parseInt(payload.get("fastAvgIntervale"));
            TMAStrategy newTMA = new TMAStrategy(name, ticker, quantity, limit, stop, slowAvgIntervale, fastAvgIntervale);
            return strategyService.save(newTMA);
        } else if(type.equals("BBStrategy")) {
            Integer timeSpan = Integer.parseInt(request.getParameter("timeSpan"));
            BBStrategy newBB = new BBStrategy(name, ticker, quantity, limit, stop, timeSpan);
            return strategyService.save(newBB);
        }
        return null;
    }

    @GetMapping(value = "/getTotalPnlById/{id}")
    public Double getTotalPnlById(@PathVariable int id) {

        return strategyService.getTotalPnlById(id);
    }

    
}