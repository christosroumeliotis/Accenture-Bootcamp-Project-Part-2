package com.accenture.part2.services;

import com.accenture.part2.models.Timeslot;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.accenture.part2.Constants.FOUND_TIMESLOT_BUT_NOT_AVAILABLE;
import static com.accenture.part2.Constants.NOT_FIND_TIMESLOT;

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
                if (t.isAvailable()) {
                    return t;
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(FOUND_TIMESLOT_BUT_NOT_AVAILABLE, timeslot.getDate()));
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FIND_TIMESLOT + ":" + timeslot.getDate());
    }

    public Timeslot getAvailableTimeslotByDate(String date) {
        for (Timeslot t : timeslots) {
            String s = t.getDate();
            if (s.equals(date)) {
                if (t.isAvailable()) {
                    return t;
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(FOUND_TIMESLOT_BUT_NOT_AVAILABLE, date));
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FIND_TIMESLOT + ":" + date);
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
