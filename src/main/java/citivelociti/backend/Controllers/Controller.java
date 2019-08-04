package citivelociti.backend.Controllers;

import citivelociti.backend.Models.BBStrategy;
import citivelociti.backend.Models.TMAStrategy;
import citivelociti.backend.Services.StrategyService;
import citivelociti.backend.Services.OrderService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    StrategyService strategyService;

    @Autowired
    OrderService orderService;

    private static final Logger LOGGER = Logger.getLogger(ServiceImpl.class);

    @RequestMapping("/")
    public String helloWorld(HttpServletResponse response) {
        List<String> symbols = getSymbols();
        for(int i = 0 ; i < 15; i++) {
            TMAStrategy newTMA = new TMAStrategy("TMA Strategy: " + i, symbols.get((int) (Math.random() * (525 - 0))), 500.0, 5.0, 5.0, 1, 10);
            BBStrategy newBB = new BBStrategy("Bollinger Strategy: " + i, symbols.get((int) (Math.random() * (525 - 0))), 500.0, 5.0, 5.0, 20);

            strategyService.save(newTMA);
            strategyService.save(newBB);
        }
        LOGGER.info("TESTING LOG");
        //orderService.save(t);
        return "Hello World!";
    }

    public List<String> getSymbols() {
        ArrayList<String> symbols = new ArrayList<String>();
        String response = "";
        try {
            URL url = new URL("http://nyc31.conygre.com:31/Stock/getSymbolList");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                response = inputLine;
            }
            in.close();
            con.disconnect();
        } catch(Exception e) {}
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
            symbols.add(jsonObject.get("symbol").toString());
        }
        return symbols;
    }
    
}
