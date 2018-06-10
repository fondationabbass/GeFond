package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Garantie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Garantie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GarantieRepository extends JpaRepository<Garantie, Long> {

}
