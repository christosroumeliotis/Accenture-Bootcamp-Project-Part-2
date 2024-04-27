package com.accenture.part2.services;

import com.accenture.part2.Constants;
import com.accenture.part2.models.Doctor;
import com.accenture.part2.models.Insured;
import com.accenture.part2.models.Reservation;
import com.accenture.part2.models.Timeslot;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.accenture.part2.Constants.*;

@Service
public class ReservationService {


    @Autowired
    DoctorService doctorService;
    List<Reservation> reservations = new ArrayList<>();

    public Reservation addReservation(Insured insured, Timeslot timeslot) {
        Reservation r = new Reservation(insured, timeslot);
        reservations.add(r);
        return r;
    }

    public void deleteReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<Reservation> getUpcomingReservations() {
        List<Reservation> upcomingReservations = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy HH:mm");
        Date now = new Date();
        for (Reservation reservation : reservations) {
            try {
                if (simpleDateFormat.parse(reservation.getTimeslot().getDate()).after(now)) {
                    upcomingReservations.add(reservation);
                }
            } catch (ParseException exc) {
                // Do nothing
            }
        }
        if (upcomingReservations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.N0_RESERVATIONS_FOUND);
        }
        return upcomingReservations;

    }

    public List<Reservation> getReservationsByDate(String date) {
        List<Reservation> reservationsByDate = new ArrayList<>();
        for (Reservation reservation : reservations) {
            String reservationDate = reservation.getTimeslot().getDate().substring(0, reservation.getTimeslot().getDate().indexOf(" "));
            if (reservationDate.equals(date)) {
                reservationsByDate.add(reservation);
            }
        }
        if (reservationsByDate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_RESERVASIONS_FOR_DATE + ": " + date);
        }
        return reservationsByDate;
    }

    public List<Reservation> getAllReservations(int page, int pageSize) {
        int totalPages = (int) Math.ceil((double) reservations.size() / pageSize);

        if (page < 1 || page > totalPages) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.N0_RESERVATIONS_FOUND);
        }

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, reservations.size());

        return reservations.subList(startIndex, endIndex);

    }


    public void exportAllReservationsToPdf(HttpServletResponse response, String doctorAmka, String date) {
        if (!doctorAmkaExists(doctorAmka)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.DOCTOR_AMKA_NOT_FOUND);
        }
        try {
            List<Reservation> doctorReservationsByDate = getDoctorReservations(doctorAmka, date);

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph(DOCTOR_AMKA + ": " + doctorAmka));
            document.add(new Paragraph(NUMBER_OF_RESERVATIONS + ": " + doctorReservationsByDate.size()));
            if (!doctorReservationsByDate.isEmpty()) {
                document.add(new Paragraph(TIMESLOTS_HEADER));
                for (int i = 0; i < doctorReservationsByDate.size(); i++) {
                    document.add(new Paragraph((i + 1) + ") " + doctorReservationsByDate.get(i).getTimeslot().toPdfFormat()));
                }
            }

            document.close();
        } catch (IOException exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, PDF_ERROR);
        }
    }

    private boolean doctorAmkaExists(String amka) {
        for (Doctor doctor : doctorService.doctors) {
            if (doctor.getAmka().equals(amka)) {
                return true;
            }
        }
        return false;
    }

    private List<Reservation> getDoctorReservations(String doctorAmka, String date) {
        List<Reservation> doctorReservationsByDate = new ArrayList<>();
        for (Reservation reservation : reservations) {
            String reservationDate = reservation.getTimeslot().getDate().substring(0, reservation.getTimeslot().getDate().indexOf(" "));
            if (reservationDate.equals(date) && (reservation.getInsured().getDoctor().getAmka().equals(doctorAmka))) {
                doctorReservationsByDate.add(reservation);
            }
        }
        return doctorReservationsByDate;
    }
}
