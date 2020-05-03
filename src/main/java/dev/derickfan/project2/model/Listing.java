package dev.derickfan.project2.model;

public class Listing {

    private int id;
    private User user;
    private Item item;
    private double price;
    private int quantity;

    public Listing(int id, User user, Item item, double price, int quantity) {
        this.id = id;
        this.user = user;
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public Listing() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
