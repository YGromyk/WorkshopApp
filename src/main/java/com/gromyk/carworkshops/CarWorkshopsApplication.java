package com.gromyk.carworkshops;

import com.gromyk.carworkshops.persistence.repository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarWorkshopsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarWorkshopsApplication.class, args);
    }

}
