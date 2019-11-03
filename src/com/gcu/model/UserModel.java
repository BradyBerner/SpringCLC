package com.gcu.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/*
User model that will be used by both the login and registration modules for the time being
Makes use of a regular expression found on: https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression
 */
public class UserModel {

    private int ID;
    @NotEmpty(message = "This is a required field")
    @Size(min = 2, max = 14, message = "Please be sure that your input is more than 2 characters and less than 14")
    private String firstName;
    @NotEmpty(message = "This is a required field")
    @Size(min = 2, max = 14, message = "Please be sure that your input is more than 2 characters and less than 14")
    private String lastName;
    @Valid
    private CredentialsModel credentials;
    @NotEmpty(message = "This is a required field")
    //Regular expression was obtained from source linked above
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])", message = "Please enter a valid email address")
    private String email;
    @NotEmpty
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
