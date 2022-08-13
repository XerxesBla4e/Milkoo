package com.example.sqliteproject.Model;

public class HODModel {
    String name;
    String email;
    String pass;
    String status;

    public HODModel() {
    }

    public HODModel(String name, String email, String pass, String status) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
