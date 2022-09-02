package com.example.test4.Models;

public class adultmodel {
    public String sourceAddress;
    public String adulterant;

    public adultmodel() {
    }

    public adultmodel(String sourceAddress, String adulterant) {
        this.sourceAddress = sourceAddress;
        this.adulterant = adulterant;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getAdulterant() {
        return adulterant;
    }

    public void setAdulterant(String adulterant) {
        this.adulterant = adulterant;
    }
}


