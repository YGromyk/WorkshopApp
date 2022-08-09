package com.gromyk.carworkshops.rest;

import com.gromyk.carworkshops.DateHelper;
import com.gromyk.carworkshops.domain.CreateAppointmentUseCase;
import com.gromyk.carworkshops.domain.FilterAppointmentsUseCase;
import com.gromyk.carworkshops.persistence.entities.Appointment;
import com.gromyk.carworkshops.persistence.repository.AppointmentsRepository;
import com.gromyk.carworkshops.rest.dtos.AppointmentRecommendation;
import com.gromyk.carworkshops.rest.dtos.AppointmentRequest;
import com.gromyk.carworkshops.rest.dtos.AppointmentDTO;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Autowired
    public AppointmentController(AppointmentsRepository repository, FilterAppointmentsUseCase filterAppointmentsUseCase, CreateAppointmentUseCase createAppointmentUseCase, ModelMapper modelMapper) {
        this.repository = repository;
        this.filterAppointmentsUseCase = filterAppointmentsUseCase;
        this.createAppointmentUseCase = createAppointmentUseCase;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/werkstatt/{workshopId}/termin", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public @ResponseBody AppointmentDTO createAppointment(
            @PathVariable(value = "workshopId") String workshopId,
            @RequestBody AppointmentRequest appointmentRequest
    ) {
        return getAppointmentDTO(createAppointmentUseCase.createAppointment(workshopId, appointmentRequest));
    }

    @RequestMapping(value = "/werkstatt/{workshopId}/termine", method = RequestMethod.GET)
    public List<AppointmentDTO> getAllAppointmentsFor(
            @PathVariable(value = "workshopId") String workshop,
            @RequestParam(name = "leistungId", required = false) String serviceId,
            @RequestParam(name = "von", required = false) @DateTimeFormat(pattern = DateHelper.DATE_FORMAT) LocalDateTime fromTime,
            @RequestParam(name = "bis", required = false) @DateTimeFormat(pattern = DateHelper.DATE_FORMAT) LocalDateTime untilTime
    ) {
        return filterAppointmentsUseCase.run(workshop, serviceId, fromTime, untilTime).stream()
                .map(this::getAppointmentDTO)
                .toList();
    }

    private AppointmentDTO getAppointmentDTO(Appointment it) {
        AppointmentDTO mapped = modelMapper.map(it, AppointmentDTO.class);
        mapped.setServiceCode(it.getServiceToDo().getServiceName());
        return mapped;
    }

    @RequestMapping(value = "/werkstatt/{workshopId}/termin/{terminId}", method = RequestMethod.GET)
    public AppointmentDTO remove(
            @PathVariable(value = "workshopId") String workshop,
            @PathVariable(value = "terminId") Long appointmentId

    ) {
        Appointment appointmentToRemove = repository.findById(appointmentId).get();
        if (appointmentToRemove.getWorkshopId().equals(workshop)) {
            repository.delete(appointmentToRemove);
        }
        return getAppointmentDTO(appointmentToRemove);
    }

    @RequestMapping(value = "/werkstatt/{workshopId}/terminvorschlag", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AppointmentRecommendation> getRecommendations(
            @PathVariable(value = "workshopId") String workshop,
            @RequestParam(name = "leistungId", required = false) String serviceId,
            @RequestParam(name = "von", required = false)
            @DateTimeFormat(pattern = DateHelper.DATE_FORMAT) LocalDateTime fromTime,
            @RequestParam(name = "bis", required = false)
            @DateTimeFormat(pattern = DateHelper.DATE_FORMAT) LocalDateTime untilTime
    ) {
        return createAppointmentUseCase.getRecommendation(workshop, serviceId, fromTime, untilTime).stream()
                .map(it -> {
                    AppointmentRecommendation mapped = modelMapper.map(it, AppointmentRecommendation.class);
                    mapped.setServiceCode(it.getService().getServiceName());
                    mapped.setService(it.getService().getDescription());
                    return mapped;
                })
                .toList();
    }
}
