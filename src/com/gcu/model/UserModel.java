package com.gcu.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
User model that will be used by both the login and registration modules for the time being
 */
public class UserModel {

    private int ID;
    @NotNull(message = "This is a required field")
    @Size(min = 2, max = 14, message = "Please be sure that the entered user name is more than 2 characters and less than 14")
    private String username;
    @NotNull(message = "This is a required field")
    @Size(min = 7, max = 32, message = "Please be sure that your password is at least 7 characters and less than 32")
    private String password;
    @NotNull(message = "This is a required field")
    @Email(message = "Please enter a valid email")
    private String email;
    private int role;
    private int status;

    //Default constructor for forms
    public UserModel(){
        ID = 0;
        username = "Username";
        password = "Password";
        email = "Email";
        role = 0;
        status = 0;
    }

    //Non-default constructor for use during login
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
        //I believe this will be necessary so that the login form doesn't trip the validation haven't tested though
        this.email = "test@test.test";
    }

    //Non-default constructor for use during registration
    public UserModel(int ID, String username, String password, String email, int role, int status) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
