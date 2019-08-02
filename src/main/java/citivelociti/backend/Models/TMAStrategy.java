package citivelociti.backend.Models;


import javax.persistence.*;

/*
 * Two Moving Averages      For long term & short term investors
 *                          User is able to choose a time frame which they prefer to view the avg of stocks
 *                          User is able to get an estimate of which direction the stock is moving towards
 *                          Given a time frame, the ending prices are jotted and used to make a graph
 * Cross over               short term cross above long term -> buy
 *                          long term cross above short term -> sell
 */

@Entity
public class TMAStrategy extends Strategy {

    private Integer slowAvgIntervale;
    private Integer fastAvgIntervale;
    private Boolean shortBelow;

    public TMAStrategy() {

    }

    public TMAStrategy(String name, String ticker, Double quantity, Double limit, Double stop, Integer slowAvgIntervale, Integer fastAvgIntervale) {
        super(name, ticker, quantity, limit, stop);
        this.slowAvgIntervale = slowAvgIntervale;
        this.fastAvgIntervale = fastAvgIntervale;
    }

    public Integer getSlowAvgIntervale() {
        return slowAvgIntervale;
    }

    public void setSlowAvgIntervale(Integer slowAvgIntervale) {
        this.slowAvgIntervale = slowAvgIntervale;
    }

    public Integer getFastAvgIntervale() {
        return fastAvgIntervale;
    }

    public void setFastAvgIntervale(Integer fastAvgIntervale) {
        this.fastAvgIntervale = fastAvgIntervale;
    }

    public Boolean getShortBelow() {
        return shortBelow;
    }

    public void setShortBelow(Boolean shortBelow) {
        this.shortBelow = shortBelow;
    }

}

