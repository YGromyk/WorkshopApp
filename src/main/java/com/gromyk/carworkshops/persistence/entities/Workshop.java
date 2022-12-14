package com.gromyk.carworkshops.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "workshops")
public class Workshop {
    @Id
    private String id;
    private String name;
    private String description;
    private int maximumParallelServices;

    @OneToMany(mappedBy = "workshop")
    private List<Service> services;

    LocalTime startOfADay;
    LocalTime endOfADay;

    public Workshop() {
    }

    public Workshop(String id, String name, String description, int maximumParallelServices, LocalTime startOfADay, LocalTime endOfADay) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maximumParallelServices = maximumParallelServices;
        this.startOfADay = startOfADay;
        this.endOfADay = endOfADay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaximumParallelServices() {
        return maximumParallelServices;
    }

    public void setMaximumParallelServices(int maximumParallelServices) {
        this.maximumParallelServices = maximumParallelServices;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public LocalTime getStartOfADay() {
        return startOfADay;
    }

    public void setStartOfADay(LocalTime startOfADay) {
        this.startOfADay = startOfADay;
    }

    public LocalTime getEndOfADay() {
        return endOfADay;
    }

    public void setEndOfADay(LocalTime endOfADay) {
        this.endOfADay = endOfADay;
    }
}
