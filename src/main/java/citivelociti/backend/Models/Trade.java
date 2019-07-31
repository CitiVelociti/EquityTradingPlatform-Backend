package citivelociti.backend.Models;

import citivelociti.backend.Enums.TradeStatus;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "trade")
public class Trade implements ITrade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer strategyId;     // The strategy which this trade is using
    private Boolean buy;            // True if the trader is buying, o.w. false -> trader is selling
    private Double openPrice;       // Price of bought stock
    private Double closePrice;      // Price of sell stock
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date openDate;          // Date which the strategy started
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;         // Date which the strategy ended
    @Enumerated
    private TradeStatus status;     // OPEN, FILLED, REJECTED

    public Trade() {
        
    }

    public Trade(int strategyId, boolean buy, double openPrice) {
        this.strategyId = strategyId;
        this.buy = buy;
        this.openPrice = openPrice;
        this.openDate = Calendar.getInstance().getTime();
        this.status = TradeStatus.OPEN;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public Boolean getBuy() {
        return buy;
    }

    public void setBuy(Boolean buy) {
        this.buy = buy;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }
}