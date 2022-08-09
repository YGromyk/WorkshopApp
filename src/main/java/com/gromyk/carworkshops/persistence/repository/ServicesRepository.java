package com.gromyk.carworkshops.persistence.repository;

import com.gromyk.carworkshops.persistence.entities.Service;
import com.gromyk.carworkshops.persistence.entities.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Service, Long> {
    Service findByWorkshopAndServiceName(Workshop workshop, String serviceName);

    List<Service> findByWorkshop(Workshop workshop);
}
