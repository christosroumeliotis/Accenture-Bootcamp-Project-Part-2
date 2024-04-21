package com.accenture.part2.controllers;

import com.accenture.part2.models.Doctor;
import com.accenture.part2.models.Vaccination;
import com.accenture.part2.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PostMapping
    public List<Doctor> addDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

    @PostMapping("/vaccination")
    public Vaccination addVaccination(@RequestParam String timeslotCode, @RequestParam String insuredAmka, @RequestParam String endDate) {
        return doctorService.addVaccination(timeslotCode, insuredAmka, endDate);
    }
}
