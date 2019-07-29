package citivelociti.backend.Models;
import citivelociti.backend.Enums.Position;
import citivelociti.backend.Enums.Status;

import javax.persistence.*;


@Entity
@Table(name="strategy")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Strategy implements IStrategy {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(insertable = false, updatable = false)
    private String type;
    @Enumerated
    private Status status;
    private String ticker;
    private Double volume;
    private Double limits;
    private Double stop;
    @Enumerated
    private Position currentPosition;

    public Strategy() {
    }

    public Strategy(String name, String ticker, Double volume, Double limit, Double stop) {
        this.name = name;
        this.currentPosition = Position.CLOSED;
        this.status = Status.ACTIVE;
        this.ticker = ticker;
        this.volume = volume;
        this.limits = limit;
        this.stop = stop;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getLimits() {
        return limits;
    }

    public void setLimits(Double limits) {
        this.limits = limits;
    }

    public Double getStop() {
        return stop;
    }

    public void setStop(Double stop) {
        this.stop = stop;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }


}
