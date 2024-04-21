package com.accenture.part2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Insured {
    private String afm;
    private String amka;
    private String name;
    private String birthdate;
    private String surname;
    private String email;
    private int timesReservationChanged;
    private Vaccination vaccinationCoverage;
    @JsonIgnore
    private Reservation reservation;
    @JsonIgnore
    private Doctor doctor;

    public Insured(String afm, String amka, String name, String birthdate, String surname, String email) {
        this.afm = afm;
        this.amka = amka;
        this.name = name;
        this.birthdate = birthdate;
        this.surname = surname;
        this.email = email;
        timesReservationChanged=0;
        vaccinationCoverage=null;
    }

    public Vaccination getVaccinationCoverage() {
        return vaccinationCoverage;
    }

    public void setVaccinationCoverage(Vaccination vaccinationCoverage) {
        this.vaccinationCoverage = vaccinationCoverage;
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void increaseTimesReservationChanged() {
        timesReservationChanged += 1;
    }

    public int getTimesReservationChanged() {
        return timesReservationChanged;
    }
}
