package com.accenture.part2.services;

import com.accenture.part2.models.*;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.accenture.part2.Constants.*;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_WITH_AMKA_NOT_FOUND, insuredAmka));
        }
        Timeslot timeslot = null;
        for (Timeslot t : timeslotService.getAllTimeslots()) {
            if (t.getDate().equals(timeslotDate)) {
                timeslot = t;
            }
        }
        if (timeslot == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TIMESLOT_NOT_FOUND, timeslotDate));
        }
        Doctor doctor = null;
        for (Doctor d : doctorService.getAllDoctors()) {
            if (d.getAmka().equals(doctorAmka)) {
                doctor = d;
            }
        }
        if (doctor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(DOCTOR_WITH_AMKA_NOT_FOUND, doctorAmka));
        }
        if (timeslot.isAvailable() && insured.getReservation() == null) {
            insured.setDoctor(doctor);
            insured.setReservation(reservationService.addReservation(insured, timeslot));
            timeslot.setAvailable(false);
            return insured.getReservation();
        } else if (!timeslot.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TIMESLOT_IS_RESERVED, timeslotDate));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_HAS_RESERVATION, insuredAmka));
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_WITH_AMKA_NOT_FOUND, insuredAmka));
        }
        if (insured.getReservation() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_HAS_NOT_RESERVATION, insuredAmka));
        }
        if (insured.getTimesReservationChanged() < 2) {
            reservationService.deleteReservation(insured.getReservation());
            insured.getReservation().getTimeslot().setAvailable(true);
            insured.setReservation(null);
            insured.increaseTimesReservationChanged();
            return RESERVATION_UNSELECTED;
        } else {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, String.format(INSURED_CANT_CHANGE_RESERVATION_OVER_2_TIMES, insuredAmka));

        }
    }

    public String getInfoOfInsured2(String insuredAmka) {

        for (Insured insured : insureds) {
            if (Objects.equals(insured.getAmka(), insuredAmka))
                if (insured.getVaccinationCoverage() != null) {
                    return (String.format(INSURED_ALREADY_VACCINATED, insuredAmka, insured.getVaccinationCoverage().getExpirationDate()));
                } else
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_NOT_VACCINATED_YET, insuredAmka));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_WITH_AMKA_NOT_FOUND, insuredAmka));
    }


    public String getInfoOfInsured(String insuredAmka) {

        String path = "./QRCodeWithAMKA" + insuredAmka + ".png"; //AYTO TO ALLAZOYME STO SOSTO PATH, DLD AN DEN LEITOYRGHSEI THA BALO SKETO "./QRCode.png"

        for (Insured insured : insureds) {
            if (Objects.equals(insured.getAmka(), insuredAmka))
                if (insured.getVaccinationCoverage() != null) {
                    try {
                        // Generate and Save Qr Code Image in static/image folder
                        QRCodeGenerator.generateQRCodeImage(String.format(INSURED_ALREADY_VACCINATED, insuredAmka, insured.getVaccinationCoverage().getExpirationDate()), 250, 250, path);
                        // EDO MPORO NA BALO SAN TEXT TO "http://localhost:8081/insured/vaccinationCoverage?insuredAmka="+insuredAmka
                        // http://localhost:8081/insured/vaccinationCoverage/qrcode/testing?insuredAmka=amka1
                        // "/vaccinationcoverage/" + insuredAmka + "/qrcode"

                    } catch (WriterException | IOException e) {
                        e.printStackTrace();
                    }

                    return path; //kano return to string path
                } else
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_NOT_VACCINATED_YET, insuredAmka));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_WITH_AMKA_NOT_FOUND, insuredAmka));

    }

    public String[] getInfoOfInsured3(String insuredAmka) {
        String arr[] = new String[2];

        String path = "./QRCode.png"; //AYTO TO ALLAZOYME STO SOSTO PATH, DLD AN DEN LEITOYRGHSEI THA BALO SKETO "./QRCode.png"

        for (Insured insured : insureds) {
            if (Objects.equals(insured.getAmka(), insuredAmka))
                if (insured.getVaccinationCoverage() != null) {
                    try {
                        // Generate and Save Qr Code Image in static/image folder
                        QRCodeGenerator.generateQRCodeImage(String.format(INSURED_ALREADY_VACCINATED, insuredAmka, insured.getVaccinationCoverage().getExpirationDate()), 250, 250, path);

                    } catch (WriterException | IOException e) {
                        e.printStackTrace();
                    }

                    arr[0] = path;
                    arr[1] = String.format(INSURED_ALREADY_VACCINATED, insuredAmka, insured.getVaccinationCoverage().getExpirationDate());

                    return arr;

                } else
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_NOT_VACCINATED_YET, insuredAmka));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_WITH_AMKA_NOT_FOUND, insuredAmka));
    }

}
