package citivelociti.backend.Models;

import citivelociti.backend.Enums.Position;
import citivelociti.backend.Enums.Status;
import citivelociti.backend.Enums.StrategyType;

public class Strategy implements IStrategy {

    private int id;
    private StrategyType strategyType;
    private Status status;
    private String ticker;
    private double volume;
    private double limit;
    private double stop;
    private Position currentPosition;

    public Strategy(String ticker, double volume, double limit, double stop) {
        this.currentPosition = Position.CLOSED;
        this.status = Status.ACTIVE;
        this.ticker = ticker;
        this.volume = volume;
        this.limit = limit;
        this.stop = stop;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StrategyType getStrategyType() {
        return this.strategyType;
    }

    public void setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getVolume() {
        return this.volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getLimit() {
        return this.limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getStop() {
        return this.stop;
    }

    public void setStop(double stop) {
        this.stop = stop;
    }

    public Position getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(Position current_position) {
        this.currentPosition = current_position;
    }
}
