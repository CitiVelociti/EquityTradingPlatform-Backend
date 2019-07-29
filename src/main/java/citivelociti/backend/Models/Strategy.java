package citivelociti.backend.Models;
import javax.persistence.*;




@Entity
@Table(name="strategy")
public class Strategy implements IStrategy {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
   // private String name;
    //private StrategyType strategyType;
    //private Status status;
    private String ticker;
    private Double volume;
    private Double limit;
    private Double stop;
    //private Position currentPosition;

    public Strategy(String ticker, Double volume, Double limit, Double stop) {
       // this.currentPosition = Position.CLOSED;
       // this.status = Status.ACTIVE;
        this.ticker = ticker;
        //this.volume = volume;
        this.limit = limit;
        this.stop = stop;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }



    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Double getStop() {
        return stop;
    }

    public void setStop(Double stop) {
        this.stop = stop;
    }


}
