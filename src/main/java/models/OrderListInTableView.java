package models;

public class OrderListInTableView {     //Class นี้ date จะเป็น String
    private int id;
    private String date;
    private double price;
    private int qty;
    private double totalPrice;

    public OrderListInTableView(){
    }

    public OrderListInTableView(int id, String date, double price, int qty, double totalPrice) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
