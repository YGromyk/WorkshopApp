package com.gromyk.carworkshops.persistence.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "startTime", columnDefinition = "TIMESTAMP")
    private LocalDateTime startTime;
    @Column(name = "endTime", columnDefinition = "TIMESTAMP")
    private LocalDateTime endTime;
    private String workshopId;
    @ManyToOne
    private Service serviceToDo;

    public Appointment() {
    }

    public Appointment(Long id, LocalDateTime start, LocalDateTime endTime, String workshopId, Service service) {
        this.id = id;
        this.startTime = start;
        this.endTime = endTime;
        this.workshopId = workshopId;
        this.serviceToDo = service;
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

    public void setStartTime(LocalDateTime start) {
        this.startTime = start;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime end) {
        this.endTime = end;
    }

    public String getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(String workshopId) {
        this.workshopId = workshopId;
    }

    public Service getServiceToDo() {
        return serviceToDo;
    }

    public void setServiceToDo(Service serviceToDo) {
        this.serviceToDo = serviceToDo;
    }

    public Service getService() {
        return serviceToDo;
    }

    public void setService(Service service) {
        this.serviceToDo = service;
    }
}
