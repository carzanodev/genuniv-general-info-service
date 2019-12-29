package carzanodev.genuniv.microservices.general.api.schoolyear;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import carzanodev.genuniv.microservices.common.api.service.InformationService;
import carzanodev.genuniv.microservices.common.config.CommonExceptionHandler.InvalidReferenceValueException;
import carzanodev.genuniv.microservices.common.config.CommonExceptionHandler.InvalidTargetEntityException;
import carzanodev.genuniv.microservices.common.model.dto.ResponseMeta;
import carzanodev.genuniv.microservices.common.model.dto.StandardResponse;
import carzanodev.genuniv.microservices.common.persistence.repository.InformationRepository;
import carzanodev.genuniv.microservices.general.persistence.entity.SchoolYear;
import carzanodev.genuniv.microservices.general.persistence.repository.SchoolYearRepository;

import static carzanodev.genuniv.microservices.common.util.MetaMessage.CREATE_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.DELETE_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.LIST_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.RETRIEVE_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.UPDATE_MSG;

@Service
class SchoolYearService extends InformationService {

    private final SchoolYearRepository schoolYearRepo;

    @Autowired
    SchoolYearService(SchoolYearRepository schoolYearRepo) {
        this.schoolYearRepo = schoolYearRepo;
    }

    @Override
    protected InformationRepository getInfoRepo() {
        return schoolYearRepo;
    }

    StandardResponse<SchoolYearDTO.List> getAllSchoolYears(boolean isActiveOnly) {
        List<SchoolYearDTO> schoolYearDtos = (isActiveOnly ? schoolYearRepo.findAllActive() : schoolYearRepo.findAll())
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());

        SchoolYearDTO.List response = new SchoolYearDTO.List(schoolYearDtos);
        ResponseMeta meta = ResponseMeta.createBasicMeta(LIST_MSG.make(schoolYearDtos.size()));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<SchoolYearDTO> getSchoolYearById(int id, boolean isActiveOnly) throws InvalidTargetEntityException {
        Optional<SchoolYear> schoolYear = isActiveOnly ? schoolYearRepo.findActiveById(id) : schoolYearRepo.findById(id);

        if (schoolYear.isEmpty()) {
            throw new InvalidTargetEntityException("school_year", String.valueOf(id));
        }

        SchoolYearDTO response = entityToDto(schoolYear.get());
        ResponseMeta meta = ResponseMeta.createBasicMeta(RETRIEVE_MSG.make(id));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<SchoolYearDTO> addSchoolYear(SchoolYearDTO schoolYearDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        SchoolYear saved = schoolYearRepo.save(dtoToNewEntity(schoolYearDto));

        SchoolYearDTO response = entityToDto(saved);
        ResponseMeta meta = ResponseMeta.createBasicMeta(CREATE_MSG.make(saved.getId()));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<SchoolYearDTO> updateSchoolYear(int id, SchoolYearDTO schoolYearDto, boolean isActiveOnly) throws InvalidTargetEntityException, InvalidReferenceValueException {
        SchoolYear saved = schoolYearRepo.save(dtoToUpdatedEntity(id, schoolYearDto, isActiveOnly));

        SchoolYearDTO response = entityToDto(saved);
        ResponseMeta meta = ResponseMeta.createBasicMeta(UPDATE_MSG.make(saved.getId()));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<SchoolYearDTO> deleteSchoolYear(int id) throws InvalidTargetEntityException {
        Optional<SchoolYear> schoolYearFromDb = schoolYearRepo.findById(id);

        if (schoolYearFromDb.isEmpty()) {
            throw new InvalidTargetEntityException("school_year", String.valueOf(id));
        }

        SchoolYear schoolYear = schoolYearFromDb.get();
        schoolYearRepo.delete(schoolYear);

        SchoolYearDTO response = entityToDto(schoolYear);
        ResponseMeta meta = ResponseMeta.createBasicMeta(DELETE_MSG.make(response.getId()));

        return new StandardResponse<>(response, meta);
    }

    private SchoolYearDTO entityToDto(SchoolYear entity) {
        return new SchoolYearDTO(
                entity.getId(),
                entity.getName());
    }

    private SchoolYear dtoToNewEntity(SchoolYearDTO schoolYearDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        return dtoToUpdatedEntity(0, schoolYearDto, false);
    }

    private SchoolYear dtoToUpdatedEntity(int id, SchoolYearDTO schoolYearDto, boolean isActiveOnly) throws InvalidTargetEntityException, InvalidReferenceValueException {
        Optional<SchoolYear> f;
        if (id == 0) {
            f = Optional.of(new SchoolYear());
        } else {
            f = isActiveOnly ? schoolYearRepo.findActiveById(id) : schoolYearRepo.findById(id);
        }

        if (f.isEmpty()) {
            throw new InvalidTargetEntityException("school_year", String.valueOf(id));
        }

        SchoolYear schoolYear = f.get();

        String name = schoolYearDto.getName();
        if (!StringUtils.isEmpty(name)) {
            schoolYear.setName(name);
        }

        return schoolYear;
    }

}