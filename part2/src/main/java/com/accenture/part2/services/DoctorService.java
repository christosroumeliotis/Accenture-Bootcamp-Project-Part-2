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
        for(Doctor d:doctors){
            if(d.getAmka().equals(doctor.getAmka())){
                return d;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor with AMKA: "+ doctor.getAmka()+" not found");
    }

    public Vaccination addVaccination(String timeslotCode, String insuredAmka, String endDate) {
        for(Insured ins:insuredService.returnAllInsureds()){
            if(ins.getAmka().equals(insuredAmka)){
                if(ins.getReservation()!=null && ins.getVaccinationCoverage()==null){
                    Vaccination vc=new Vaccination(ins,timeslotCode,endDate);
                    ins.setVaccinationCoverage(vc);
                    reservationService.deleteReservation(ins.getReservation());
                    ins.setReservation(null);
                    return vc;
                }else if(ins.getVaccinationCoverage()!=null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Insured with AMKA: " +insuredAmka+ " has already been vaccinated");
                }else{
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Insured with AMKA: " +insuredAmka+ " doesn't have a reservation");
                }
            }

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Insured with AMKA: " +insuredAmka+ " doesn't exist");
    }
}
