package com.example.sqliteproject.Model;

public class UploadModel {

    String current_date;
    String machnename;
    String serialno;
    String modelno;
    String manufacturer;
    String department;
    String machinestate;
    String lastservice;
    String comment;

    public UploadModel() {
    }

    public UploadModel(String current_date, String machnename, String serialno, String modelno, String manufacturer, String department, String machinestate, String lastservice, String comment) {
        this.current_date = current_date;
        this.machnename = machnename;
        this.serialno = serialno;
        this.modelno = modelno;
        this.manufacturer = manufacturer;
        this.department = department;
        this.machinestate = machinestate;
        this.lastservice = lastservice;
        this.comment = comment;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getMachnename() {
        return machnename;
    }

    public void setMachnename(String machnename) {
        this.machnename = machnename;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
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

    public String getMachinestate() {
        return machinestate;
    }

    public void setMachinestate(String machinestate) {
        this.machinestate = machinestate;
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