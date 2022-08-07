package com.gromyk.carworkshops.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gromyk.carworkshops.DateHelper;

import java.time.LocalDateTime;

public class AppointmentSuggestion {
    @JsonProperty("vom")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateHelper.DATE_FORMAT)
    private LocalDateTime startTime;
    @JsonProperty("bis")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateHelper.DATE_FORMAT)
    private LocalDateTime endTime;
    @JsonProperty("leistungsId")
    private String serviceId;

    public AppointmentSuggestion() {
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
