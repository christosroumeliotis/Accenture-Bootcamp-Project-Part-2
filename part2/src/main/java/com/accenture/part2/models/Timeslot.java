package com.accenture.part2.models;

public class Timeslot {
    private String code;
    private String date;
    private Integer endTime;
    private boolean isAvailable;

    public Timeslot(String code, String date, Integer endTime) {
        this.code = code;
        this.date = date;
        this.endTime = endTime;
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


    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
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
