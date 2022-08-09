package com.gromyk.carworkshops.domain;

import com.gromyk.carworkshops.domain.exceptions.TimeConflictException;
import com.gromyk.carworkshops.persistence.entities.Appointment;
import com.gromyk.carworkshops.persistence.entities.Service;
import com.gromyk.carworkshops.persistence.entities.Workshop;
import com.gromyk.carworkshops.persistence.repository.AppointmentsRepository;
import com.gromyk.carworkshops.persistence.repository.ServicesRepository;
import com.gromyk.carworkshops.persistence.repository.WorkshopsRepository;
import com.gromyk.carworkshops.rest.dtos.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.stereotype.Service
public class CreateAppointmentUseCase {
    private final AppointmentsRepository appointmentsRepository;
    private final ServicesRepository servicesRepository;
    private final WorkshopsRepository workshopsRepository;

    @Autowired
    public CreateAppointmentUseCase(AppointmentsRepository appointmentsRepository, ServicesRepository servicesRepository, WorkshopsRepository workshopsRepository) {
        this.appointmentsRepository = appointmentsRepository;
        this.servicesRepository = servicesRepository;
        this.workshopsRepository = workshopsRepository;
    }

    public Appointment createAppointment(String workshopId, AppointmentRequest appointmentRequest) {
        Workshop workshop = workshopsRepository.findById(workshopId).orElseThrow(() -> new IllegalArgumentException("Wrong workshop id"));

        Appointment appointmentToCreate = fillUpAppointment(workshop, appointmentRequest);

        if (isTimeOutBusinessHours(appointmentToCreate.getStartTime().toLocalTime(), appointmentToCreate.getEndTime().toLocalTime(), workshop)) {
            throw new TimeConflictException("Please, create an appointment during the working hours!");
        } else if (!isNotOverloaded(workshop, appointmentToCreate)) {
            throw new TimeConflictException("All garages are busy in that timespan!");
        }

        return appointmentsRepository.save(appointmentToCreate);
    }

    private boolean isNotOverloaded(Workshop workshop, Appointment appointmentToCreate) {
        List<Appointment> appointmentsAfter = appointmentsRepository.findByWorkshopId(workshop.getId());
        return findParallelAppointments(appointmentsAfter, workshop.getMaximumParallelServices(), appointmentToCreate);

    }

    private Appointment fillUpAppointment(Workshop workshop, AppointmentRequest appointmentRequest) {
        Service service = servicesRepository.findByWorkshopAndServiceName(workshop, appointmentRequest.getServiceId());
        int serviceDurationInMin = service.getDurationOfServiceInMin();
        Appointment appointmentToCreate = new Appointment();
        appointmentToCreate.setWorkshopId(workshop.getId());
        appointmentToCreate.setServiceToDo(service);
        appointmentToCreate.setStartTime(generateStartTime(appointmentRequest.getStartTime()));
        LocalDateTime expectedEndTime = appointmentToCreate.getStartTime().plusMinutes(serviceDurationInMin);
        appointmentToCreate.setEndTime(expectedEndTime);
        return appointmentToCreate;
    }

    private boolean isTimeOutBusinessHours(LocalTime from, LocalTime to, Workshop workshop) {
        boolean timeIsSameOrAfter = workshop.getStartOfADay().isBefore(from) || workshop.getStartOfADay().equals(from);
        boolean timeIsBeforeEndOfDay = workshop.getEndOfADay().isAfter(to) || workshop.getEndOfADay().equals(to);

        return !timeIsSameOrAfter || !timeIsBeforeEndOfDay;
    }

    private boolean findParallelAppointments(List<Appointment> appointments, int maxParallelWorks, Appointment appointmentToCreate) {
        AtomicInteger parallelAppointment = new AtomicInteger();
        LocalDateTime newStart = appointmentToCreate.getStartTime();
        LocalDateTime newEnd = appointmentToCreate.getEndTime();
        appointments.forEach(appointment -> {
            LocalDateTime currentStart = appointment.getStartTime();
            LocalDateTime currentEnd = appointment.getEndTime();
            boolean newStartInRange = within(newStart, currentStart, currentEnd);
            boolean newEndInRange = within(newEnd, currentStart, currentEnd);
            if (newStartInRange || newEndInRange) {
                parallelAppointment.incrementAndGet();
            }
        });
        return parallelAppointment.get() < maxParallelWorks;
    }

    public static boolean within(
            LocalDateTime toCheck,
            LocalDateTime startInterval,
            LocalDateTime endInterval) {
        return toCheck.compareTo(startInterval) >= 0 && toCheck.compareTo(endInterval) < 0;
    }

    private LocalDateTime generateStartTime(LocalDateTime startTime) {
        int minutes = startTime.getMinute();
        if (minutes == 0) return startTime;

        while (minutes > 15) {
            minutes -= 15;
        }
        return startTime.plusMinutes(15 - minutes);
    }

    public List<Appointment> getRecommendation(String workshopId, String serviceId, LocalDateTime from, LocalDateTime to) {
        Workshop workshop = workshopsRepository.findById(workshopId).orElseThrow(() -> new IllegalArgumentException("Wrong workshop id"));

        // adjust time here, instead using SpEl
        if (from == null) {
            from = LocalDateTime.now();
        }
        if (to == null) {
            to = from.withHour(workshop.getEndOfADay().getHour());
            to = to.withMinute(workshop.getEndOfADay().getMinute());
        }
        if (isTimeOutBusinessHours(from.toLocalTime(), to.toLocalTime(), workshop)) {
            from = from.withHour(workshop.getStartOfADay().getHour());
            from = from.withMinute(workshop.getStartOfADay().getMinute());
        }

        Service service = servicesRepository.findByWorkshopAndServiceName(workshop, serviceId);
        return generateAppointments(workshop, service, from, to);
    }

    private List<Appointment> generateAppointments(Workshop workshop, Service service, LocalDateTime from, LocalDateTime to) {
        List<Appointment> appointmentRequests = new ArrayList<>();
        LocalDateTime tempDate = from;
        while (true) {
            tempDate = generateStartTime(tempDate);
            AppointmentRequest appointmentRequest = new AppointmentRequest();
            appointmentRequest.setServiceId(service.getServiceName());
            appointmentRequest.setStartTime(tempDate);
            Appointment appointment = fillUpAppointment(workshop, appointmentRequest);

            if (appointment.getEndTime().isAfter(to)) break;

            LocalTime startTime = appointment.getStartTime().toLocalTime();
            LocalTime endTime = appointment.getEndTime().toLocalTime();

            boolean isOutOfBusinessHours = isTimeOutBusinessHours(startTime, endTime, workshop);
            if (isNotOverloaded(workshop, appointment) && !isOutOfBusinessHours) {
                appointmentRequests.add(appointment);
            }
            if (isOutOfBusinessHours) {
                tempDate = tempDate.plusDays(1);
                tempDate = tempDate.withHour(workshop.getStartOfADay().getHour());
                tempDate = tempDate.withMinute(workshop.getStartOfADay().getMinute());
            } else {
                tempDate = tempDate.plusMinutes(1);
            }
        }
        return appointmentRequests;
    }
}
