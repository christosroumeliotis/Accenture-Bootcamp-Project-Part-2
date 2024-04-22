package com.accenture.part2.controllers;

import com.accenture.part2.models.*;
import com.accenture.part2.services.InsuredService;
import com.accenture.part2.services.ReservationService;
import com.accenture.part2.services.TimeslotService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/insured")
public class InsuredController {
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";
    @Autowired
    TimeslotService timeslotService;
    @Autowired
    InsuredService insuredService;
    @Autowired
    ReservationService reservationService;

    @GetMapping("/available/date")
    public Timeslot getAvailableTimeslotByDate(@RequestParam String date) {
        return timeslotService.getAvailableTimeslotByDate(date);
    }

    @GetMapping("/available/month")
    public List<Timeslot> getAvailableTimeslotByMonth(@RequestParam int month) {
        return timeslotService.getAvailableTimeslotByMonth(month);
    }

    @PostMapping()
    public List<Insured> addInsured(@RequestBody Insured insured) {
        return insuredService.addInsured(insured);
    }

    @PostMapping("/makeReservation")
    public Reservation makeReservation(@RequestParam String insuredAmka,
                                       @RequestParam String timeslotDate,
                                       @RequestParam String doctorAmka) {
        return insuredService.makeAReservation(insuredAmka, timeslotDate, doctorAmka);
    }

    @DeleteMapping("/unselectReservation")
    public String unselectReservation(@RequestParam String insuredAmka) {
        return insuredService.unselectReservation(insuredAmka);
    }

//    @GetMapping("/vaccinationCoverage")
////    @ResponseBody
//    public Vaccination getInfoOfInsured(@RequestParam String insuredAmka) {
//
//        // VaccinationDTO vaccination = new VaccinationDTO(String amka);
//        for (Insured insured : insuredService.returnAllInsureds()) {
//            //for (Vaccination vacc : vaccinationService.getInfoOfVacc()) {
//            // VaccinationDTO vaccination = null;
//            if (Objects.equals(insured.getAmka(), insuredAmka)) //&& vacc.getInsured().getAmka() == insured.getAmka())
//                if (insured.getVaccinationCoverage() != null) {
//
//                    byte[] image = new byte[0];
//                    try {
//
//                        // Generate and Return Qr Code in Byte Array
//                        image = QRCodeGenerator.getQRCodeImage("/vaccinationcoverage/" + insuredAmka + "/qrcode", 250, 250);
//
//                        // Generate and Save Qr Code Image in static/image folder
//                        QRCodeGenerator.generateQRCodeImage("/vaccinationcoverage/" + insuredAmka + "/qrcode", 250, 250, QR_CODE_IMAGE_PATH);
//
//                    } catch (WriterException | IOException e) {
//                        e.printStackTrace();
//                    }
//                    // Convert Byte Array into Base64 Encode String
//                    String qrcode = Base64.getEncoder().encodeToString(image);
//
//
//                    return insured.getVaccinationCoverage();
////                        return ((new InsuredDTO(insured.getAmka(), insured.getVaccinationCoverage())) (vacc.getExpirationDate()));
////                        return s;
//                } else
//                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This insured with AMKA: " + insuredAmka + ", hasn't vaccinated yet!");
//
//        }
//        //}
//
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error! This insured with AMKA: " + insuredAmka + ", doesn't exists!");
//
//    }
    @GetMapping("/vaccinationCoverage")
//    @ResponseBody
    public ResponseEntity<?> getInfoOfInsured(@RequestParam String insuredAmka) {
        String image= insuredService.getInfoOfInsured(insuredAmka);

        File file = new File(image);

        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(Files.probeContentType(Paths.get(file.getAbsolutePath()))))
                    .body(new UrlResource(file.toURI()));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
    }
}
