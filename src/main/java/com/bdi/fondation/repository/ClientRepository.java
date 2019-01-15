package com.bdi.fondation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.domain.Client;


/**
 * Spring Data JPA repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
	public List<Client> findFirst3ByOrderByIdDesc();

	public List<Client> findByCandidat(Candidat candidat);
}
