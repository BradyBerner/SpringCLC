package com.gcu.model;


import org.hibernate.validator.constraints.Email;
import javax.validation.Valid;
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
    @Valid
    private CredentialsModel credentials;
    //Had to kill email for time being, threw very nasty errors due to not recognizing email validation constraints
    @NotNull(message = "This is a required field")
    @Size(min = 3, message = "This is a required field")
    @Email(message = "Please enter a valid email address")
    private String email;
    @NotNull
    @Pattern(regexp="\\d{3}-\\d{3}-\\d{4}", message = "Please be sure that you've entered your phone number without the country code and with \"-\" between the area codes")
    private String phoneNumber;
    private int role;
    private int status;

    //Default constructor for forms
    public UserModel(){
        ID = 0; // The user's ID, largely used in conjuction with a database
        firstName = "fname"; //User's first name
        lastName = "lname"; // User's last name
        credentials = new CredentialsModel("", "");
        email = "test@test.test"; //Valid email for user
        phoneNumber = "111-111-1111"; // Phone number, entered with dashes and no parenthesis
        role = 0; //Whether the user is a typical user or administrator
        status = 0; //Whether the user is active or disabled
    }

    //Non-default constructor for use during registration
    public UserModel(int ID, String firstName, String lastName, String username, String password, String email, String phoneNumber, int role, int status) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        credentials = new CredentialsModel(username, password);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername(){ return credentials.getUsername(); }

    public void setUsername(String username){ credentials.setUsername(username); }

    public String getPassword(){ return credentials.getPassword(); }

    public void setPassword(String password){ credentials.setPassword(password);}

    public CredentialsModel getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsModel credentials) {
        this.credentials = credentials;
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
