package com.accenture.part2.controllers;

import com.accenture.part2.models.Insured;
import com.accenture.part2.models.Reservation;
import com.accenture.part2.models.Timeslot;
import com.accenture.part2.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping()
    public List<Reservation> getReservations(){
        return reservationService.getReservations();
    }

    /*@PostMapping()
    public Reservation addReservation (@RequestBody Insured insured,
                                             @RequestBody Timeslot timeslot){
        return reservationService.addReservation(insured,timeslot);
    }*/
}
