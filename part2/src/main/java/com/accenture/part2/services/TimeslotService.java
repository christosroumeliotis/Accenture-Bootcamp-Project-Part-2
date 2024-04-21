package com.accenture.part2.services;

import com.accenture.part2.models.Timeslot;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeslotService {

    List<Timeslot> timeslots = new ArrayList<>();


    public List<Timeslot> getAllTimeslots() {
        return timeslots;
    }

    public List<Timeslot> addTimeslot(Timeslot timeslot) {
        timeslots.add(timeslot);
        return timeslots;
    }

    public Timeslot getTimeslot(Timeslot timeslot) {
        for (Timeslot t : timeslots) {
            if (t.getDate().equals(timeslot.getDate())) {
                if (t.isAvailable() == true) {
                    return t;
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Found timeslot at: " + timeslot.getDate() + " but its not available");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find timeslot at: " + timeslot.getDate());

    }

    public Timeslot getAvailableTimeslotByDate(String date) {
        for (Timeslot t : timeslots) {
            String s = t.getDate();
            if (s.equals(date)) {
                if (t.isAvailable() == true) {
                    return t;
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Found timeslot at: " + date + " but its not available");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find timeslot at: " + date);//NA ROTHSW POS THA EMTHANIZETAI TO MESSAGE
    }

    public List<Timeslot> getAvailableTimeslotByMonth(int month) {
        List<Timeslot> timeslotsMonth = new ArrayList<>();
        for (Timeslot t : timeslots) {
            String[] arrOfStr = t.getDate().split("/", 3);
            if (month == Integer.parseInt(arrOfStr[1]) && t.isAvailable()) {
                timeslotsMonth.add(t);
            }
        }
        return timeslotsMonth;
    }
}
