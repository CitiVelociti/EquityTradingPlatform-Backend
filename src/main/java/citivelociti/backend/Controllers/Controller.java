package citivelociti.backend.Controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {



    @RequestMapping("/")
    public String helloWorld(){
        // Strategy s = new Strategy("GOOG",5,5,5);
        // Trade t = new Trade(s.getId(), true, 5);

        // strategyService.save(s);
        return "helloWorld";
    }
}
