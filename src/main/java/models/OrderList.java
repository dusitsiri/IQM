package models;

public class OrderList {
    private int id;
    private int date;
    private double price;
    private int qty;
    private double totalPrice;

    public OrderList(){
    }

    public OrderList(int id, int date, double price, int qty, double totalPrice) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.qty = qty;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
