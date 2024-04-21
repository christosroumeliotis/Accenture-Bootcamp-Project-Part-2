package com.accenture.part2.models;

public class Vaccination {


    private Insured insured;
    private String timeslotCode;
    private String expirationDate;

    public Vaccination() {
    }

    public Vaccination(Insured insured,String timeslotCode,String expirationDate) {
        this.insured = insured;
        this.expirationDate = expirationDate;
        this.timeslotCode=timeslotCode;
    }

    public String getTimeslotCode() {
        return timeslotCode;
    }

    public void setTimeslotCode(String timeslotCode) {
        this.timeslotCode = timeslotCode;
    }

    public Insured getInsured() {
        return insured;
    }

    public void setInsured(Insured insured) {
        this.insured = insured;
    }


    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }



}