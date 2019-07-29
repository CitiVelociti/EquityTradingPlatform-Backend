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

    @GetMapping(value = "/getById/{Id}")
    public @ResponseBody Strategy getAllStrategiesById(@PathVariable int Id)
    {
        return strategyService.findById(Id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Strategy> getAllStrategies()
    {

        return strategyService.findAll();

    }

    @GetMapping(value = "/getAllByType/{type}")
    public @ResponseBody List<Strategy> getAllStrategiesById(@PathVariable String type)
    {
        return strategyService.findAllByType(type);
    }

    @GetMapping(value = "/getAllByStatus/{status}")
    public @ResponseBody List<Strategy> getAllStrategiesByStatus(@PathVariable("status") Status status)
    {
        return strategyService.findAllByStatus(status);
    }

    @PostMapping(value = "/create")
    public String createStrategy()
    {

        return "";
    }

    @GetMapping(value = "/stopById")
    public String stopStrategyById()
    {
        return "";
    }


}
