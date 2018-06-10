package com.bdi.fondation.repository;

import com.bdi.fondation.domain.SessionProjet;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SessionProjet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SessionProjetRepository extends JpaRepository<SessionProjet, Long> {

}
