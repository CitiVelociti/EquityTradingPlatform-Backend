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

    public List<Strategy> findAll()
    {
        return strategyRepo.findAll();
    }
    public void save(Strategy s){
        strategyRepo.save(s);
    }


    public Strategy findById(int Id) {
        return strategyRepo.findById(Id);
    }

    public List<Strategy> findAllByType(String type){
        return strategyRepo.findAllByType(type);
    }

    public List<Strategy> findAllByStatus(Status status) {
        return strategyRepo.findAllByStatus(status);
    }
}
