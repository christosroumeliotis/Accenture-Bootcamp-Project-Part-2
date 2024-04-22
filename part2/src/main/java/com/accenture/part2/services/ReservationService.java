package com.accenture.part2.services;

import com.accenture.part2.Constants;
import com.accenture.part2.models.Insured;
import com.accenture.part2.models.Reservation;
import com.accenture.part2.models.Timeslot;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.accenture.part2.Constants.*;

@Service
public class ReservationService {


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
        LocalDate now = LocalDate.now();
        for (Reservation reservation : reservations) {
            if (LocalDate.parse(reservation.getTimeslot().getDate(), DateTimeFormatter.ofPattern("d/M/yyyy HH:mm")).isAfter(now)) {
                upcomingReservations.add(reservation);
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

    public List<Reservation> getAllReservations(int page) {
        int pageSize = 3;
        int totalPages = (int) Math.ceil((double) reservations.size() / pageSize);

        if (page < 1 || page > totalPages) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.N0_RESERVATIONS_FOUND);
        }

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, reservations.size());

        return reservations.subList(startIndex, endIndex);
    }


    public void exportAllReservationsToPdf(HttpServletResponse response, String doctorAmka, String date) throws IOException {
        if (!doctorAmkaExists(doctorAmka)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.DOCTOR_AMKA_NOT_FOUND);
        }

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        List<Reservation> doctorReservationsByDate = getDoctorReservations(doctorAmka, date);
        document.open();
        document.add(new Paragraph("Doctor Amka: " + doctorAmka));
        document.add(new Paragraph("Number of Reservations: " + doctorReservationsByDate.size()));
        if (!doctorReservationsByDate.isEmpty()) {
            document.add(new Paragraph("------------ Timeslots ------------"));
            for (int i = 0; i < doctorReservationsByDate.size(); i++) {
                document.add(new Paragraph((i + 1) + ") " + doctorReservationsByDate.get(i).getTimeslot().toPdfFormat()));
            }
        }
        document.close();
    }

    private boolean doctorAmkaExists(String amka) {
        for (Reservation reservation : reservations) {
            if (reservation.getInsured().getDoctor().getAmka().equals(amka)) {
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
