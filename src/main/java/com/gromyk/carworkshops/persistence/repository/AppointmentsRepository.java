package com.gromyk.carworkshops.persistence.repository;

import com.gromyk.carworkshops.persistence.entities.Appointment;
import com.gromyk.carworkshops.persistence.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByWorkshopIdAndServiceToDoAndStartTimeEqualsOrStartTimeAfter(String workshop, Service serviceToDo, LocalDateTime startTime, LocalDateTime startTimeAgain);
    default List<Appointment> findAppointmentsByWorkshopServiceAndAfterDate(String workshop, Service serviceToDo, LocalDateTime startTime) {
        return findByWorkshopIdAndServiceToDoAndStartTimeEqualsOrStartTimeAfter(workshop, serviceToDo, startTime, startTime);
    }

    List<Appointment> findByWorkshopId(String workshop);

}
