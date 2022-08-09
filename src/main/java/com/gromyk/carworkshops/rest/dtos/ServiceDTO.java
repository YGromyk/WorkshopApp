package com.gromyk.carworkshops.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class ServiceDTO {

    @JsonProperty("leistungCode")
    private String serviceName;
    @JsonProperty("beschreibung")
    private String description;
    @JsonProperty("leistungZeitInMin")
    private int durationOfServiceInMin;

    public ServiceDTO() {
    }

    public ServiceDTO(String serviceName, String description, int durationOfServiceInMin) {
        this.serviceName = serviceName;
        this.description = description;
        this.durationOfServiceInMin = durationOfServiceInMin;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationOfServiceInMin() {
        return durationOfServiceInMin;
    }

    public void setDurationOfServiceInMin(int durationOfServiceInMin) {
        this.durationOfServiceInMin = durationOfServiceInMin;
    }
}
