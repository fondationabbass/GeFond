package com.bdi.fondation.service;

import com.bdi.fondation.domain.Candidature;
import com.bdi.fondation.repository.CandidatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Candidature.
 */
@Service
@Transactional
public class CandidatureService {

    private final Logger log = LoggerFactory.getLogger(CandidatureService.class);

    private final CandidatureRepository candidatureRepository;

    public CandidatureService(CandidatureRepository candidatureRepository) {
        this.candidatureRepository = candidatureRepository;
    }

    /**
     * Save a candidature.
     *
     * @param candidature the entity to save
     * @return the persisted entity
     */
    public Candidature save(Candidature candidature) {
        log.debug("Request to save Candidature : {}", candidature);
        return candidatureRepository.save(candidature);
    }

    /**
     * Get all the candidatures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Candidature> findAll(Pageable pageable) {
        log.debug("Request to get all Candidatures");
        return candidatureRepository.findAll(pageable);
    }

    /**
     * Get one candidature by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Candidature findOne(Long id) {
        log.debug("Request to get Candidature : {}", id);
        return candidatureRepository.findOne(id);
    }

    /**
     * Delete the candidature by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Candidature : {}", id);
        candidatureRepository.delete(id);
    }
}
