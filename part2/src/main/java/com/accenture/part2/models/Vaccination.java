package com.accenture.part2.models;

public class Vaccination {

    private Insured insured;
    private String expirationDate;

    public Vaccination() {
    }

    public Vaccination(Insured insured,String expirationDate) {
        this.insured = insured;
        this.expirationDate = expirationDate;
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