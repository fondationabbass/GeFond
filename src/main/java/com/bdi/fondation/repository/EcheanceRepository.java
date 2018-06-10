package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Echeance;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Echeance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcheanceRepository extends JpaRepository<Echeance, Long> {

}
