package com.gcu.model;


import javax.validation.constraints.*;

/*
User model that will be used by both the login and registration modules for the time being
 */
public class UserModel {

    private int ID;
    @NotNull(message = "This is a required field")
    @Size(min = 2, max = 14, message = "Please be sure that your input is more than 2 characters and less than 14")
    private String firstName;
    @NotNull(message = "This is a required field")
    @Size(min = 2, max = 14, message = "Please be sure that your input is more than 2 characters and less than 14")
    private String lastName;
    @NotNull(message = "This is a required field")
    @Size(min = 2, max = 14, message = "Please be sure that the entered user name is more than 2 characters and less than 14")
    private String username;
    @NotNull(message = "This is a required field")
    @Size(min = 7, max = 32, message = "Please be sure that your password is at least 7 characters and less than 32")
    private String password;
    //Had to kill email for time being, threw very nasty errors due to not recognizing email validation constraints
    @NotNull(message = "This is a required field")
    private String email;
    @NotNull
    @Pattern(regexp="\\d{3}-\\d{3}-\\d{4}", message = "Please be sure that you've entered your phone number without the country code and with \"-\" between the area codes")
    private String phoneNumber;
    private int role;
    private int status;

    //Default constructor for forms
    public UserModel(){
        ID = 0;
        firstName = "fname";
        lastName = "lname";
        username = "Username";
        password = "Password";
        email = "test@test.test";
        phoneNumber = "111-111-1111";
        role = 0;
        status = 0;
    }

    //Non-default constructor for use during login
    public UserModel(String username, String password) {
        firstName = "fname";
        lastName = "lname";
        this.username = username;
        this.password = password;
        //Email validation ended up not resolving at all, so this ended up being unnecessary (for now)
        email = "test@test.test";
        phoneNumber = "111-111-1111";
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
