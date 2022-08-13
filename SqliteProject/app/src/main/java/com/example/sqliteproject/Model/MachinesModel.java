package com.example.sqliteproject.Model;

public class MachinesModel {
    int id;
    String currentdate;
    String name;
    String serialnum;
    String modelnum;
    String manufacturer;
    String department;
    String machine_state;
    String lastservice;
    String comment;

    public MachinesModel() {
    }

    public MachinesModel(int id, String currentdate, String name, String serialnum, String modelnum, String manufacturer, String department, String machine_state, String lastservice, String comment) {
        this.id = id;
        this.currentdate = currentdate;
        this.name = name;
        this.serialnum = serialnum;
        this.modelnum = modelnum;
        this.manufacturer = manufacturer;
        this.department = department;
        this.machine_state = machine_state;
        this.lastservice = lastservice;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(String serialnum) {
        this.serialnum = serialnum;
    }

    public String getModelnum() {
        return modelnum;
    }

    public void setModelnum(String modelnum) {
        this.modelnum = modelnum;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMachine_state() {
        return machine_state;
    }

    public void setMachine_state(String machine_state) {
        this.machine_state = machine_state;
    }

    public String getLastservice() {
        return lastservice;
    }

    public void setLastservice(String lastservice) {
        this.lastservice = lastservice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}