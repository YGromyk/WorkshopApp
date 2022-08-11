package com.gromyk.carworkshops;

import com.gromyk.carworkshops.persistence.entities.Appointment;
import com.gromyk.carworkshops.rest.dtos.AppointmentDTO;
import com.gromyk.carworkshops.rest.dtos.AppointmentRecommendation;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        PropertyMap<Appointment, AppointmentDTO> appointmentDTOPropertyMap = new PropertyMap<>() {
            protected void configure() {
                map().setServiceCode(source.getService().getServiceName());
            }
        };

        PropertyMap<Appointment, AppointmentRecommendation> appointmentRecommendationPropertyMap = new PropertyMap<>() {
            protected void configure() {
                map().setServiceCode(source.getService().getServiceName());
                map().setService(source.getService().getDescription());
            }
        };

        mapper.addMappings(appointmentDTOPropertyMap);
        mapper.addMappings(appointmentRecommendationPropertyMap);

        return mapper;
    }
}
