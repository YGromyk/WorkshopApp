package com.gromyk.carworkshops.domain;

import com.gromyk.carworkshops.persistence.entities.Appointment;
import com.gromyk.carworkshops.persistence.repository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class FilterAppointmentsUseCase {
    private final AppointmentsRepository appointmentsRepository;

    @Autowired
    public FilterAppointmentsUseCase(AppointmentsRepository appointmentsRepository) {
        this.appointmentsRepository = appointmentsRepository;
    }

    public List<Appointment> run(String workshopName, String serviceId, LocalDateTime fromTime, LocalDateTime untilTime) {
        return appointmentsRepository.findByWorkshopId(workshopName).stream()
                .filter(it -> {
                    if (serviceId == null) return true;
                    return it.getServiceToDo().getServiceName().equals(serviceId);
                })
                .filter(it -> {
                    if (fromTime == null) return true;
                    return it.getStartTime().isAfter(fromTime) || it.getStartTime().isEqual(fromTime);
                })
                .filter(it -> {
                    if (untilTime == null) return true;
                    return it.getEndTime().isBefore(untilTime) || it.getEndTime().isEqual(untilTime);
                })
                .toList();
    }
}
