package citivelociti.backend.Models;

import java.io.Serializable;

public class OrderTransaction implements Serializable {


    private boolean buy;
    private int id;
    private double price;
    private int size;
    private String stock;
    private String whenAsDate;

    public OrderTransaction() {
    }

    public OrderTransaction(boolean buy, int id, double price, int size, String stock, String whenAsDate) {
        this.buy = buy;
        this.id = id;
        this.price = price;
        this.size = size;
        this.stock = stock;
        this.whenAsDate = whenAsDate;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getWhenAsDate() {
        return whenAsDate;
    }

    public void setWhenAsDate(String whenAsDate) {
        this.whenAsDate = whenAsDate;
    }

    @Override
    public String toString() {
        return "OrderTransaction{" +
                "buy=" + buy +
                ", id=" + id +
                ", price=" + price +
                ", size=" + size +
                ", stock='" + stock + '\'' +
                ", whenAsDate='" + whenAsDate + '\'' +
                '}';
    }
}
