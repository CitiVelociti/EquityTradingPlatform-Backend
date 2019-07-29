package citivelociti.backend.Controllers;


import citivelociti.backend.Models.TestModel;
import citivelociti.backend.Services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    TestService testService;

    @RequestMapping("/")
    public String helloWorld(){
        TestModel test = new TestModel("aasdfas");
        testService.save(test);
        return "helloWorld";

    }



}
