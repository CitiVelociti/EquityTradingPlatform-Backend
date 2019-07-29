package citivelociti.backend.Services;

import citivelociti.backend.Models.Strategy;
import citivelociti.backend.Repositories.StrategyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StrategyService {

    @Autowired
    StrategyRepo strategyRepo;

    public Strategy addNewStrategy(Strategy strat) {
        return strategyRepo.save(strat);
    }
}
