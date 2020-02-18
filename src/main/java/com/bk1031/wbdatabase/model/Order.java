package com.bk1031.wbdatabase.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    String orderID;
    String userID;
    boolean paymentComplete;

    public List<OrderItem> items = new ArrayList<>();

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isPaymentComplete() {
        return paymentComplete;
    }

    public void setPaymentComplete(boolean paymentComplete) {
        this.paymentComplete = paymentComplete;
    }

    @Override
    public String toString() {
        return "{" +
                "\"orderID\":\"" + orderID + "\"," +
                "\"userID\":\"" + userID + "\"," +
                "\"paymentComplete\":" + paymentComplete + "," +
                "\"items\":" + items.toString() + "" +
                "}";
    }
}
