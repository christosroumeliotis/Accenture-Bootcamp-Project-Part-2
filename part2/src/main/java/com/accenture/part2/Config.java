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
            Doctor doctor1 = new Doctor("amka1", "Leon", "Pavlou");
            Doctor doctor2 = new Doctor("amka2", "Neo", "Petrou");
            Insured insured1 = new Insured("133569710", "amka1", "Nikos", "1223", "Anagnostou", "nikos@gmail");
            Insured insured2 = new Insured("185411269", "amka2", "Petros", "1223", "Papadakhs", "petros@gmail");
            Insured insured3 = new Insured("145286903", "amka3", "Maria", "1223", "Sofou", "maria@gmail");
            Insured insured4 = new Insured("123457891", "amka4", "Marialena", "1223", "Arvaniti", "marialena@gmail");

            insuredService.addInsured(insured1);
            insuredService.addInsured(insured2);
            insuredService.addInsured(insured3);
            insuredService.addInsured(insured4);

            doctorService.addDoctor(doctor1);
            doctorService.addDoctor(doctor2);

            timeslotService.addTimeslot(new Timeslot("dc1", "27/4/2024 10:30", 40));
            timeslotService.addTimeslot(new Timeslot("dc2", "27/4/2024 11:30", 40));
            timeslotService.addTimeslot(new Timeslot("dc3", "28/4/2024 10:30", 40));
            timeslotService.addTimeslot(new Timeslot("dc4", "29/5/2024 10:30", 40));
            Timeslot timeslot1 = new Timeslot("cd1", "20/5/2024 10:30", 40);
            Timeslot timeslot2 = new Timeslot("cd2", "28/5/2024 13:30", 40);
            Timeslot timeslot3 = new Timeslot("cd3", "28/4/2024 12:30", 40);
            Timeslot timeslot4 = new Timeslot("cd4", "30/4/2024 10:30", 40);
            timeslotService.addTimeslot(timeslot1);
            timeslotService.addTimeslot(timeslot2);
            timeslotService.addTimeslot(timeslot3);
            timeslotService.addTimeslot(timeslot4);


            insuredService.makeAReservation("amka1", "28/4/2024 12:30", "amka1");
            insuredService.makeAReservation("amka2", "28/4/2024 10:30", "amka1");
            insuredService.makeAReservation("amka3", "20/5/2024 10:30", "amka2");

        };
    }
}
