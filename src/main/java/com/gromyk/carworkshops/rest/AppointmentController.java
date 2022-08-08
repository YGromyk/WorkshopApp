package com.gromyk.carworkshops.rest;

import com.gromyk.carworkshops.DateHelper;
import com.gromyk.carworkshops.domain.CreateAppointmentUseCase;
import com.gromyk.carworkshops.domain.FilterAppointmentsUseCase;
import com.gromyk.carworkshops.persistence.entities.Appointment;
import com.gromyk.carworkshops.persistence.repository.AppointmentsRepository;
import com.gromyk.carworkshops.rest.dtos.AppointmentRecommendation;
import com.gromyk.carworkshops.rest.dtos.AppointmentRequest;
import com.gromyk.carworkshops.rest.dtos.AppointmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AppointmentController {
    private final AppointmentsRepository repository;
    private final FilterAppointmentsUseCase filterAppointmentsUseCase;
    private final CreateAppointmentUseCase createAppointmentUseCase;

    @Autowired
    public AppointmentController(AppointmentsRepository repository, FilterAppointmentsUseCase filterAppointmentsUseCase, CreateAppointmentUseCase createAppointmentUseCase) {
        this.repository = repository;
        this.filterAppointmentsUseCase = filterAppointmentsUseCase;
        this.createAppointmentUseCase = createAppointmentUseCase;
    }

    @RequestMapping(value = "/termins", produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> getAppointments() {
        return repository.findAll().stream().map(it -> it.getService() + " in " + it.getWorkshopId()).toList();
    }

    @RequestMapping(value = "/werkstatt/{workshopId}/termin", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public @ResponseBody AppointmentResponse createAppointment(
            @PathVariable(value = "workshopId") String workshopId,
            @RequestBody AppointmentRequest appointmentRequest
    ) {
        return mapToResponse(createAppointmentUseCase.createAppointment(workshopId, appointmentRequest));
    }

    @RequestMapping(value = "/werkstatt/{workshopId}/termine", method = RequestMethod.GET)
    public List<AppointmentResponse> getAllAppointmentsFor(
            @PathVariable(value = "workshopId") String workshop,
            @RequestParam(name = "leistungId", required = false) String serviceId,
            @RequestParam(name = "von", required = false) @DateTimeFormat(pattern = DateHelper.DATE_FORMAT) LocalDateTime fromTime,
            @RequestParam(name = "bis", required = false) @DateTimeFormat(pattern = DateHelper.DATE_FORMAT) LocalDateTime untilTime
    ) {
        return filterAppointmentsUseCase.run(workshop, serviceId, fromTime, untilTime).stream().map(it -> mapToResponse(it)).toList();
    }

    @RequestMapping(value = "/werkstatt/{workshopId}/termin/{terminId}", method = RequestMethod.GET)
    public AppointmentResponse remove(
            @PathVariable(value = "workshopId") String workshop,
            @PathVariable(value = "terminId") Long appointmentId

    ) {
        Appointment appointmentToRemove = repository.findById(appointmentId).get();
        if (appointmentToRemove.getWorkshopId().equals(workshop)) {
            repository.delete(appointmentToRemove);
        }
        return mapToResponse(appointmentToRemove);
    }

    @RequestMapping(value = "/werkstatt/{workshopId}/terminvorschlag", method = RequestMethod.GET)
    public List<AppointmentRecommendation> getRecommendations(
            @PathVariable(value = "workshopId") String workshop,
            @RequestParam(name = "leistungId", required = false) String serviceId,
            @RequestParam(name = "von", required = false) @DateTimeFormat(pattern = DateHelper.DATE_FORMAT) LocalDateTime fromTime,
            @RequestParam(name = "bis", required = false) @DateTimeFormat(pattern = DateHelper.DATE_FORMAT) LocalDateTime untilTime
    ) {
        return createAppointmentUseCase.getRecommendation(workshop, serviceId, fromTime, untilTime).stream().map(AppointmentController::mapToRecommendation).toList();
    }


    private static AppointmentResponse mapToResponse(Appointment it) {
        AppointmentResponse appointmentResponse = new AppointmentResponse();
        appointmentResponse.setId(it.getId());
        appointmentResponse.setServiceCode(it.getServiceToDo().getServiceName());
        appointmentResponse.setWorkshopId(it.getWorkshopId());
        appointmentResponse.setStartTime(it.getStartTime());
        appointmentResponse.setEndTime(it.getEndTime());
        return appointmentResponse;
    }

    private static AppointmentRecommendation mapToRecommendation(Appointment it) {
        AppointmentRecommendation appointmentResponse = new AppointmentRecommendation();
        appointmentResponse.setServiceCode(it.getServiceToDo().getServiceName());
        appointmentResponse.setWorkshopName(it.getWorkshopId());
        appointmentResponse.setService(it.getService().getDescription());
        appointmentResponse.setStartTime(it.getStartTime());
        appointmentResponse.setEndTime(it.getEndTime());
        return appointmentResponse;
    }


}
