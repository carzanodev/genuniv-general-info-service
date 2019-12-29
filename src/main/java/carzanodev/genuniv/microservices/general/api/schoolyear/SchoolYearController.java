package carzanodev.genuniv.microservices.general.api.schoolyear;

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
@RequestMapping("/api/v1/school-year")
class SchoolYearController {

    private final SchoolYearService schoolYearService;

    @Autowired
    SchoolYearController(SchoolYearService schoolYearService) {
        this.schoolYearService = schoolYearService;
    }

    // ↓↓↓ GET ↓↓↓

    @GetMapping
    StandardResponse<SchoolYearDTO.List> getSchoolYear() {
        return schoolYearService.getAllSchoolYears(true);
    }

    @GetMapping(path = "{id}")
    StandardResponse<SchoolYearDTO> getSchoolYearById(@PathVariable("id") int id) throws InvalidTargetEntityException {
        return schoolYearService.getSchoolYearById(id, true);
    }

    @GetMapping(path = "info")
    StandardResponse<SourceInfo> getSchoolYearInfo(@RequestParam(name = "last_updated", defaultValue = "") String lastUpdated) {
        return schoolYearService.getInfo(lastUpdated);
    }

    // ↓↓↓ POST ↓↓↓

    @PostMapping
    StandardResponse<SchoolYearDTO> postSchoolYear(@Valid @NonNull @RequestBody SchoolYearDTO schoolYearDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        return schoolYearService.addSchoolYear(schoolYearDto);
    }

    // ↓↓↓ PUT ↓↓↓

    @PutMapping(path = "{id}")
    StandardResponse<SchoolYearDTO> putSchoolYear(@PathVariable("id") int id,
                                                  @Valid @NonNull @RequestBody SchoolYearDTO schoolYearDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        return schoolYearService.updateSchoolYear(id, schoolYearDto, true);
    }

    // ↓↓↓ DELETE ↓↓↓

    @DeleteMapping(path = "{id}")
    StandardResponse<SchoolYearDTO> deleteSchoolYear(@PathVariable("id") int id) throws InvalidTargetEntityException {
        return schoolYearService.deleteSchoolYear(id);
    }

}
