package com.bdi.fondation.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdi.fondation.domain.Compte;
import com.bdi.fondation.repository.CompteRepository;
import com.bdi.fondation.security.SecurityUtils;
/**
 * Service Implementation for managing Compte.
 */
@Service
@Transactional
public class CompteService {

    private static final String CAISSE = "Caisse";

	private final Logger log = LoggerFactory.getLogger(CompteService.class);

    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    /**
     * Save a compte.
     *
     * @param compte the entity to save
     * @return the persisted entity
     */
    public Compte save(Compte compte) {
        log.debug("Request to save Compte : {}", compte);        return compteRepository.save(compte);
    }

    /**
     * Get all the comptes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Compte> findAll(Pageable pageable) {
        log.debug("Request to get all Comptes");
        return compteRepository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public Compte findUserCompte() {
    	List<Compte> findByUserIsCurrentUser = compteRepository.findByUserIsCurrentUser();
    	for (Compte compte : findByUserIsCurrentUser) {
			if(CAISSE.equals(compte.getIntituleCompte())) {
				return compte;
			}
		}
		throw new IllegalStateException("L'utilisateur " + SecurityUtils.getCurrentUserLogin() + " n'a pas de compte caissier");
    }


    /**
     * Get one compte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Compte findOne(Long id) {
        log.debug("Request to get Compte : {}", id);
        return compteRepository.findOne(id);
    }

    /**
     * Delete the compte by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Compte : {}", id);
        compteRepository.delete(id);
    }
}
