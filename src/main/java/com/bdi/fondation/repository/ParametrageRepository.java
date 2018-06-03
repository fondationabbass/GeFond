package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Parametrage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Parametrage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametrageRepository extends JpaRepository<Parametrage, Long> {

}
