package citivelociti.backend.Controllers;

import citivelociti.backend.Enums.TradeStatus;
import citivelociti.backend.Models.Trade;
import citivelociti.backend.Services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    TradeService tradeService;

    @GetMapping(value = "/getById/{id}")
    public @ResponseBody Trade getAllTradesById(@PathVariable int id) {
        return tradeService.findById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trade> getAllTrades() {
        return tradeService.findAll();
    }

    @GetMapping(value = "/getAllByStrategyId/{strategyId}")
    public @ResponseBody List<Trade> getAllTradesByStrategyId(@PathVariable Integer strategyId) {
        return tradeService.findAllByStrategyId(strategyId);
    }

    @GetMapping(value = "/getAllByBuyOrSell/{buy}")
    public @ResponseBody List<Trade> getAllTradeByBuyOrSell(@PathVariable Boolean buy) {
        return tradeService.findAllByBuy(buy);
    }

    @PostMapping(value = "/create")
    public Trade createStrategy(HttpServletRequest request, @RequestBody Map<String, String> payload) {

        // int strategyId, boolean buy, double price

        String type = payload.get("type");
        String name = payload.get("name");
        String ticker = payload.get("ticker");
        Double quantity = Double.parseDouble(payload.get("quantity"));
        Double limit = Double.parseDouble(payload.get("limit"));
        Double stop = Double.parseDouble(payload.get("stop"));

        // if(type.equals("TMAStrategy")) {
        //     Integer slowAvgIntervale = Integer.parseInt(payload.get("slowAvgIntervale"));
        //     Integer fastAvgIntervale = Integer.parseInt(payload.get("fastAvgIntervale"));
        //     TMAStrategy newTMA = new TMAStrategy(name, ticker, quantity, limit, stop, slowAvgIntervale, fastAvgIntervale);
        //     return strategyService.save(newTMA);
        // } else if(type.equals("BBStrategy")) {
        //     Integer timeSpan = Integer.parseInt(request.getParameter("timeSpan"));
        //     BBStrategy newBB = new BBStrategy(name, ticker, quantity, limit, stop, timeSpan);
        //     return strategyService.save(newBB);
        // }
        return null;
    }

    @GetMapping(value = "/getAllByOpenDateAsc")
    public @ResponseBody List<Trade> findAllByOrderByOpenDateAsc() {
        return tradeService.findAllByOrderByOpenDateAsc();
    }

    @GetMapping(value = "/getAllByCloseDateAsc")
    public @ResponseBody List<Trade> findAllByOrderByCloseDateAsc() {
        return tradeService.findAllByOrderByCloseDateAsc();
    }

    @GetMapping(value = "/getAllByOpenDateDesc")
    public @ResponseBody List<Trade> getAllByOpenDateDesc() {
        return tradeService.findAllByOrderByOpenDateDesc();
    }

    @GetMapping(value = "/getAllByCloseDateDesc")
    public @ResponseBody List<Trade> getAllByCloseDateDesc() {
        return tradeService.findAllByOrderByCloseDateDesc();
    }

    @GetMapping(value = "/getAllByStatus/{status}")
    public @ResponseBody List<Trade> getAllTradesByStatus(@PathVariable TradeStatus status) {
        return tradeService.findAllByStatus(status);
    }

}
