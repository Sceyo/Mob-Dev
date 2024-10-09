package com.example.shopphlef;

public class OrderItem {
    private String orderNumber;
    private String orderDate;
    private String productBrand;
    private String productDescription;
    private String orderPrice;
    private int orderImage;

    public OrderItem(String orderNumber, String orderDate, String productBrand, String productDescription, String orderPrice, int orderImage) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.productBrand = productBrand;
        this.productDescription = productDescription;
        this.orderPrice = orderPrice;
        this.orderImage = orderImage;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public int getOrderImage() {
        return orderImage;
    }
}
