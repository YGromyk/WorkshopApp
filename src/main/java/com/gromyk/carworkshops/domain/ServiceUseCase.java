package com.gromyk.carworkshops.domain;

import com.gromyk.carworkshops.domain.entities.ServiceSave;
import com.gromyk.carworkshops.persistence.entities.Service;
import com.gromyk.carworkshops.persistence.entities.Workshop;
import com.gromyk.carworkshops.persistence.repository.ServicesRepository;
import com.gromyk.carworkshops.persistence.repository.WorkshopsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class ServiceUseCase {
    private final ModelMapper modelMapper;
    private final ServicesRepository servicesRepository;
private final  WorkshopsRepository workshopsRepository;
    @Autowired
    public ServiceUseCase(ServicesRepository servicesRepository, WorkshopsRepository workshopsRepository, ModelMapper modelMapper, WorkshopsRepository workshopsRepository1) {
        this.servicesRepository = servicesRepository;
        this.modelMapper = modelMapper;
        this.workshopsRepository = workshopsRepository1;
    }

    public Service addServiceFor(String workshopId, ServiceSave serviceSave) {
        Workshop workshop = workshopsRepository.findById(workshopId).orElseThrow();
        Service service = modelMapper.map(serviceSave, Service.class);
        service.setWorkshop(workshop);
        return servicesRepository.save(service);
    }
}
