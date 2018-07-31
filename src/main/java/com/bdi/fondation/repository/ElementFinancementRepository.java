package com.bdi.fondation.repository;

import com.bdi.fondation.domain.ElementFinancement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ElementFinancement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementFinancementRepository extends JpaRepository<ElementFinancement, Long>, JpaSpecificationExecutor<ElementFinancement> {

}
