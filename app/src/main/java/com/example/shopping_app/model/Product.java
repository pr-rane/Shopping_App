package com.example.shopping_app.model;

public class Product {

    private int id;

    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private float rate;
    private int ratecount;

    public Product(int id, String title, double price, String description, String category, String image, float rate, int ratecount) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.rate = rate;
        this.ratecount = ratecount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getRatecount() {
        return ratecount;
    }

    public void setRatecount(int ratecount) {
        this.ratecount = ratecount;
    }
}
