package citivelociti.backend.Controllers;

import citivelociti.backend.Enums.TradeStatus;
import citivelociti.backend.Models.Trade;
import citivelociti.backend.Services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    TradeService tradeService;

    @GetMapping(value = "/getTradesById/{id}")
    public @ResponseBody
    Trade getAllTradesById(@PathVariable int id) {
        return tradeService.findById(id);
    }

    @GetMapping(value = "/getAllTrades", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Trade> getAllTrades() {
        return tradeService.findAll();
    }

    @GetMapping(value = "/getAllTradesByStrategyId/{strategyId}")
    public @ResponseBody
    List<Trade> getAllTradesByStrategyId(@PathVariable Integer strategyId) {
        return tradeService.findAllByStrategyId(strategyId);
    }

    @GetMapping(value = "/getAllTradesByBuyOrSell/{buy}")
    public @ResponseBody
    List<Trade> getAllTradeByBuyOrSell(@PathVariable Boolean buy) {
        return tradeService.findAllByBuy(buy);
    }


    @GetMapping(value = "/getAllTradesByOpenDate")
    public @ResponseBody
    List<Trade> getAllTradeByOpenDate() {
        return tradeService.findAllByOpenDateByDateDesc();
    }

    @GetMapping(value = "/getAllTradesByCloseDate")
    public @ResponseBody
    List<Trade> getAllTradeByCloseDate() {
        return tradeService.findAllByCloseDateByDateDesc();
    }


    @GetMapping(value = "/getAllTradesByStatus/{status}")
    public @ResponseBody
    List<Trade> getAllTradesByStatus(@PathVariable TradeStatus status) {
        return tradeService.findAllByStatus(status);
    }

}
