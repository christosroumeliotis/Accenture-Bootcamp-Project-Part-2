package com.accenture.part2.services;

import com.accenture.part2.models.Insured;
import com.accenture.part2.models.Reservation;
import com.accenture.part2.models.Timeslot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    List<Reservation> reservations=new ArrayList<>();
    public Reservation addReservation(Insured insured, Timeslot timeslot) {
        Reservation r=new Reservation(insured,timeslot);
        reservations.add(r);
        return r;
    }
    public void deleteReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
