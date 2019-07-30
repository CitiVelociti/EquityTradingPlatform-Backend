package citivelociti.backend.Controllers;

import citivelociti.backend.Enums.Status;
import citivelociti.backend.Models.Strategy;
import citivelociti.backend.Services.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/strategy")
public class StrategyController {

    @Autowired
    StrategyService strategyService;

    @GetMapping(value = "/getById/{id}")
    public @ResponseBody
    Strategy getAllStrategiesById(@PathVariable int id) {
        return strategyService.findById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Strategy> getAllStrategies() {
        return strategyService.findAll();
    }

    @GetMapping(value = "/getAllByType/{type}")
    public @ResponseBody
    List<Strategy> getAllStrategiesByType(@PathVariable String type) {
        return strategyService.findAllByType(type);
    }

    @GetMapping(value = "/getAllByStatus/{status}")
    public @ResponseBody
    List<Strategy> getAllStrategiesByStatus(@PathVariable Status status) {
        return strategyService.findAllByStatus(status);
    }

    @PostMapping(value = "/create")
    public String createStrategy() {
        return "";
    }

    @GetMapping(value = "/startById/{id}")
    public @ResponseBody
    Strategy startStrategyById(@PathVariable int id) {
        return strategyService.startById(id);
    }

    @GetMapping(value = "/stopById/{id}")
    public @ResponseBody
    Strategy stopStrategyById(@PathVariable int id) {
        return strategyService.stopById(id);
    }

}