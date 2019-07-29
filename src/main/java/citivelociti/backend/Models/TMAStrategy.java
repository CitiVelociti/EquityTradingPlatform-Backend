package citivelociti.backend.Models;

import javax.persistence.*;

@Entity
public class TMAStrategy extends Strategy {
    private Integer slowAvgIntervale;
    private Integer fastAvgIntervale;
    private Boolean shortBelow;

    public TMAStrategy(){ super(); };

    public TMAStrategy(String ticker, Double volume, Double limit, Double stop, Integer slowAvgIntervale, Integer fastAvgIntervale){
        super(ticker, volume, limit, stop);
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

