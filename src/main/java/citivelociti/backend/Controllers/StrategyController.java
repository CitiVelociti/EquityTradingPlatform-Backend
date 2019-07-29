package citivelociti.backend.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/strategies")
public class StrategyController {


    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public String getAllStrategies()
    {
        return "";
    }

    @RequestMapping(value = "/getAllByStatus", method = RequestMethod.GET)
    public String getAllStrategiesByStatus()
    {
        return "";
    }

    @RequestMapping(value = "/getAllById", method = RequestMethod.GET)
    public String getAllStrategiesById()
    {
        return "";
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
