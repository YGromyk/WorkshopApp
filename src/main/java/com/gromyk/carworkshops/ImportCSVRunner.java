package com.gromyk.carworkshops;

import com.gromyk.carworkshops.domain.CreateAppointmentUseCase;
import com.gromyk.carworkshops.persistence.entities.Appointment;
import com.gromyk.carworkshops.persistence.entities.Service;
import com.gromyk.carworkshops.persistence.entities.Workshop;
import com.gromyk.carworkshops.persistence.repository.AppointmentsRepository;
import com.gromyk.carworkshops.persistence.repository.ServicesRepository;
import com.gromyk.carworkshops.persistence.repository.WorkshopsRepository;
import com.gromyk.carworkshops.rest.dtos.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ImportCSVRunner implements CommandLineRunner {
    private final AppointmentsRepository appointmentsRepository;
    private final WorkshopsRepository workshopsRepository;
    private final ServicesRepository servicesRepository;

    private final CreateAppointmentUseCase createAppointmentUseCase;

    @Autowired
    public ImportCSVRunner(AppointmentsRepository appointmentsRepository, WorkshopsRepository workshopsRepository, ServicesRepository servicesRepository, CreateAppointmentUseCase createAppointmentUseCase) {
        this.appointmentsRepository = appointmentsRepository;
        this.workshopsRepository = workshopsRepository;
        this.servicesRepository = servicesRepository;
        this.createAppointmentUseCase = createAppointmentUseCase;
    }

    @Override
    public void run(String... args) throws Exception {
        readAppointmentsFromResources();
    }

    private void readAppointmentsFromResources() {
        Workshop schmidt = workshopsRepository.save(new Workshop("schmidt", "Autohaus-Schmidt", "autohaus-schmidt auf der Strasse...", 2, LocalTime.of(8, 0), LocalTime.of(19, 0)));
        Workshop bachstrasse = workshopsRepository.save(new Workshop("bachstrasse", "Meisterbetrieb-Bachstraße", "Meisterbetrieb auf der Bachstrasse...", 3, LocalTime.of(8, 0), LocalTime.of(20, 0)));

        Service oilChangeSchmidt = new Service("MOT", "Motorinstandsetzung", 4 * 60, schmidt);
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

//        importFile("classpath:autohaus-schmidt.csv", schmidt);
//        importFile("classpath:meisterbetrieb-bachstraße.csv", bachstrasse);
    }

    private void importFile(String fileName, Workshop workshop) {
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

                AppointmentRequest appointment = new AppointmentRequest();
                appointment.setStartTime(LocalDateTime.parse(values[0], DateTimeFormatter.ISO_ZONED_DATE_TIME));
                appointment.setServiceId(values[1]);
                createAppointmentUseCase.createAppointment(workshop.getId(), appointment);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
