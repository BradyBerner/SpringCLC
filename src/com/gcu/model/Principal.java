package com.gcu.model;

public class Principal {

    private String username;
    private int ID;
    private int role;
    private int status;

    public Principal(){
        username = "";
        ID = -1;
        role = -1;
        status = -1;
    }

    public Principal(String username, int ID, int role, int status) {
        this.username = username;
        this.ID = ID;
        this.role = role;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
