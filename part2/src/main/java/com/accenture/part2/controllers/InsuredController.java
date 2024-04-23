package com.accenture.part2.controllers;

import com.accenture.part2.models.Insured;
import com.accenture.part2.models.Reservation;
import com.accenture.part2.models.Timeslot;
import com.accenture.part2.services.InsuredService;
import com.accenture.part2.services.ReservationService;
import com.accenture.part2.services.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.accenture.part2.Constants.NOT_FOUND;

@RestController
@RequestMapping("/insured")
public class InsuredController {
    @Autowired
    TimeslotService timeslotService;
    @Autowired
    InsuredService insuredService;
    @Autowired
    ReservationService reservationService;

    @GetMapping("/available/date")
    public Timeslot getAvailableTimeslotByDate(@RequestParam String date) {
        return timeslotService.getAvailableTimeslotByDate(date);
    }

    @GetMapping("/available/month")
    public List<Timeslot> getAvailableTimeslotByMonth(@RequestParam int month) {
        return timeslotService.getAvailableTimeslotByMonth(month);
    }

    @PostMapping()
    public List<Insured> addInsured(@RequestBody Insured insured) {
        return insuredService.addInsured(insured);
    }

    @PostMapping("/makeReservation")
    public Reservation makeReservation(@RequestParam String insuredAmka,
                                       @RequestParam String timeslotDate,
                                       @RequestParam String doctorAmka) {
        return insuredService.makeAReservation(insuredAmka, timeslotDate, doctorAmka);
    }

    @DeleteMapping("/unselectReservation")
    public String unselectReservation(@RequestParam String insuredAmka) {
        return insuredService.unselectReservation(insuredAmka);
    }

    @GetMapping("/vaccinationCoverage")
    public String getInfoOfInsured2(@RequestParam String insuredAmka) {
        return insuredService.getInfoOfInsured2(insuredAmka);
    }

    @GetMapping("/vaccinationCoverage/qrcode")
    public ResponseEntity<?> getInfoOfInsured(@RequestParam String insuredAmka) {
        String image = insuredService.getInfoOfInsured(insuredAmka);

        File file = new File(image);

        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(Files.probeContentType(Paths.get(file.getAbsolutePath()))))
                    .body(new UrlResource(file.toURI()));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

}
