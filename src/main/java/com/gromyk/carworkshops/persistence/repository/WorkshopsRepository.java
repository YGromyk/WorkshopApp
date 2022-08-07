package com.gromyk.carworkshops.persistence.repository;

import com.gromyk.carworkshops.persistence.entities.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopsRepository extends JpaRepository<Workshop, String> {
}
