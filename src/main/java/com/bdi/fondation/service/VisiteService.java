package com.bdi.fondation.service;

import com.bdi.fondation.domain.Visite;
import com.bdi.fondation.repository.VisiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Visite.
 */
@Service
@Transactional
public class VisiteService {

    private final Logger log = LoggerFactory.getLogger(VisiteService.class);

    private final VisiteRepository visiteRepository;

    public VisiteService(VisiteRepository visiteRepository) {
        this.visiteRepository = visiteRepository;
    }

    /**
     * Save a visite.
     *
     * @param visite the entity to save
     * @return the persisted entity
     */
    public Visite save(Visite visite) {
        log.debug("Request to save Visite : {}", visite);
        return visiteRepository.save(visite);
    }

    /**
     * Get all the visites.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Visite> findAll() {
        log.debug("Request to get all Visites");
        return visiteRepository.findAll();
    }

    /**
     * Get one visite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Visite findOne(Long id) {
        log.debug("Request to get Visite : {}", id);
        return visiteRepository.findOne(id);
    }

    /**
     * Delete the visite by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Visite : {}", id);
        visiteRepository.delete(id);
    }
}
