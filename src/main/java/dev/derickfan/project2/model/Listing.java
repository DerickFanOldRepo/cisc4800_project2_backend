package dev.derickfan.project2.model;

public class Listing {

    private int id;
    private String username;
    private String itemName;
    private double price;
    private String category;
    private String url;

    public Listing(int id, String username, String itemName, double price, String category, String url) {
        this.id = id;
        this.username = username;
        this.itemName = itemName;
        this.price = price;
        this.category = category;
        this.url = url;
    }

    public Listing() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
