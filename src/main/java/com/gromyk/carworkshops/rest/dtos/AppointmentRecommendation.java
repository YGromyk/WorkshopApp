package com.gromyk.carworkshops.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gromyk.carworkshops.DateHelper;

import java.time.LocalDateTime;

public class AppointmentRecommendation {
    @JsonProperty("von")
    @JsonFormat(pattern = DateHelper.DATE_FORMAT)
    private LocalDateTime startTime;
    @JsonProperty("bis")
    @JsonFormat(pattern = DateHelper.DATE_FORMAT)
    private LocalDateTime endTime;
    @JsonProperty("werkstattName")
    private String workshop;
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

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
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
