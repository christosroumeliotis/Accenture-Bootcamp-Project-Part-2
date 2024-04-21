package com.accenture.part2.controllers;

import com.accenture.part2.DTOs.VaccinationDTO;
import com.accenture.part2.models.Doctor;
import com.accenture.part2.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PostMapping
    public List<Doctor> addDoctor(@RequestBody Doctor doctor){
        return doctorService.addDoctor(doctor);
    }

    @GetMapping("/{amka}")
    public VaccinationDTO getVaccinationByAmka(@PathVariable String amka) {
        return doctorService.getVaccinationByAmka(amka);
    }

    @GetMapping("/all")
    public List<VaccinationDTO> getALlVaccinations() {return doctorService.getVaccinations();}

    @PostMapping("/vaccination")
    public List<VaccinationDTO> addVaccination(@RequestBody VaccinationDTO vaccinationDTO) {
        return doctorService.addVaccination(vaccinationDTO);
    }

    @PutMapping("/{amka}")
    public VaccinationDTO updateVaccination(@PathVariable String amka,
                                            @RequestParam(required = false) String vaccinationDate,
                                            @RequestParam(required = false) String vaccinationTime,
                                            @RequestParam(required = false) String expirationDate,
                                            @RequestParam(required = false) String code) {
        return doctorService.updateVaccination(amka,vaccinationDate,vaccinationTime, expirationDate,code);
    }

    @DeleteMapping("/{amka}")
    public ResponseEntity<HttpStatus> deleteVaccination(@PathVariable String amka) {
        doctorService.deleteVaccination(amka);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

