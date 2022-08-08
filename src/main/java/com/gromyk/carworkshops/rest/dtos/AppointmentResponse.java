package com.gromyk.carworkshops.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gromyk.carworkshops.persistence.entities.Service;

import javax.persistence.*;
import java.time.LocalDateTime;

public class AppointmentResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("von")
    private LocalDateTime startTime;
    @JsonProperty("bis")
    private LocalDateTime endTime;
    @JsonProperty("werkstattIs")
    private String workshopId;
    @JsonProperty("leistungId")
    private String serviceCode;

    public AppointmentResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(String workshopId) {
        this.workshopId = workshopId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
