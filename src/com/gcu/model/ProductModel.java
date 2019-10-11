package com.gcu.model;

import java.util.ArrayList;

public class ProductModel {

    private int ID;
    private int userID;
    private String name;
    private String description;
    private ArrayList<String> tags;

    public ProductModel(){
        ID = 0;
        userID = 0;
        name = "";
        description = "";
        tags = new ArrayList<>();
    }

    public ProductModel(int ID, int userID, String name, String description, ArrayList<String> tags) {
        this.ID = ID;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
