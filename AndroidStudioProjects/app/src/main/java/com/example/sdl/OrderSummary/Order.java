package com.example.sdl.OrderSummary;

public class Order {
    int sNo;
    String item;
    int quantity;

    public Order() { }

    public Order(int sNo, String item) {
        this.sNo = sNo;
        this.item = item;
        this.quantity = 1;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
