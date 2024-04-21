package com.accenture.part2.services;

import com.accenture.part2.models.Doctor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

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
}
