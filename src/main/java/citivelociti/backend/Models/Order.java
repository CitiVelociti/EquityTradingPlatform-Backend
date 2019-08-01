package citivelociti.backend.Models;

import citivelociti.backend.Enums.OrderStatus;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order implements IOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer strategyId;     // The strategy which this trade is using
    private Boolean buy;            // True if the trader is buying, o.w. false -> trader is selling
    private Double price;       // Price of bought stock
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;          // Date which the strategy started
    @Enumerated
    private OrderStatus status;     // OPEN, FILLED, REJECTED

    public Order() {
        
    }

    public Order(Integer strategyId, Boolean buy, Double price) {
        this.strategyId = strategyId;
        this.buy = buy;
        this.price = price;
        this.date = Calendar.getInstance().getTime();
        this.status = OrderStatus.UNFILLED;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", strategyId=" + strategyId +
                ", buy=" + buy +
                ", price=" + price +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}