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

    @GetMapping(value = "/getById/{id}")
    public @ResponseBody
    Trade getAllTradesById(@PathVariable int id) {
        return tradeService.findById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Trade> getAllTrades() {
        return tradeService.findAll();
    }

    @GetMapping(value = "/getAllByStrategyId/{strategyId}")
    public @ResponseBody
    List<Trade> getAllTradesByStrategyId(@PathVariable Integer strategyId) {
        return tradeService.findAllByStrategyId(strategyId);
    }

    @GetMapping(value = "/getAllByBuyOrSell/{buy}")
    public @ResponseBody
    List<Trade> getAllTradeByBuyOrSell(@PathVariable Boolean buy) {
        return tradeService.findAllByBuy(buy);
    }

    @GetMapping(value = "/getAllByOpenDateAsc")
    public @ResponseBody
    List<Trade> findAllByOpenDateByDateAsc() {
        return tradeService.findAllByOpenDateByDateAsc();
    }

    @GetMapping(value = "/getAllByCloseDateAsc")
    public @ResponseBody
    List<Trade> findAllByCloseDateByDateAsc() {
        return tradeService.findAllByCloseDateByDateAsc();
    }

    @GetMapping(value = "/getAllByOpenDateDesc")
    public @ResponseBody
    List<Trade> getAllTradeByOpenDateDesc() {
        return tradeService.findAllByOpenDateByDateDesc();
    }

    @GetMapping(value = "/getAllByCloseDateDesc")
    public @ResponseBody
    List<Trade> getAllTradeByCloseDateDesc() {
        return tradeService.findAllByCloseDateByDateDesc();
    }

    @GetMapping(value = "/getAllByStatus/{status}")
    public @ResponseBody
    List<Trade> getAllTradesByStatus(@PathVariable TradeStatus status) {
        return tradeService.findAllByStatus(status);
    }

}
