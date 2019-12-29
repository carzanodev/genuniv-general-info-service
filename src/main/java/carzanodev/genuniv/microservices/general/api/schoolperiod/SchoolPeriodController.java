package carzanodev.genuniv.microservices.general.api.schoolperiod;

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
@RequestMapping("/api/v1/school-period")
class SchoolPeriodController {

    private final SchoolPeriodService schoolPeriodService;

    @Autowired
    SchoolPeriodController(SchoolPeriodService schoolPeriodService) {
        this.schoolPeriodService = schoolPeriodService;
    }

    // ↓↓↓ GET ↓↓↓

    @GetMapping
    StandardResponse<SchoolPeriodDTO.List> getSchoolPeriod() {
        return schoolPeriodService.getAllSchoolPeriods(true);
    }

    @GetMapping(path = "{id}")
    StandardResponse<SchoolPeriodDTO> getSchoolPeriodById(@PathVariable("id") int id) throws InvalidTargetEntityException {
        return schoolPeriodService.getSchoolPeriodById(id, true);
    }

    @GetMapping(path = "info")
    StandardResponse<SourceInfo> getSchoolPeriodInfo(@RequestParam(name = "last_updated", defaultValue = "") String lastUpdated) {
        return schoolPeriodService.getInfo(lastUpdated);
    }

    // ↓↓↓ POST ↓↓↓

    @PostMapping
    StandardResponse<SchoolPeriodDTO> postSchoolPeriod(@Valid @NonNull @RequestBody SchoolPeriodDTO schoolPeriodDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        return schoolPeriodService.addSchoolPeriod(schoolPeriodDto);
    }

    // ↓↓↓ PUT ↓↓↓

    @PutMapping(path = "{id}")
    StandardResponse<SchoolPeriodDTO> putSchoolPeriod(@PathVariable("id") int id,
                                                      @Valid @NonNull @RequestBody SchoolPeriodDTO schoolPeriodDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        return schoolPeriodService.updateSchoolPeriod(id, schoolPeriodDto, true);
    }

    // ↓↓↓ DELETE ↓↓↓

    @DeleteMapping(path = "{id}")
    StandardResponse<SchoolPeriodDTO> deleteSchoolPeriod(@PathVariable("id") int id) throws InvalidTargetEntityException {
        return schoolPeriodService.deleteSchoolPeriod(id);
    }

}
