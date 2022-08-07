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
    private String workshopName;
    @ManyToOne
    private Service serviceToDo;

    public Appointment() {
    }

    public Appointment(Long id, LocalDateTime start, LocalDateTime endTime, String workshopName, Service service) {
        this.id = id;
        this.startTime = start;
        this.endTime = endTime;
        this.workshopName = workshopName;
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

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public Service getService() {
        return serviceToDo;
    }

    public void setService(Service service) {
        this.serviceToDo = service;
    }
}
