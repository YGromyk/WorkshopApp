package com.gromyk.carworkshops.domain;

import com.gromyk.carworkshops.domain.entities.WorkshopSave;
import com.gromyk.carworkshops.persistence.entities.Workshop;
import com.gromyk.carworkshops.persistence.repository.WorkshopsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkshopUseCase {
    private final ModelMapper mapper;
    private final WorkshopsRepository workshopsRepository;

    @Autowired
    public WorkshopUseCase(ModelMapper mapper, WorkshopsRepository workshopsRepository) {
        this.mapper = mapper;
        this.workshopsRepository = workshopsRepository;
    }

    public Workshop saveWorkshop(WorkshopSave workshopSave) {
        if(workshopsRepository.findById(workshopSave.getId()).isPresent()) {
            throw new IllegalArgumentException("Workshop with such id already exists; id =  " + workshopSave.getId());
        }

        Workshop workshop = mapper.map(workshopSave, Workshop.class);
        return workshopsRepository.save(workshop);
    }

    public List<Workshop> getAllWorkshops() {
        return workshopsRepository.findAll();
    }

    public Workshop getWorkshopById(String id) throws IllegalArgumentException {
        return workshopsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no workshop with id " + id));
    }
}
