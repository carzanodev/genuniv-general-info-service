package carzanodev.genuniv.microservices.general.api.schedule;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import carzanodev.genuniv.microservices.common.config.CommonExceptionHandler.InvalidReferenceValueException;
import carzanodev.genuniv.microservices.common.config.CommonExceptionHandler.InvalidTargetEntityException;
import carzanodev.genuniv.microservices.common.model.dto.SourceInfo;
import carzanodev.genuniv.microservices.common.model.dto.StandardResponse;

@RestController
@RequestMapping("/api/v1/schedule")
class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // ↓↓↓ GET ↓↓↓

    @GetMapping
    StandardResponse<ScheduleDTO.List> getSchedule() {
        return scheduleService.getAllSchedules(true);
    }

    @GetMapping(path = "{id}")
    StandardResponse<ScheduleDTO> getScheduleById(@PathVariable("id") int id) throws InvalidTargetEntityException {
        return scheduleService.getScheduleById(id, true);
    }

    @GetMapping(path = "info")
    StandardResponse<SourceInfo> getScheduleInfo(@RequestParam(name = "last_updated", defaultValue = "") String lastUpdated) {
        return scheduleService.getInfo(lastUpdated);
    }

    // ↓↓↓ POST ↓↓↓

    @PostMapping
    StandardResponse<ScheduleDTO> postSchedule(@Valid @NonNull @RequestBody ScheduleDTO scheduleDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        return scheduleService.addSchedule(scheduleDto);
    }

    // ↓↓↓ PUT ↓↓↓

    @PutMapping(path = "{id}")
    StandardResponse<ScheduleDTO> putSchedule(@PathVariable("id") int id,
                                              @Valid @NonNull @RequestBody ScheduleDTO scheduleDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        return scheduleService.updateSchedule(id, scheduleDto, true);
    }

    // ↓↓↓ DELETE ↓↓↓

    @DeleteMapping(path = "{id}")
    StandardResponse<ScheduleDTO> deleteSchedule(@PathVariable("id") int id) throws InvalidTargetEntityException {
        return scheduleService.deleteSchedule(id);
    }

}
