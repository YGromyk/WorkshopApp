package com.gromyk.carworkshops.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gromyk.carworkshops.DateHelper;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AppointmentDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("von")
    @JsonFormat(pattern = DateHelper.DATE_FORMAT)
    private LocalDateTime startTime;
    @JsonProperty("bis")
    @JsonFormat(pattern = DateHelper.DATE_FORMAT)
    private LocalDateTime endTime;
    @JsonProperty("werkstattId")
    private String workshopId;
    @JsonProperty("leistungCode")
    private String serviceCode;

    public AppointmentDTO() {
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
