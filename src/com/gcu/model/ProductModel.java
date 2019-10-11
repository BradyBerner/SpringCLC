package com.gcu.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ProductModel {

    private int ID;
    private int userID;
    @NotEmpty(message = "This is a required field")
    @Size(min = 1, max = 40, message = "Please make sure the name of the product is between 1 and 40 characters")
    private String name;
    @Size(max = 255, message = "Please limit the product's description to 255 characters")
    private String description;
    @Size(max = 14, message = "Please limit the product's genre to 14 characters")
    private String genre;

    public ProductModel(){
        ID = 0;
        userID = 0;
        name = "";
        description = "";
        genre = "";
    }

    public ProductModel(int ID, int userID, String name, String description, String genre) {
        this.ID = ID;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.genre = genre;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }
}
