package com.accenture.part2.models;

public class Timeslot {
    private String date;
    private Integer endTime;
    private boolean isAvailable;
    private String code;

    public Timeslot(String date, Integer endTime,String code) {
        this.date = date;
        this.endTime = endTime;
        this.code = code;
        isAvailable=true;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

}
