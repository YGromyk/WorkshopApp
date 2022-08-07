package com.gromyk.carworkshops.domain;

import com.gromyk.carworkshops.persistence.repository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Planner {
    private final AppointmentsRepository repository;

    @Autowired
    public Planner(AppointmentsRepository repository) {
        this.repository = repository;
    }



}
