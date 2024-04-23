package com.accenture.part2.controllers;

import com.accenture.part2.models.Timeslot;
import com.accenture.part2.services.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeslot")
public class TimeslotController {

    @Autowired
    TimeslotService timeslotService;

    @GetMapping
    public List<Timeslot> getAllTimeslots() {
        return timeslotService.getAllTimeslots();
    }

    @GetMapping("/timeslot")
    public Timeslot getTimeslot(@RequestBody Timeslot timeslot) {
        return timeslotService.getTimeslot(timeslot);
    }

    @PostMapping()
    public List<Timeslot> addTimeslot(@RequestBody Timeslot timeslot) {
        return timeslotService.addTimeslot(timeslot);
    }
}
