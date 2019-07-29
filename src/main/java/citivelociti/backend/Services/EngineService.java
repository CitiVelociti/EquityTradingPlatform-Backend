package citivelociti.backend.Services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class EngineService {


    @Scheduled(fixedRate=1000)
    public void readFeed() {

        //System.out.println("1s");
        try{
            URL url = new URL("http://nyc31.conygre.com:31/Stock/getStockPrice/msft");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            con.disconnect();
        }catch(Exception e){}
    }
}
