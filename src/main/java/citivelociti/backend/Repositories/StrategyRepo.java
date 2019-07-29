package citivelociti.backend.Repositories;

import citivelociti.backend.Models.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepo extends JpaRepository<Strategy, Integer> {
}
