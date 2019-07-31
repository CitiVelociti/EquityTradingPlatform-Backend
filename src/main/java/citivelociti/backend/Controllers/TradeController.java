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
@CrossOrigin(origins = "*")
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
