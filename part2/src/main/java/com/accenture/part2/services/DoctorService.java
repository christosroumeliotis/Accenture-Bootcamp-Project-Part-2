package com.accenture.part2.services;

import com.accenture.part2.models.Doctor;
import com.accenture.part2.models.Insured;
import com.accenture.part2.models.Vaccination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.accenture.part2.Constants.*;

@Service
public class DoctorService {

    @Autowired
    InsuredService insuredService;
    @Autowired
    ReservationService reservationService;

    List<Doctor> doctors = new ArrayList<>();

    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    public List<Doctor> addDoctor(Doctor doctor) {
        doctors.add(doctor);
        return doctors;
    }

    public Doctor getDoctor(Doctor doctor) {
        for (Doctor d : doctors) {
            if (d.getAmka().equals(doctor.getAmka())) {
                return d;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(DOCTOR_WITH_AMKA_NOT_FOUND, doctor.getAmka()));
    }

    public Vaccination addVaccination(String timeslotCode, String insuredAmka, String endDate) {
        for (Insured ins : insuredService.returnAllInsureds()) {
            if (ins.getAmka().equals(insuredAmka)) {
                if (ins.getReservation() != null && ins.getVaccinationCoverage() == null) {
                    Vaccination vc = new Vaccination(ins, timeslotCode, endDate);
                    ins.setVaccinationCoverage(vc);
                    reservationService.deleteReservation(ins.getReservation());
                    ins.setReservation(null);
                    return vc;
                } else if (ins.getVaccinationCoverage() != null) {
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format(INSURED_IS_VACCINATED, insuredAmka));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format(INSURED_HAS_NOT_RESERVATION, insuredAmka));
                }
            }

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(INSURED_WITH_AMKA_NOT_FOUND, insuredAmka));
    }
}
