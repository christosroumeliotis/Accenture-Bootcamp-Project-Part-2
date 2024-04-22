package com.accenture.part2.services;

import com.accenture.part2.models.*;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class InsuredService {
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";

    @Autowired
    TimeslotService timeslotService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    ReservationService reservationService;

    List<Insured> insureds = new ArrayList<>();


    public List<Insured> addInsured(Insured insured) {
        insureds.add(insured);
        return insureds;
    }

    public List<Insured> returnAllInsureds() {
        return insureds;
    }

    public Reservation makeAReservation(String insuredAmka, String timeslotDate, String doctorAmka) {
        Insured insured = null;
        for (Insured ins : insureds) {
            if (ins.getAmka().equals(insuredAmka)) {
                insured = ins;
            }
        }
        if (insured == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Insured with AMKA: " + insuredAmka + " not found");
        }
        Timeslot timeslot = null;
        for (Timeslot t : timeslotService.getAllTimeslots()) {
            if (t.getDate().equals(timeslotDate)) {
                timeslot = t;
            }
        }
        if (timeslot == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Timeslot at: " + timeslotDate + " not found");
        }
        Doctor doctor = null;
        for (Doctor d : doctorService.getAllDoctors()) {
            if (d.getAmka().equals(doctorAmka)) {
                doctor = d;
            }
        }
        if (doctor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor with AMKA: " + doctorAmka + " not found");
        }
        if (timeslot.isAvailable() && insured.getReservation() == null) {
            insured.setDoctor(doctor);
            insured.setReservation(reservationService.addReservation(insured, timeslot));
            timeslot.setAvailable(false);
            return insured.getReservation();
        } else if (!timeslot.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Timeslot at : " + timeslotDate + " is reserved");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Insured with AMKA: " + insuredAmka + " already has a reservation");

        }

    }

    public String unselectReservation(String insuredAmka) {
        Insured insured = null;
        for (Insured ins : insureds) {
            if (ins.getAmka().equals(insuredAmka)) {
                insured = ins;
            }
        }
        if (insured == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Insured with AMKA: " + insuredAmka + " not found");
        }
        if (insured.getReservation() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation for insured with with AMKA: " + insuredAmka + " not found, you have to make one first to unselect it");
        }
        if (insured.getTimesReservationChanged() < 2) {
            reservationService.deleteReservation(insured.getReservation());
            insured.setReservation(null);
            insured.increaseTimesReservationChanged();
            return "Reservation unselected";
        } else {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Insured with with AMKA: " + insuredAmka + " cant change reservation over 2 times");

        }

    }


    public Vaccination getInfoOfInsured(String insuredAmka) {

        for (Insured insured : insureds) {
            if (Objects.equals(insured.getAmka(), insuredAmka))
                if (insured.getVaccinationCoverage() != null) {

                    byte[] image = new byte[0];
                    try {

                        // Generate and Return Qr Code in Byte Array
                        image = QRCodeGenerator.getQRCodeImage("/vaccinationcoverage/" + insuredAmka + "/qrcode", 250, 250);

                        // Generate and Save Qr Code Image in static/image folder
                        QRCodeGenerator.generateQRCodeImage("/vaccinationcoverage/" + insuredAmka + "/qrcode", 250, 250, QR_CODE_IMAGE_PATH);

                    } catch (WriterException | IOException e) {
                        e.printStackTrace();
                    }
                    // Convert Byte Array into Base64 Encode String
                    String qrcode = Base64.getEncoder().encodeToString(image);


                    return insured.getVaccinationCoverage();
//                        return ((new InsuredDTO(insured.getAmka(), insured.getVaccinationCoverage())) (vacc.getExpirationDate()));
//                        return s;
                } else
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This insured with AMKA: " + insuredAmka + ", hasn't vaccinated yet!");

        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error! This insured with AMKA: " + insuredAmka + ", doesn't exists!");

    }
}
