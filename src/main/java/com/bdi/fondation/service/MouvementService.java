package com.bdi.fondation.service;

import com.bdi.fondation.domain.Mouvement;
import com.bdi.fondation.repository.MouvementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Mouvement.
 */
@Service
@Transactional
public class MouvementService {

    private final Logger log = LoggerFactory.getLogger(MouvementService.class);

    private final MouvementRepository mouvementRepository;

    public MouvementService(MouvementRepository mouvementRepository) {
        this.mouvementRepository = mouvementRepository;
    }

    /**
     * Save a mouvement.
     *
     * @param mouvement the entity to save
     * @return the persisted entity
     */
    public Mouvement save(Mouvement mouvement) {
        log.debug("Request to save Mouvement : {}", mouvement);
        return mouvementRepository.save(mouvement);
    }

    /**
     * Get all the mouvements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Mouvement> findAll(Pageable pageable) {
        log.debug("Request to get all Mouvements");
        return mouvementRepository.findAll(pageable);
    }

    /**
     * Get one mouvement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Mouvement findOne(Long id) {
        log.debug("Request to get Mouvement : {}", id);
        return mouvementRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the mouvement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Mouvement : {}", id);
        mouvementRepository.delete(id);
    }
}
