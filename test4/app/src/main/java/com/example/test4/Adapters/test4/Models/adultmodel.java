package com.example.test4.Adapters.test4.Models;

public class adultmodel {
    public int _id;
    public String address;
    public String adulterant;

    public adultmodel() {
    }

    public adultmodel(int _id, String address, String adulterant) {
        this._id = _id;
        this.address = address;
        this.adulterant = adulterant;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdulterant() {
        return adulterant;
    }

    public void setAdulterant(String adulterant) {
        this.adulterant = adulterant;
    }
}