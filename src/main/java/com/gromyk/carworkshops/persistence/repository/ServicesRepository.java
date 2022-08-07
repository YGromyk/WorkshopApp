package com.gromyk.carworkshops.persistence.repository;

import com.gromyk.carworkshops.persistence.entities.Service;
import com.gromyk.carworkshops.persistence.entities.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Service, Long> {
    Service findByWorkshopAndServiceName(Workshop workshop, String serviceName);

    List<Service> findByWorkshop(Workshop workshop);
}
