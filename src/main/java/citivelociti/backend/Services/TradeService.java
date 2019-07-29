package citivelociti.backend.Services;

import citivelociti.backend.Models.Trade;
import citivelociti.backend.Repositories.TradeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService {
    @Autowired
    TradeRepo tradeRepo;

    public void save(Trade t){
        tradeRepo.save(t);
    }
}

