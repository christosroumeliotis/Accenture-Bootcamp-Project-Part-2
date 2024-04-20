package com.accenture.part2.models;

public class Doctor {
    private String amka;
    private String name;
    private String surname;

    public Doctor(String amka, String name, String surname) {
        this.amka = amka;
        this.name = name;
        this.surname = surname;
    }

    public String getAmka() {
        return amka;
    }

    public void setAmka(String amka) {
        this.amka = amka;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
