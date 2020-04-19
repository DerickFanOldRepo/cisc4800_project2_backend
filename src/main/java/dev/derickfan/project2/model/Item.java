package dev.derickfan.project2.model;

public class Item {

    private int id;
    private String name;
    private Category category;

    public Item(int id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Item() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}