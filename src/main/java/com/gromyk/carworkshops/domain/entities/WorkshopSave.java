package com.gromyk.carworkshops.domain.entities;

import java.time.LocalTime;

public class WorkshopSave {
    private String id;
    private String name;
    private String description;
    private int maximumParallelServices;
    LocalTime startOfADay;
    LocalTime endOfADay;

    public WorkshopSave() {
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

    public LocalTime getStartOfADay() {
        return startOfADay;
    }

    public void setStartOfADay(LocalTime startOfADay) {
        this.startOfADay = startOfADay;
    }

    public LocalTime getEndOfADay() {
        return endOfADay;
    }

    public void setEndOfADay(LocalTime endOfADay) {
        this.endOfADay = endOfADay;
    }
}
