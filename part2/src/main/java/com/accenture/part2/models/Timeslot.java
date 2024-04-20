package com.accenture.part2.models;

public class Timeslot {
    private String date;

    private Integer endTime;
    private boolean isAvailable;

    public Timeslot(String date, Integer endTime) {
        this.date = date;
        this.endTime = endTime;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
