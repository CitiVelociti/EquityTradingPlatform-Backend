package citivelociti.backend.Repositories;

import citivelociti.backend.Enums.Status;
import citivelociti.backend.Models.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrategyRepo extends JpaRepository<Strategy, Integer> {

    public Strategy findById(int id);

    public void deleteById(Integer id);

    public List<Strategy> findAllByType(String type);

    public List<Strategy> findAllByStatus(Status status);

    public List<Strategy> findAll();
}
