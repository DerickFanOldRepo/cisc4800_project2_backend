package dev.derickfan.project2.model;

public class Category {

    // Instance Variables
    private int id;
    private String name;

    // Default Constructor
    public Category() {}

    // Constructor
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters and Setters
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
}
