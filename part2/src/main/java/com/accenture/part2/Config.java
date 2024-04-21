package com.accenture.part2;

import com.accenture.part2.models.Doctor;
import com.accenture.part2.models.Insured;
import com.accenture.part2.models.Timeslot;
import com.accenture.part2.services.DoctorService;
import com.accenture.part2.services.InsuredService;
import com.accenture.part2.services.ReservationService;
import com.accenture.part2.services.TimeslotService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    //gia na kanoume arxikopoihsh thn listas addresses
    @Bean//xrisimopoieitai mesa se config kai services //na trexei san apomonomeno apo ta ipoloipa
    public CommandLineRunner commandLineRunner(InsuredService insuredService, DoctorService doctorService, TimeslotService timeslotService, ReservationService reservationService) {
        return args -> {
            Doctor doctor1 = new Doctor("amka1", "leo", "pavlou");
            Insured insured1 = new Insured("133569710", "13356971098", "Nikos", "1223", "Anagnostou", "nikos@gmail");
            Insured insured2 = new Insured("185411269", "18541126934", "Petros", "1223", "Papadakhs", "petros@gmail");
            Insured insured3 = new Insured("145286903", "14528690389", "Maria", "1223", "Sofou", "maria@gmail");
            Insured insured4 = new Insured("123457891", "32434232234", "Marialena", "1223", "Arvaniti", "marialena@gmail");
            insured1.setDoctor(doctor1);
            insured2.setDoctor(doctor1);
            insured3.setDoctor(doctor1);
            insured4.setDoctor(doctor1);
            insuredService.addInsured(insured1);
            insuredService.addInsured(insured2);
            insuredService.addInsured(insured3);
            insuredService.addInsured(insured4);

            doctorService.addDoctor(doctor1);

            timeslotService.addTimeslot(new Timeslot("dc1","20/4/2023 10:30",40));
            timeslotService.addTimeslot(new Timeslot("dc2","21/4/2023 10:30",40));
            timeslotService.addTimeslot(new Timeslot("dc3","22/4/2023 10:30",40));
            timeslotService.addTimeslot(new Timeslot("dc4","20/5/2023 10:30",40));
            Timeslot timeslot1 = new Timeslot("cd1", "20/4/2023 10:30", 40);
            Timeslot timeslot2 = new Timeslot("cd2" ,"21/4/2023 10:30", 40);
            Timeslot timeslot3 = new Timeslot("cd3", "28/4/2024 10:30", 40);
            Timeslot timeslot4 = new Timeslot("cd4", "2/12/2024 20:30", 40);
            timeslotService.addTimeslot(timeslot1);
            timeslotService.addTimeslot(timeslot2);
            timeslotService.addTimeslot(timeslot3);

            reservationService.addReservation(insured1, timeslot1);
            reservationService.addReservation(insured2, timeslot2);
            reservationService.addReservation(insured3, timeslot3);
            reservationService.addReservation(insured4, timeslot4);
        };
    }
}
