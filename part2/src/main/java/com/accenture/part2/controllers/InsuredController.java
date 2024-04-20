package com.accenture.part2.controllers;

import com.accenture.part2.models.Doctor;
import com.accenture.part2.models.Insured;
import com.accenture.part2.models.Reservation;
import com.accenture.part2.models.Timeslot;
import com.accenture.part2.services.InsuredService;
import com.accenture.part2.services.ReservationService;
import com.accenture.part2.services.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.Date;
import java.util.List;

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
    public Timeslot getAvailableTimeslotByDate(@RequestParam String date){
        return timeslotService.getAvailableTimeslotByDate(date);
    }
    @GetMapping("/available/month")
    public List<Timeslot> getAvailableTimeslotByMonth(@RequestParam int month){
        return timeslotService.getAvailableTimeslotByMonth(month);
    }

    @PostMapping()
    public List<Insured> addInsured(@RequestBody Insured insured){
        return insuredService.addInsured(insured);
    }
    @PostMapping("/makeReservation")
    public Reservation makeReservation(@RequestParam String insuredAmka,
                                       @RequestParam String timeslotDate,
                                       @RequestParam String doctorAmka){
        return insuredService.makeAReservation(insuredAmka,timeslotDate,doctorAmka);
    }

    @DeleteMapping("/unselectReservation")
    public String unselectReservation(@RequestParam String insuredAmka){
        return insuredService.unselectReservation(insuredAmka);
    }

}
