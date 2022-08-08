package com.gromyk.carworkshops.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class AppointmentRecommendation {

    @JsonProperty("von")
    private LocalDateTime startTime;
    @JsonProperty("bis")
    private LocalDateTime endTime;
    @JsonProperty("werkstattName")
    private String workshopName;
    @JsonProperty("leistungId")
    private String serviceCode;
    @JsonProperty("leistung")
    private String service;

    public AppointmentRecommendation() {
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

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
