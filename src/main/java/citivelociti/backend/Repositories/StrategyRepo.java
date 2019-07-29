package citivelociti.backend.Repositories;

import citivelociti.backend.Models.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrategyRepo extends JpaRepository<Strategy, Integer> {

    Strategy findById(int Id);

    List<Strategy> findAllByType(String type);
}
