package citivelociti.backend.Models;

import citivelociti.backend.Enums.Position;
import citivelociti.backend.Enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "strategy")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Strategy implements IStrategy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(insertable = false, updatable = false)
    private String type;
    @Enumerated
    private Status status;
    private String ticker;
    private Double quantity;
    private Double limits;      // Percentage to exit a strategy - profit
    private Double stop;        // Percentage to exit a strategy - loss
    @Enumerated
    private Position currentPosition;

    public Strategy() {
        this.stop = 10.0;
    }

    public Strategy(String name, String ticker, Double quantity, Double limit, Double stop) {
        this.name = name;
        this.currentPosition = Position.CLOSED;
        this.status = Status.ACTIVE;
        this.ticker = ticker;
        this.quantity = quantity;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
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
