package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Chapitre;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Chapitre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChapitreRepository extends JpaRepository<Chapitre, Long> {

}
