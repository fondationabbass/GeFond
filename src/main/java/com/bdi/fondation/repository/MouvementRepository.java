package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Mouvement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Mouvement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, Long> {

}
