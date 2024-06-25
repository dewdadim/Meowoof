package com.example.meowoof;

import java.util.Date;

public class WalkHistory {
    private int id;
    private String pet;
    private String owner;
    private int stepCount;
    private Date save_at;

    //Constructor
    public WalkHistory(int id, String pet, String owner, int stepCount, Date save_at) {
        this.id = id;
        this.pet = pet;
        this.owner = owner;
        this.stepCount = stepCount;
        this.save_at = save_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public Date getSave_at() {
        return save_at;
    }

    public void setSave_at(Date save_at) {
        this.save_at = save_at;
    }
}
