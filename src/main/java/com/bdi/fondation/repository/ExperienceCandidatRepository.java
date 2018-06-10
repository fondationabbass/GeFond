package com.bdi.fondation.repository;

import com.bdi.fondation.domain.ExperienceCandidat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExperienceCandidat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperienceCandidatRepository extends JpaRepository<ExperienceCandidat, Long> {

}
