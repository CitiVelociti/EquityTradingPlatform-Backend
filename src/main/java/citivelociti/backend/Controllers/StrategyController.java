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
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/strategy")
public class StrategyController {

    @Autowired
    StrategyService strategyService;

    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    @GetMapping(value = "/getById/{id}")
    public @ResponseBody Strategy getAllStrategiesById(@PathVariable Integer id) {
        LOGGER.info("Get strategy by id: " + id);
        return strategyService.findById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Strategy> getAllStrategies() {
        // LOGGER.info("Get all strategy");
        return strategyService.findAll();
    }

    @GetMapping(value = "/getAllByType/{type}")
    public @ResponseBody List<Strategy> getAllStrategiesByType(@PathVariable String type) {
        LOGGER.info("Get all strategy by type: " + type);
        return strategyService.findAllByType(type);
    }

    @GetMapping(value = "/pauseAll")
    public @ResponseBody List<Strategy> pauseAll() {
        LOGGER.info("Pause all strategy");
        List<Strategy> strats = strategyService.findAll();
        for(Strategy strat : strats) {
            strat.setStatus(Status.PAUSED);
            strategyService.save(strat);
        }
        return strats;
    }

    @GetMapping(value = "/startAll")
    public @ResponseBody List<Strategy> startAll() {
        LOGGER.info("Start all strategy");
        List<Strategy> strats = strategyService.findAll();
        for(Strategy strat : strats) {
            strat.setStatus(Status.ACTIVE);
            strategyService.save(strat);
        }
        return strats;
    }

    @GetMapping(value = "/getAllByStatus/{status}")
    public @ResponseBody List<Strategy> getAllStrategiesByStatus(@PathVariable Status status) {
        LOGGER.info("Get all strategy by status: " + status);
        return strategyService.findAllByStatus(status);
    }

    @GetMapping(value = "/startById/{id}")
    public @ResponseBody Strategy startStrategyById(@PathVariable int id) {
        LOGGER.info("Start strategy by id: " + id);
        return strategyService.startById(id);
    }

    @GetMapping(value = "/stopById/{id}")
    public @ResponseBody Strategy stopStrategyById(@PathVariable int id) {
        LOGGER.info("Stop strategy by id: " + id);
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

        Strategy newStrat = null;
        if(type.equals("TMAStrategy")) {
            Integer slowAvgIntervale = Integer.parseInt(payload.get("slowAvgIntervale"));
            Integer fastAvgIntervale = Integer.parseInt(payload.get("fastAvgIntervale"));
            newStrat = new TMAStrategy(name, ticker, quantity, limit, stop, slowAvgIntervale, fastAvgIntervale);
            LOGGER.info("CREATED A STRATEGY");
        } else if(type.equals("BBStrategy")) {
            Integer timeSpan = Integer.parseInt(payload.get("timeSpan"));
            newStrat = new BBStrategy(name, ticker, quantity, limit, stop, timeSpan);
            LOGGER.info("CREATED A STRATEGY");
        }
        return strategyService.save(newStrat);
    }

    @GetMapping(value = "/getTotalPnlById/{id}")
    public Double getTotalPnlById(@PathVariable int id) {
        LOGGER.info("Get total pnl by id: " + id);
        return strategyService.getTotalPnlById(id);
    }
}