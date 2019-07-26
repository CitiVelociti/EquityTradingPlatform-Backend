package citivelociti.backend.Models;

import javax.persistence.*;

@Entity
@Table(name="test_models")
public class TestModel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String testName;

    public TestModel(){};
    public TestModel(String testName)
    {
        this.testName=testName;
    }
}
