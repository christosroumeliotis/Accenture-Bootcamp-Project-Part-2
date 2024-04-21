package com.accenture.part2.services;

import com.accenture.part2.models.Doctor;
import com.accenture.part2.models.Insured;
import com.accenture.part2.models.Reservation;
import com.accenture.part2.models.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class InsuredService {

    @Autowired
    TimeslotService timeslotService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    ReservationService reservationService;

    List<Insured> insureds=new ArrayList<>();

    public List<Insured> addInsured(Insured insured) {
        insureds.add(insured);
        return insureds;
    }

    public List<Insured> returnAllInsureds(){
        return insureds;
    }

    public Reservation makeAReservation(String insuredAmka, String timeslotDate, String doctorAmka) {
        Insured insured=null;
        for(Insured ins:insureds){
            if(ins.getAmka().equals(insuredAmka)){
                insured=ins;
            }
        }
        if(insured==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Insured with AMKA: "+ insuredAmka+" not found");
        }
        Timeslot timeslot=null;
        for(Timeslot t:timeslotService.getAllTimeslots()){
            if(t.getDate().equals(timeslotDate)){
                timeslot=t;
            }
        }
        if(timeslot==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Timeslot at: "+ timeslotDate+" not found");
        }
        Doctor doctor=null;
        for(Doctor d:doctorService.getAllDoctors()){
            if(d.getAmka().equals(doctorAmka)){
                doctor=d;
            }
        }
        if(doctor==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor with AMKA: "+ doctorAmka+" not found");
        }
        if(timeslot.isAvailable() && insured.getReservation()==null){
            insured.setDoctor(doctor);
            insured.setReservation(reservationService.addReservation(insured,timeslot));
            timeslot.setAvailable(false);
            return insured.getReservation();
        }else if(!timeslot.isAvailable()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Timeslot at : "+ timeslotDate+" is reserved");
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Insured with AMKA: " +insuredAmka+ " already has a reservation");

        }

    }

    public String unselectReservation(String insuredAmka) {
        Insured insured=null;
        for(Insured ins:insureds){
            if(ins.getAmka().equals(insuredAmka)){
                insured=ins;
            }
        }
        if(insured==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Insured with AMKA: "+ insuredAmka+" not found");
        }
        if(insured.getReservation()==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Reservation for insured with with AMKA: "+ insuredAmka+" not found, you have to make one first to unselect it");
        }
        if(insured.getTimesReservationChanged()<2){
            reservationService.deleteReservation(insured.getReservation());
            insured.setReservation(null);
            insured.increaseTimesReservationChanged();
            return "Reservation unselected";
        }else{
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,"Insured with with AMKA: "+ insuredAmka+" cant change reservation over 2 times");

        }

    }
}
