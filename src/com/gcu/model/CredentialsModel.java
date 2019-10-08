package com.gcu.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CredentialsModel {

    @NotEmpty(message = "This is a required field")
    @Size(min = 2, max = 14, message = "Please be sure that the entered user name is more than 2 and less than 14")
    private String username;
    @NotEmpty(message = "This is a required field")
    @Size(min = 7, max = 32, message = "Please be sure that the entered password is at least 7 characters and less than 32")
    private String password;

    public CredentialsModel(){
        username = "";
        password = "";
    }

    public CredentialsModel(String username, String password){
        this.username = username;
        this.password = password;
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
}
