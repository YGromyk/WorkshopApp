package com.gromyk.carworkshops.rest;

import com.gromyk.carworkshops.persistence.entities.Appointment;
import com.gromyk.carworkshops.persistence.repository.AppointmentsRepository;
import com.gromyk.carworkshops.rest.dtos.AppointmentSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;

@RestController
public class AppointmentController {
    private final AppointmentsRepository repository;

    @Autowired
    public AppointmentController(AppointmentsRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/termins", produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> getAppointments() {
        return repository.findAll().stream().map(it -> it.getService() + " in " + it.getWorkshopName()).toList();
    }

    @RequestMapping(value = "/werkstatt/{workshopId}/termin",consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public @ResponseBody int getAttr(
            @PathVariable(value = "workshopId") String workshopId,
            @RequestBody AppointmentSuggestion appointmentSuggestion
    ) {
        System.out.println(appointmentSuggestion.getServiceId());
        return 0;
    }

}
