package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Parametrage;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Parametrage entity.
 */
@Repository
public interface ParametrageRepository extends JpaRepository<Parametrage, Long> {
	List<Parametrage> findByCodeTypeParam(String type);
}
