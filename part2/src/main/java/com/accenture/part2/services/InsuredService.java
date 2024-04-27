package com.accenture.part2.services;

import com.accenture.part2.models.*;
import com.accenture.part2.utils.QRCodeGenerator;
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
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(TIMESLOT_IS_RESERVED, timeslotDate));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format(INSURED_HAS_RESERVATION, insuredAmka));
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
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format(INSURED_HAS_NOT_RESERVATION_MUST_UNSELECT, insuredAmka));
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
            if (insured.getAmka().equals(insuredAmka))
                if (insured.getVaccinationCoverage() != null) {
                    return (String.format(INSURED_ALREADY_VACCINATED, insuredAmka, insured.getVaccinationCoverage().getExpirationDate()));
                } else
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format(INSURED_NOT_VACCINATED_YET, insuredAmka));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_WITH_AMKA_NOT_FOUND, insuredAmka));
    }

    public String getInfoOfInsured(String insuredAmka) {

        String path = String.format(QR_CODE_PATH, insuredAmka);

        for (Insured insured : insureds) {
            if (Objects.equals(insured.getAmka(), insuredAmka))
                if (insured.getVaccinationCoverage() != null) {
                    try {
                        // Generate and Save Qr Code Image in static/image folder
                        QRCodeGenerator.generateQRCodeImage(String.format(INSURED_ALREADY_VACCINATED, insuredAmka,
                                insured.getVaccinationCoverage().getExpirationDate()), 250, 250, path);
                    } catch (WriterException | IOException e) {
                        e.printStackTrace();
                    }
                    return path;
                } else
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format(INSURED_NOT_VACCINATED_YET, insuredAmka));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_WITH_AMKA_NOT_FOUND, insuredAmka));
    }

}
