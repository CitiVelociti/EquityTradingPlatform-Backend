package citivelociti.backend.Controllers;

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




    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Strategy> getAllStrategies()
    {

        return strategyService.findAll();

    }





    @RequestMapping(value = "/getAllByStatus", method = RequestMethod.GET)
    public String getAllStrategiesByStatus()
    {
        return "";
    }

    @RequestMapping(value = "/getById/{Id}", method = RequestMethod.GET)
    public @ResponseBody Strategy getAllStrategiesById(@PathVariable int Id)
    {
        return strategyService.findById(Id);
    }


    @RequestMapping(value = "/getAllByType/{type}", method = RequestMethod.GET)
    public @ResponseBody List<Strategy> getAllStrategiesById(@PathVariable String type)
    {
        return strategyService.findAllByType(type);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createStrategy()
    {

        return "";
    }

    @RequestMapping(value = "/stopById", method = RequestMethod.GET)
    public String stopStrategyById()
    {
        return "";
    }


}
