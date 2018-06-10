package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Entretien;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Entretien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntretienRepository extends JpaRepository<Entretien, Long> {

}
