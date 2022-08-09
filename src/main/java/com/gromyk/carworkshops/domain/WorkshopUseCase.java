package com.gromyk.carworkshops.domain;

import com.gromyk.carworkshops.domain.entities.WorkshopSave;
import com.gromyk.carworkshops.persistence.entities.Workshop;
import com.gromyk.carworkshops.persistence.repository.WorkshopsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkshopUseCase {
    private final WorkshopsRepository workshopsRepository;

    @Autowired
    public WorkshopUseCase(WorkshopsRepository workshopsRepository) {
        this.workshopsRepository = workshopsRepository;
    }

    public Workshop saveWorkshop(WorkshopSave workshopSave) {
        Workshop workshop = new Workshop();
        workshop.setId(workshopSave.getId());
        workshop.setName(workshopSave.getName());
        workshop.setDescription(workshopSave.getDescription());
        workshop.setMaximumParallelServices(workshopSave.getMaximumParallelServices());
        workshop.setStartOfADay(workshopSave.getStartOfADay());
        workshop.setEndOfADay(workshopSave.getEndOfADay());
        return workshopsRepository.save(workshop);
    }

    public List<Workshop> getAllWorkshops() {
        return workshopsRepository.findAll();
    }

    public Workshop getWorkshopById(String id) throws IllegalArgumentException {
        return workshopsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no workshop with id " + id));
    }
}
