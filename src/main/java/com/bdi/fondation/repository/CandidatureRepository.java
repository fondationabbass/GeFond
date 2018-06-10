package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Candidature;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Candidature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long>, JpaSpecificationExecutor<Candidature> {

}
