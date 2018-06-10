package com.bdi.fondation.service;

import com.bdi.fondation.domain.Projet;
import com.bdi.fondation.repository.ProjetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Projet.
 */
@Service
@Transactional
public class ProjetService {

    private final Logger log = LoggerFactory.getLogger(ProjetService.class);

    private final ProjetRepository projetRepository;

    public ProjetService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    /**
     * Save a projet.
     *
     * @param projet the entity to save
     * @return the persisted entity
     */
    public Projet save(Projet projet) {
        log.debug("Request to save Projet : {}", projet);
        return projetRepository.save(projet);
    }

    /**
     * Get all the projets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Projet> findAll(Pageable pageable) {
        log.debug("Request to get all Projets");
        return projetRepository.findAll(pageable);
    }

    /**
     * Get one projet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Projet findOne(Long id) {
        log.debug("Request to get Projet : {}", id);
        return projetRepository.findOne(id);
    }

    /**
     * Delete the projet by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Projet : {}", id);
        projetRepository.delete(id);
    }
}
