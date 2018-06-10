package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Candidat;



import com.bdi.fondation.domain.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
	public List<Client> findFirst3ByOrderByIdDesc();
}
