package com.example.lavenderadmin;

public class Candle {
    private String id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;

    public Candle() {
        // مطلوب للفايربيس
    }

    public Candle(String id, String name, double price, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
}