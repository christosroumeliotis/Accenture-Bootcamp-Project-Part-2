package com.accenture.part2.services;

import com.accenture.part2.DTOs.VaccinationDTO;
import com.accenture.part2.models.Doctor;
import com.accenture.part2.models.Insured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
            InsuredService insuredService;
    @Autowired
            ReservationService reservationService;

    List<Doctor> doctors = new ArrayList<>();
    List<VaccinationDTO> vaccinations = new ArrayList<>();
    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    public List<Doctor> addDoctor(Doctor doctor) {
        doctors.add(doctor);
        return doctors;
    }

    public Doctor getDoctor(Doctor doctor) {
        for(Doctor d:doctors){
            if(d.getAmka().equals(doctor.getAmka())){
                return d;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor with AMKA: "+ doctor.getAmka()+" not found");
    }

    public VaccinationDTO getVaccinationByAmka(String amka) {
        for (VaccinationDTO vaccination : vaccinations) {
            if (vaccination.getAmka().equals(amka)) {
                return vaccination;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Αυτό το ΑΜΚΑ δεν υπάρχει.");
    }

    public List<VaccinationDTO> addVaccination(VaccinationDTO vaccinationDTO){
        for (VaccinationDTO vaccination : vaccinations) {
            if (vaccination.getAmka().equals(vaccinationDTO.getAmka())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Αυτό το ΑΜΚΑ υπάρχει ήδη.");
            }
        }
        vaccinations.add(vaccinationDTO);
        for(Insured ins : insuredService.returnAllInsureds()) {
            if(ins.getAmka().equals(vaccinationDTO.getAmka())){
                reservationService.deleteReservation(ins.getReservation());
                ins.setVaccinationCoverage(vaccinationDTO);
            }
        }

        return vaccinations;
    }

    public List<VaccinationDTO> getVaccinations(){
        if(vaccinations.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Δεν υπάρχουν καταχωρήσεις.");
        return vaccinations;
    }

    public VaccinationDTO updateVaccination(String amka, String vaccinationDate, String vaccinationTime, String expirationDate,String code) {
        for (VaccinationDTO vaccination : vaccinations) {
            if (vaccination.getAmka().equals(amka)) {
                vaccination.setVaccinationDate(vaccinationDate);
                vaccination.setVaccinationTime(vaccinationTime);
                vaccination.setExpirationDate(expirationDate);
                vaccination.setCode(code);
                return vaccination;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Αυτό το ΑΜΚΑ δεν υπάρχει.");
    }

    public List<VaccinationDTO> deleteVaccination(String amka) {
        for(VaccinationDTO vaccination : vaccinations) {
            if (vaccination.getAmka().equals(amka)) {
                vaccinations.remove(vaccination);
                return vaccinations;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Αυτό το ΑΜΚΑ δεν υπάρχει.");
    }
}
