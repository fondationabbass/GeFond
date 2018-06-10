package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.domain.Pret;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pret entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PretRepository extends JpaRepository<Pret, Long>, JpaSpecificationExecutor<Pret> {
	public List<Pret> findFirst3ByOrderByIdDesc();
}
