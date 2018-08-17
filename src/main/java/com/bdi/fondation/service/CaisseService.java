package com.bdi.fondation.service;

import com.bdi.fondation.domain.Caisse;
import com.bdi.fondation.repository.CaisseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Caisse.
 */
@Service
@Transactional
public class CaisseService {

    private final Logger log = LoggerFactory.getLogger(CaisseService.class);

    private final CaisseRepository caisseRepository;

    public CaisseService(CaisseRepository caisseRepository) {
        this.caisseRepository = caisseRepository;
    }

    /**
     * Save a caisse.
     *
     * @param caisse the entity to save
     * @return the persisted entity
     */
    public Caisse save(Caisse caisse) {
        log.debug("Request to save Caisse : {}", caisse);
        return caisseRepository.save(caisse);
    }

    /**
     * Get all the caisses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Caisse> findAll(Pageable pageable) {
        log.debug("Request to get all Caisses");
        return caisseRepository.findAll(pageable);
    }

    /**
     * Get one caisse by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Caisse findOne(Long id) {
        log.debug("Request to get Caisse : {}", id);
        return caisseRepository.findOne(id);
    }

    /**
     * Delete the caisse by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Caisse : {}", id);
        caisseRepository.delete(id);
    }
}
