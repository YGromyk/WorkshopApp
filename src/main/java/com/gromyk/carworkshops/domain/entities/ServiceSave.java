package com.gromyk.carworkshops.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class ServiceSave {
    private String serviceName;
    private String description;
    private int durationOfServiceInMin;

    public ServiceSave() {
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
