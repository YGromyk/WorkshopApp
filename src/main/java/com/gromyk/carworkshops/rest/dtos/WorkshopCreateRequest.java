package com.gromyk.carworkshops.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gromyk.carworkshops.DateHelper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class WorkshopCreateRequest {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("beschreibung")
    private String description;
    @JsonProperty("maximumParallelServices")
    private int maximumParallelServices;
    @JsonProperty("von")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateHelper.TIME_FORMAT)
    private LocalTime startOfADay;
    @JsonProperty("bis")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateHelper.TIME_FORMAT)
    private LocalTime endOfADay;

    public WorkshopCreateRequest() {
    }

    public WorkshopCreateRequest(String id, String name, String description, int maximumParallelServices) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maximumParallelServices = maximumParallelServices;
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
