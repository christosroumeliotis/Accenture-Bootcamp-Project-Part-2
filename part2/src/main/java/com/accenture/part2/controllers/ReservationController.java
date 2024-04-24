package com.accenture.part2.controllers;

import com.accenture.part2.models.Reservation;
import com.accenture.part2.services.ReservationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.accenture.part2.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping()
    public List<Reservation> getReservations() {
        return reservationService.getReservations();
    }

    @GetMapping("/upcoming") // http://localhost:8081/reservation/upcoming
    public List<Reservation> getUpcomingReservations() {
        return reservationService.getUpcomingReservations();
    }

    @GetMapping("/date") // http://localhost:8081/reservation/date?date=2/12/2024
    public List<Reservation> getReservationsByDate(@RequestParam String date) {
        return reservationService.getReservationsByDate(date);
    }

    @GetMapping("/page/{page}") //http://localhost:8081/reservation/page/2?pageSize=3
    public List<Reservation> getReservationsByPage(@PathVariable int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        return reservationService.getAllReservations(page, pageSize);
    }

    @GetMapping("/doctor/pdf") // http://localhost:8081/reservation/doctor/pdf?doctorAmka=amka1&date=28/4/2024
    public void createPdf(@RequestParam String doctorAmka,
                          @RequestParam("date") String date, HttpServletResponse response) {
        response.setContentType("application/pdf");
        reservationService.exportAllReservationsToPdf(response, doctorAmka, date);
    }

}
