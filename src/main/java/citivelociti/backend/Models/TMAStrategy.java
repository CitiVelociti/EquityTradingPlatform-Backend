package citivelociti.backend.Models;

import javax.persistence.Entity;

@Entity
public class TMAStrategy extends Strategy {


    private Integer slowAvgIntervale;
    private Integer fastAvgIntervale;
    private boolean shortBelow;

    public TMAStrategy(String ticker, Double volume, Double limit, Double stop, Integer slowAvgIntervale, Integer fastAvgIntervale){
        super(ticker, volume, limit, stop);
        this.slowAvgIntervale = slowAvgIntervale;
        this.fastAvgIntervale = fastAvgIntervale;
    };
}
