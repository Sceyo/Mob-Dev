package com.example.shopphlef;

public class Product {
    private int id;
    private String brand;
    private String description;
    private double price;
    private int imageResId; // Field for image resource ID
    private int quantity;
    // Constructor
    public Product(int id, String brand, String description, double price, int imageResId, int quantity) {
        this.id = id;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
        this.quantity = quantity;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand; // This method should return the brand name
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResId() { // Getter for image resource ID
        return imageResId;
    }

    public int getQuantity() {
        return 1;
    }

    public void setQuantity(int i) {

    }
}
