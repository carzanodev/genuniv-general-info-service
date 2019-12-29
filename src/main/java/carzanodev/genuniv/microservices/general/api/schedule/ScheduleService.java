package carzanodev.genuniv.microservices.general.api.schedule;

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
import carzanodev.genuniv.microservices.general.persistence.entity.Schedule;
import carzanodev.genuniv.microservices.general.persistence.repository.ScheduleRepository;

import static carzanodev.genuniv.microservices.common.util.MetaMessage.CREATE_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.DELETE_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.LIST_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.RETRIEVE_MSG;
import static carzanodev.genuniv.microservices.common.util.MetaMessage.UPDATE_MSG;

@Service
class ScheduleService extends InformationService {

    private final ScheduleRepository scheduleRepo;

    @Autowired
    ScheduleService(ScheduleRepository scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    @Override
    protected InformationRepository getInfoRepo() {
        return scheduleRepo;
    }

    StandardResponse<ScheduleDTO.List> getAllSchedules(boolean isActiveOnly) {
        List<ScheduleDTO> scheduleDtos = (isActiveOnly ? scheduleRepo.findAllActive() : scheduleRepo.findAll())
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());

        ScheduleDTO.List response = new ScheduleDTO.List(scheduleDtos);
        ResponseMeta meta = ResponseMeta.createBasicMeta(LIST_MSG.make(scheduleDtos.size()));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<ScheduleDTO> getScheduleById(int id, boolean isActiveOnly) throws InvalidTargetEntityException {
        Optional<Schedule> schedule = isActiveOnly ? scheduleRepo.findActiveById(id) : scheduleRepo.findById(id);

        if (schedule.isEmpty()) {
            throw new InvalidTargetEntityException("schedule", String.valueOf(id));
        }

        ScheduleDTO response = entityToDto(schedule.get());
        ResponseMeta meta = ResponseMeta.createBasicMeta(RETRIEVE_MSG.make(id));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<ScheduleDTO> addSchedule(ScheduleDTO scheduleDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        Schedule saved = scheduleRepo.save(dtoToNewEntity(scheduleDto));

        ScheduleDTO response = entityToDto(saved);
        ResponseMeta meta = ResponseMeta.createBasicMeta(CREATE_MSG.make(saved.getId()));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<ScheduleDTO> updateSchedule(int id, ScheduleDTO scheduleDto, boolean isActiveOnly) throws InvalidTargetEntityException, InvalidReferenceValueException {
        Schedule saved = scheduleRepo.save(dtoToUpdatedEntity(id, scheduleDto, isActiveOnly));

        ScheduleDTO response = entityToDto(saved);
        ResponseMeta meta = ResponseMeta.createBasicMeta(UPDATE_MSG.make(saved.getId()));

        return new StandardResponse<>(response, meta);
    }

    StandardResponse<ScheduleDTO> deleteSchedule(int id) throws InvalidTargetEntityException {
        Optional<Schedule> scheduleFromDb = scheduleRepo.findById(id);

        if (scheduleFromDb.isEmpty()) {
            throw new InvalidTargetEntityException("schedule", String.valueOf(id));
        }

        Schedule schedule = scheduleFromDb.get();
        scheduleRepo.delete(schedule);

        ScheduleDTO response = entityToDto(schedule);
        ResponseMeta meta = ResponseMeta.createBasicMeta(DELETE_MSG.make(response.getId()));

        return new StandardResponse<>(response, meta);
    }

    private ScheduleDTO entityToDto(Schedule entity) {
        return new ScheduleDTO(
                entity.getId(),
                entity.getName(),
                entity.getTimeSlot());
    }

    private Schedule dtoToNewEntity(ScheduleDTO scheduleDto) throws InvalidTargetEntityException, InvalidReferenceValueException {
        return dtoToUpdatedEntity(0, scheduleDto, false);
    }

    private Schedule dtoToUpdatedEntity(int id, ScheduleDTO scheduleDto, boolean isActiveOnly) throws InvalidTargetEntityException, InvalidReferenceValueException {
        Optional<Schedule> f;
        if (id == 0) {
            f = Optional.of(new Schedule());
        } else {
            f = isActiveOnly ? scheduleRepo.findActiveById(id) : scheduleRepo.findById(id);
        }

        if (f.isEmpty()) {
            throw new InvalidTargetEntityException("schedule", String.valueOf(id));
        }

        Schedule schedule = f.get();

        String name = scheduleDto.getName();
        if (!StringUtils.isEmpty(name)) {
            schedule.setName(name);
        }

        return schedule;
    }

}