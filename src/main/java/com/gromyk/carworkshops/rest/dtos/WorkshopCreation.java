package com.gromyk.carworkshops.rest.dtos;

import javax.persistence.Id;

public class WorkshopCreation {
    private String id;
    private String name;
    private String description;
    private int maximumParallelServices;

    public WorkshopCreation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaximumParallelServices() {
        return maximumParallelServices;
    }

    public void setMaximumParallelServices(int maximumParallelServices) {
        this.maximumParallelServices = maximumParallelServices;
    }
}
