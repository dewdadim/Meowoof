package com.example.meowoof;

public class Pet {
    private String name;
    private String gender;
    private String category;
    private int id;

    //Constructor
    public Pet(int id, String name, String gender, String category) {
        this.name = name;
        this.gender = gender;
        this.category = category;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
