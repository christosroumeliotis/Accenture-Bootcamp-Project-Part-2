package com.accenture.part2;

import com.accenture.part2.models.Doctor;
import com.accenture.part2.models.Insured;
import com.accenture.part2.models.Timeslot;
import com.accenture.part2.services.DoctorService;
import com.accenture.part2.services.InsuredService;
import com.accenture.part2.services.TimeslotService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class Config {
    //gia na kanoume arxikopoihsh thn listas addresses
    @Bean//xrisimopoieitai mesa se config kai services //na trexei san apomonomeno apo ta ipoloipa
    public CommandLineRunner commandLineRunner(InsuredService insuredService, DoctorService doctorService, TimeslotService timeslotService){
        return args -> {
            insuredService.addInsured(new Insured("123456789","amka1","Nikos","23/4/1999","Giannou","sdn@gmail.com"));
            insuredService.addInsured(new Insured("123456789","amka2","Giannis","23/4/1999","Giannou","sdn@gmail.com"));
            insuredService.addInsured(new Insured("123456789","amka3","Maria","23/4/1999","Giannou","sdn@gmail.com"));

            doctorService.addDoctor(new Doctor("amka1","leo","pavlou"));

            timeslotService.addTimeslot(new Timeslot("dc1","20/4/2023 10:30",40));
            timeslotService.addTimeslot(new Timeslot("dc2","21/4/2023 10:30",40));
            timeslotService.addTimeslot(new Timeslot("dc3","22/4/2023 10:30",40));
            timeslotService.addTimeslot(new Timeslot("dc4","20/5/2023 10:30",40));
        };
    }
}
