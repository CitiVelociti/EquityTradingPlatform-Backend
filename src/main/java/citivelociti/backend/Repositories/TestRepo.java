package citivelociti.backend.Repositories;

import citivelociti.backend.Models.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo extends JpaRepository<TestModel, Integer> {
}
