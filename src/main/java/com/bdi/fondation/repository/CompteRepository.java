package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Compte;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Compte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long>, JpaSpecificationExecutor<Compte> {

}
