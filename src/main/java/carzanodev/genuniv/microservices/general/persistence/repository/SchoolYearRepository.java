package carzanodev.genuniv.microservices.general.persistence.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import carzanodev.genuniv.microservices.common.persistence.repository.InformationRepository;
import carzanodev.genuniv.microservices.general.persistence.entity.SchoolYear;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, Integer>, InformationRepository {

    @Query("SELECT s FROM SchoolYear s WHERE s.isActive = TRUE")
    Set<SchoolYear> findAllActive();

    @Query("SELECT s FROM SchoolYear s WHERE s.id = :id AND s.isActive = TRUE")
    Optional<SchoolYear> findActiveById(int id);

}
