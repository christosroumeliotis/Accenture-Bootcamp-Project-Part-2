package com.accenture.part2.models;

public class Timeslot {
    private String code;
    private String date;
    private boolean isAvailable;

    public Timeslot(String code, String date) {
        this.code = code;
        this.date = date;
        isAvailable = true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String toPdfFormat() {
        return "code: " + code + ", date:" + date;
    }

}
