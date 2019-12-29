package carzanodev.genuniv.microservices.general.persistence.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import carzanodev.genuniv.microservices.common.persistence.repository.InformationRepository;
import carzanodev.genuniv.microservices.general.persistence.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, InformationRepository {

    @Query("SELECT s FROM Schedule s WHERE s.isActive = TRUE")
    Set<Schedule> findAllActive();

    @Query("SELECT s FROM Schedule s WHERE s.id = :id AND s.isActive = TRUE")
    Optional<Schedule> findActiveById(int id);

}
