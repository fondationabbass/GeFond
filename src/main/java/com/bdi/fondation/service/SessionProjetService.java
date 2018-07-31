package com.bdi.fondation.service;

import com.bdi.fondation.domain.SessionProjet;
import com.bdi.fondation.repository.SessionProjetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing SessionProjet.
 */
@Service
@Transactional
public class SessionProjetService {

    private final Logger log = LoggerFactory.getLogger(SessionProjetService.class);

    private final SessionProjetRepository sessionProjetRepository;

    public SessionProjetService(SessionProjetRepository sessionProjetRepository) {
        this.sessionProjetRepository = sessionProjetRepository;
    }

    /**
     * Save a sessionProjet.
     *
     * @param sessionProjet the entity to save
     * @return the persisted entity
     */
    public SessionProjet save(SessionProjet sessionProjet) {
        log.debug("Request to save SessionProjet : {}", sessionProjet);
        return sessionProjetRepository.save(sessionProjet);
    }

    /**
     * Get all the sessionProjets.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SessionProjet> findAll() {
        log.debug("Request to get all SessionProjets");
        return sessionProjetRepository.findAll();
    }

    /**
     * Get one sessionProjet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SessionProjet findOne(Long id) {
        log.debug("Request to get SessionProjet : {}", id);
        return sessionProjetRepository.findOne(id);
    }

    /**
     * Delete the sessionProjet by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SessionProjet : {}", id);
        sessionProjetRepository.delete(id);
    }
}
