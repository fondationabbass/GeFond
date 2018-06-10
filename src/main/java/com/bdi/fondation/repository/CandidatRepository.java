package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Candidat
;


import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Candidat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long>, JpaSpecificationExecutor<Candidat> {
	
	public List<Candidat> findFirst3ByOrderByIdDesc();

}
