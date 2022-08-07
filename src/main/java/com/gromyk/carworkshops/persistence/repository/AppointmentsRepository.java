package com.gromyk.carworkshops.persistence.repository;

import com.gromyk.carworkshops.persistence.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentsRepository extends JpaRepository<Appointment, Long> {
}
