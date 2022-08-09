package com.gromyk.carworkshops.rest;

import com.gromyk.carworkshops.domain.ServiceUseCase;
import com.gromyk.carworkshops.domain.WorkshopUseCase;
import com.gromyk.carworkshops.domain.entities.ServiceSave;
import com.gromyk.carworkshops.domain.entities.WorkshopSave;
import com.gromyk.carworkshops.rest.dtos.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController()
public class WorkshopsController {
    private final WorkshopUseCase workshopUseCase;
    private final ServiceUseCase serviceUseCase;
    private final ModelMapper modelMapper;

    @Autowired
    public WorkshopsController(WorkshopUseCase workshopUseCase, ServiceUseCase serviceUseCase, ModelMapper modelMapper) {
        this.workshopUseCase = workshopUseCase;
        this.serviceUseCase = serviceUseCase;
        this.modelMapper = modelMapper;
    }

    @PostMapping(path = "/werkstatt",consumes = MediaType.APPLICATION_JSON_VALUE)
    public WorkshopDTO createWorkshop(@RequestBody WorkshopCreateRequest createRequest) {
        WorkshopSave workshopSave = modelMapper.map(createRequest, WorkshopSave.class);
        return modelMapper.map(workshopUseCase.saveWorkshop(workshopSave), WorkshopDTO.class);
    }

    @RequestMapping(value = "/werkstatt/{workshopId}", method = RequestMethod.GET)
    public WorkshopDTO getAllAppointmentsFor(
            @PathVariable(value = "workshopId") String workshop
    ) {
        return modelMapper.map(workshopUseCase.getWorkshopById(workshop), WorkshopDTO.class);
    }

    @PostMapping(path = "/werkstatt/{workshopId}/leistung",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ServiceDTO addService(@PathVariable(value = "workshopId") String workshopId,
                                 @RequestBody ServiceDTO serviceDTO
    ) {
        ServiceSave serviceSave = modelMapper.map(serviceDTO, ServiceSave.class);
        return modelMapper.map(serviceUseCase.addServiceFor(workshopId, serviceSave), ServiceDTO.class);
    }
}
