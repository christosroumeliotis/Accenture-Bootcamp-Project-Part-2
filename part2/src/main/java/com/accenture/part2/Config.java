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
    @Bean
    public CommandLineRunner commandLineRunner(InsuredService insuredService, DoctorService doctorService, TimeslotService timeslotService, ReservationService reservationService) {
        return args -> {
            Doctor doctor1 = new Doctor("amka1", "Leo", "Pavlou");
            Doctor doctor2 = new Doctor("amka2", "Neo", "Petrou");
            Insured insured1 = new Insured("133569710", "amka1", "Nikos", "20/8/1999", "Anagnostou", "nikos@gmail");
            Insured insured2 = new Insured("185411269", "amka2", "Petros", "15/6/1992", "Papadakhs", "petros@gmail");
            Insured insured3 = new Insured("145286903", "amka3", "Maria", "21/2/1997", "Sofou", "maria@gmail");
            Insured insured4 = new Insured("123457891", "amka4", "Marialena", "30/8/1985", "Arvaniti", "marialena@gmail");
            Insured insured5 = new Insured("123057891", "amka6", "Danae", "12/8/1995", "Xiarxou", "danae@gmail");

            insuredService.addInsured(insured1);
            insuredService.addInsured(insured2);
            insuredService.addInsured(insured3);
            insuredService.addInsured(insured4);
            insuredService.addInsured(insured5);
            doctorService.addDoctor(doctor1);
            doctorService.addDoctor(doctor2);


            Timeslot timeslot1 = new Timeslot("cd1", "29/4/2024 11:30");
            Timeslot timeslot2 = new Timeslot("cd2", "28/4/2024 13:30");
            Timeslot timeslot3 = new Timeslot("cd3", "28/4/2024 10:30");
            Timeslot timeslot4 = new Timeslot("cd4", "2/5/2024 20:30");
            Timeslot timeslot5 = new Timeslot("cd5", "26/4/2024 21:30");
            Timeslot timeslot7 = new Timeslot("dc1", "24/5/2024 10:30");
            Timeslot timeslot8 = new Timeslot("dc2", "29/4/2024 10:30");
            Timeslot timeslot9 = new Timeslot("dc3", "22/5/2024 10:30");
            Timeslot timeslot6 = new Timeslot("dc4", "20/5/2024 10:30");
            Timeslot timeslot10 = new Timeslot("dc5", "20/3/2024 10:30");
            timeslotService.addTimeslot(timeslot1);
            timeslotService.addTimeslot(timeslot2);
            timeslotService.addTimeslot(timeslot3);
            timeslotService.addTimeslot(timeslot4);
            timeslotService.addTimeslot(timeslot5);
            timeslotService.addTimeslot(timeslot6);
            timeslotService.addTimeslot(timeslot7);
            timeslotService.addTimeslot(timeslot8);
            timeslotService.addTimeslot(timeslot9);
            timeslotService.addTimeslot(timeslot10);


            insuredService.makeAReservation(insured1.getAmka(), timeslot2.getDate(), doctor1.getAmka());
            insuredService.makeAReservation(insured2.getAmka(), timeslot3.getDate(), doctor1.getAmka());
            insuredService.makeAReservation(insured3.getAmka(), timeslot6.getDate(), doctor2.getAmka());
            insuredService.makeAReservation(insured5.getAmka(), timeslot10.getDate(), doctor2.getAmka());


        };
    }
}
