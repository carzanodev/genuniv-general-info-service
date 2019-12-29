package carzanodev.genuniv.microservices.general.persistence.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import carzanodev.genuniv.microservices.common.persistence.repository.InformationRepository;
import carzanodev.genuniv.microservices.general.persistence.entity.SchoolPeriod;

@Repository
public interface SchoolPeriodRepository extends JpaRepository<SchoolPeriod, Integer>, InformationRepository {

    @Query("SELECT s FROM SchoolPeriod s WHERE s.isActive = TRUE")
    Set<SchoolPeriod> findAllActive();

    @Query("SELECT s FROM SchoolPeriod s WHERE s.id = :id AND s.isActive = TRUE")
    Optional<SchoolPeriod> findActiveById(int id);

}
