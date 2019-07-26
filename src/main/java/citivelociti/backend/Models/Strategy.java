package citivelociti.backend.Models;

import citivelociti.backend.Enums.Position;
import citivelociti.backend.Enums.Status;
import citivelociti.backend.Enums.StrategyType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Strategy implements StrategyInterface{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Integer id;
    private StrategyType strategy_type;
    private Status status;
    private String ticker;
    private Double volume;
    private Double limit;
    private Double stop;
    private Position current_position;

    public Strategy(String ticker, Double volume, Double limit, Double stop)
    {
        current_position = Position.CLOSED;
        status = Status.ACTIVE;
        this.ticker = ticker;
        this.volume = volume;
        this.limit = limit;
        this.stop = stop;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StrategyType getStrategy_type() {
        return strategy_type;
    }

    public void setStrategy_type(StrategyType strategy_type) {
        this.strategy_type = strategy_type;
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getStop() {
        return stop;
    }

    public void setStop(double stop) {
        this.stop = stop;
    }

    public Position getCurrent_position() {
        return current_position;
    }

    public void setCurrent_position(Position current_position) {
        this.current_position = current_position;
    }
}
