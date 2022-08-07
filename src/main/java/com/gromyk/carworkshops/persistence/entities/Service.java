package com.gromyk.carworkshops.persistence.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String serviceName;
    private String description;
    private int durationOfServiceInMin;

    @OneToMany(mappedBy = "serviceToDo")
    private List<Appointment> appointmentsForService;
    @ManyToOne
    private Workshop workshop;

    public Service() {
    }

    public Service(String serviceName, int durationOfServiceInMin) {
        this.serviceName = serviceName;
        this.durationOfServiceInMin = durationOfServiceInMin;
    }

    public Service(String serviceName, String description, int durationOfServiceInMin, Workshop workshop) {
        this.serviceName = serviceName;
        this.description = description;
        this.durationOfServiceInMin = durationOfServiceInMin;
        this.workshop = workshop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getDurationOfServiceInMin() {
        return durationOfServiceInMin;
    }

    public void setDurationOfServiceInMin(int durationOfServiceInMin) {
        this.durationOfServiceInMin = durationOfServiceInMin;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Appointment> getAppointmentsForService() {
        return appointmentsForService;
    }

    public void setAppointmentsForService(List<Appointment> appointmentsForService) {
        this.appointmentsForService = appointmentsForService;
    }
}
