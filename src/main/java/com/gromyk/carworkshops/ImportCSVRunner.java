package com.gromyk.carworkshops;

import com.gromyk.carworkshops.persistence.entities.Appointment;
import com.gromyk.carworkshops.persistence.entities.Service;
import com.gromyk.carworkshops.persistence.entities.Workshop;
import com.gromyk.carworkshops.persistence.repository.AppointmentsRepository;
import com.gromyk.carworkshops.persistence.repository.ServicesRepository;
import com.gromyk.carworkshops.persistence.repository.WorkshopsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ImportCSVRunner implements CommandLineRunner {
    private final AppointmentsRepository appointmentsRepository;
    private final WorkshopsRepository workshopsRepository;
    private final ServicesRepository servicesRepository;

    public ImportCSVRunner(AppointmentsRepository appointmentsRepository, WorkshopsRepository workshopsRepository, ServicesRepository servicesRepository) {
        this.appointmentsRepository = appointmentsRepository;
        this.workshopsRepository = workshopsRepository;
        this.servicesRepository = servicesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        appointmentsRepository.saveAll(readAppointmentsFromResources());
    }

    private List<Appointment> readAppointmentsFromResources() {
        List<Appointment> appointments = new ArrayList<>();
        Workshop schmidt = workshopsRepository.save(new Workshop("schmidt", "Autohaus-Schmidt", "autohaus-schmidt auf der Strasse...", 2));
        Workshop bachstrasse = workshopsRepository.save(new Workshop("bachstrasse", "Meisterbetrieb-Bachstraße", "Meisterbetrieb auf der Bachstrasse...", 3));

        Service oilChangeSchmidt = new Service("MOT", "Motorinstandsetzung", 4*60, schmidt);
        Service metalRepairSchmidt = new Service("OIL", "Ölwechsel", 15, schmidt);
        Service generalInspectionSchmidt = new Service("WHE", "Radwechsel", 30, schmidt);
        servicesRepository.save(oilChangeSchmidt);
        servicesRepository.save(metalRepairSchmidt);
        servicesRepository.save(generalInspectionSchmidt);

        Service oilChangeBachstrasse = new Service("OIL", "Ölwechsel", 10, bachstrasse);
        Service metalRepairBachstrasse = new Service("FIX", "Blechreparatur", 180, bachstrasse);
        Service generalInspectionBachstrasse = new Service("INS", "Hauptuntersuchung", 60, bachstrasse);
        servicesRepository.save(oilChangeBachstrasse);
        servicesRepository.save(metalRepairBachstrasse);
        servicesRepository.save(generalInspectionBachstrasse);

        appointments.addAll(readFile("classpath:autohaus-schmidt.csv", schmidt));
        appointments.addAll(readFile("classpath:meisterbetrieb-bachstraße.csv", bachstrasse));


        return appointments;
    }

    private List<Appointment> readFile(String fileName, Workshop workshop) {
        List<Appointment> appointments = new ArrayList<>();
        try {
            File workshopFile = ResourceUtils.getFile(fileName);
            Scanner scannerWorkshop = new Scanner(workshopFile);
            scannerWorkshop.useDelimiter(",");
            scannerWorkshop.nextLine(); // skip the column names
            while (scannerWorkshop.hasNext()) {

                String record = scannerWorkshop.nextLine();
                String[] values = record.split(",");
                System.out.println(record);

                Appointment appointment = new Appointment();
                appointment.setStartTime(LocalDateTime.parse(values[0], DateTimeFormatter.ISO_ZONED_DATE_TIME));
                appointment.setService(servicesRepository.findByWorkshopAndServiceName(workshop, values[1]));
                appointment.setWorkshopName(workshop.getName());
                appointments.add(appointment);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        return appointments;
    }
}
