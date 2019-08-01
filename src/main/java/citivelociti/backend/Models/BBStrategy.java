package citivelociti.backend.Models;

import javax.persistence.*;

/*
 * Bollinger Bands      Given a time span along with a standard deviation, program will
 *                      calculate the avg time span and set the std to usually 2. If the
 *                      current stock value is at the lower std, program will buy, if the
 *                      current stock is above the specified std, it will sell.
 */

@Entity
public class BBStrategy extends Strategy {

    private Integer timeSpan;   // In seconds
    private Integer std;

    public BBStrategy(){

    }

    /*
     * The default std is typically 2. If the user does not specify the std, we will default it to 2.
     */
    public BBStrategy(Double initialCapital, String name, String ticker, Double volume, Double limit, Double stop, Integer timeSpan) {
        super(initialCapital, name, ticker, volume, limit, stop);
        this.timeSpan = timeSpan;
        this.std = 2;
    }

    public BBStrategy(Double initialCapital,String name, String ticker, Double quantity, Double limit, Double stop, Integer timeSpan, Integer std) {
        super(initialCapital, name, ticker, quantity, limit, stop);
        this.timeSpan = timeSpan;
        this.std = std;
    }

    public Integer getTimeSpan() {
        return this.timeSpan;
    }

    public void setTimeSpan(Integer timeSpan) {
        this.timeSpan = timeSpan;
    }

    public Integer getStd() {
        return this.std;
    }

    public void setStd(Integer std) {
        this.std = std;
    }
}

