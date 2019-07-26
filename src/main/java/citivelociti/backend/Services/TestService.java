package citivelociti.backend.Services;

import citivelociti.backend.Models.TestModel;
import citivelociti.backend.Repositories.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    TestRepo testRepo;

    public void save(TestModel test){
        testRepo.save(test);
    }
}
