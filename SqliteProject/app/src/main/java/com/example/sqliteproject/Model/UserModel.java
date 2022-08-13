package com.example.sqliteproject.Model;

public class UserModel {
    int ID;
    String email;
    String Name;
    String Password;
    String Health_Facility;
    String HealthFacility_Level;

    public UserModel() {
    }

    public UserModel(int ID, String email, String name, String password, String health_Facility, String healthFacility_Level) {
        this.ID = ID;
        this.email = email;
        Name = name;
        Password = password;
        Health_Facility = health_Facility;
        HealthFacility_Level = healthFacility_Level;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getHealth_Facility() {
        return Health_Facility;
    }

    public void setHealth_Facility(String health_Facility) {
        Health_Facility = health_Facility;
    }

    public String getHealthFacility_Level() {
        return HealthFacility_Level;
    }

    public void setHealthFacility_Level(String healthFacility_Level) {
        HealthFacility_Level = healthFacility_Level;
    }
}