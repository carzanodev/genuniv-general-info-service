package carzanodev.genuniv.microservices.general.api.schoolperiod;

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
import carzanodev.genuniv.microservices.general.persistence.entity.SchoolPeriod;
import carzanodev.genuniv.microservices.general.persistence.repository.SchoolPeriodRepository;

import static carzanodev.genuniv.microservices.common.util.MetaMessage.CREATE_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.DELETE_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.LIST_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.RETRIEVE_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.UPDATE_MSG;

@Service
class SchoolPeriodService extends InformationService {

    private final SchoolPeriodRepository schoolPeriodRepo;

    @Autowired
    SchoolPeriodService(SchoolPeriodRepository schoolPeriodRepo) {
        this.schoolPeriodRepo = schoolPeriodRepo;
    }

    @Override
    protected InformationRepository getInfoRepo() {
        return schoolPeriodRepo;
    }

    StandardResponse<SchoolPeriodDTO.List> getAllSchoolPeriods(boolean isActiveOnly) {
        List<SchoolPeriodDTO> schoolPeriodDtos = (isActiveOnly ? schoolPeriodRepo.findAllActive() : schoolPeriodRepo.findAll())
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());

        SchoolPeriodDTO.List response = new SchoolPeriodDTO.List(schoolPeriodDtos);
        ResponseMeta meta = ResponseMeta.createBasicMeta(LIST_MSG.make(schoolPeriodDtos.size()));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<SchoolPeriodDTO> getSchoolPeriodById(int id, boolean isActiveOnly) throws InvalidTargetEntityException {
        Optional<SchoolPeriod> schoolPeriod = isActiveOnly ? schoolPeriodRepo.findActiveById(id) : schoolPeriodRepo.findById(id);

        if (schoolPeriod.isEmpty()) {
            throw new InvalidTargetEntityException("school_period", String.valueOf(id));
        }

        SchoolPeriodDTO response = entityToDto(schoolPeriod.get());
        ResponseMeta meta = ResponseMeta.createBasicMeta(RETRIEVE_MSG.make(id));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<SchoolPeriodDTO> addSchoolPeriod(SchoolPeriodDTO schoolPeriodDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        SchoolPeriod saved = schoolPeriodRepo.save(dtoToNewEntity(schoolPeriodDto));

        SchoolPeriodDTO response = entityToDto(saved);
        ResponseMeta meta = ResponseMeta.createBasicMeta(CREATE_MSG.make(saved.getId()));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<SchoolPeriodDTO> updateSchoolPeriod(int id, SchoolPeriodDTO schoolPeriodDto, boolean isActiveOnly) throws InvalidTargetEntityException, InvalidReferenceValueException {
        SchoolPeriod saved = schoolPeriodRepo.save(dtoToUpdatedEntity(id, schoolPeriodDto, isActiveOnly));

        SchoolPeriodDTO response = entityToDto(saved);
        ResponseMeta meta = ResponseMeta.createBasicMeta(UPDATE_MSG.make(saved.getId()));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<SchoolPeriodDTO> deleteSchoolPeriod(int id) throws InvalidTargetEntityException {
        Optional<SchoolPeriod> schoolPeriodFromDb = schoolPeriodRepo.findById(id);

        if (schoolPeriodFromDb.isEmpty()) {
            throw new InvalidTargetEntityException("school_period", String.valueOf(id));
        }

        SchoolPeriod schoolPeriod = schoolPeriodFromDb.get();
        schoolPeriodRepo.delete(schoolPeriod);

        SchoolPeriodDTO response = entityToDto(schoolPeriod);
        ResponseMeta meta = ResponseMeta.createBasicMeta(DELETE_MSG.make(response.getId()));

        return new StandardResponse<>(response, meta);
    }

    private SchoolPeriodDTO entityToDto(SchoolPeriod entity) {
        return new SchoolPeriodDTO(
                entity.getId(),
                entity.getName());
    }

    private SchoolPeriod dtoToNewEntity(SchoolPeriodDTO schoolPeriodDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        return dtoToUpdatedEntity(0, schoolPeriodDto, false);
    }

    private SchoolPeriod dtoToUpdatedEntity(int id, SchoolPeriodDTO schoolPeriodDto, boolean isActiveOnly) throws InvalidTargetEntityException, InvalidReferenceValueException {
        Optional<SchoolPeriod> f;
        if (id == 0) {
            f = Optional.of(new SchoolPeriod());
        } else {
            f = isActiveOnly ? schoolPeriodRepo.findActiveById(id) : schoolPeriodRepo.findById(id);
        }

        if (f.isEmpty()) {
            throw new InvalidTargetEntityException("school_period", String.valueOf(id));
        }

        SchoolPeriod schoolPeriod = f.get();

        String name = schoolPeriodDto.getName();
        if (!StringUtils.isEmpty(name)) {
            schoolPeriod.setName(name);
        }

        return schoolPeriod;
    }

}