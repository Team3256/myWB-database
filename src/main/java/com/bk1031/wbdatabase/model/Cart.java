package com.bk1031.wbdatabase.model;

public class Cart {
    String productID;
    String userID;
    String productName;
    String size;
    String variant;
    int quantity;
    int price;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" +
                "\"productID\":\"" + productID + "\"," +
                "\"userID\":\"" + userID + "\"," +
                "\"productName\":\"" + productName + "\"," +
                "\"size\":\"" + size + "\"," +
                "\"variant\":\"" + variant + "\"," +
                "\"quantity\":" + quantity + "," +
                "\"price\":" + price + "" +
                "}";
    }
}
