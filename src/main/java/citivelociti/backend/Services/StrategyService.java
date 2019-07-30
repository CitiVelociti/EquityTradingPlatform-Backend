package citivelociti.backend.Services;

import citivelociti.backend.Enums.Status;
import citivelociti.backend.Models.Strategy;
import citivelociti.backend.Repositories.StrategyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyService {

    @Autowired
    StrategyRepo strategyRepo;

    public void save(Strategy s) {
        strategyRepo.save(s);
    }

    public Strategy findById(int id) {
        return strategyRepo.findById(id);
    }

    // public void deleteById(Integer id) {
    //     strategyRepo.deleteById(id);
    // }

    public List<Strategy> findAllByType(String type) {
        return strategyRepo.findAllByType(type);
    }

    public List<Strategy> findAllByStatus(Status status) {
        return strategyRepo.findAllByStatus(status);
    }

    public List<Strategy> findAll() {
        return strategyRepo.findAll();
    }

    /*
     * If the status is paused, then pause it? do you mean active instead?
     */
    public Strategy startById(int id) {
        Strategy strategy = strategyRepo.findById(id);
        if (strategy.getStatus() == Status.PAUSED) {
            strategy.setStatus(Status.PAUSED);
            strategyRepo.save(strategy);
        }
        return strategy;
    }

    public Strategy stopById(int id) {
        Strategy strategy = strategyRepo.findById(id);
        strategy.setStatus(Status.PAUSED);
        strategyRepo.save(strategy);
        return strategy;
    }

}
