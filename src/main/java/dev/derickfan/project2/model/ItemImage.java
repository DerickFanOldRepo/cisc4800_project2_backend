package dev.derickfan.project2.model;

public class ItemImage {

    // Instance variables
    private int id;
    private String url;

    // Constructor
    public ItemImage(int id, String url) {
        this.id = id;
        this.url = url;
    }

    // Default Constructor
    public ItemImage() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
