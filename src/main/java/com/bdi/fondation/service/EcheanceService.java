package com.bdi.fondation.service;

import com.bdi.fondation.domain.Echeance;
import com.bdi.fondation.repository.EcheanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Echeance.
 */
@Service
@Transactional
public class EcheanceService {

    private final Logger log = LoggerFactory.getLogger(EcheanceService.class);

    private final EcheanceRepository echeanceRepository;

    public EcheanceService(EcheanceRepository echeanceRepository) {
        this.echeanceRepository = echeanceRepository;
    }

    /**
     * Save a echeance.
     *
     * @param echeance the entity to save
     * @return the persisted entity
     */
    public Echeance save(Echeance echeance) {
        log.debug("Request to save Echeance : {}", echeance);
        return echeanceRepository.save(echeance);
    }

    /**
     * Get all the echeances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Echeance> findAll(Pageable pageable) {
        log.debug("Request to get all Echeances");
        return echeanceRepository.findAll(pageable);
    }

    /**
     * Get one echeance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Echeance findOne(Long id) {
        log.debug("Request to get Echeance : {}", id);
        return echeanceRepository.findOne(id);
    }

    /**
     * Delete the echeance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Echeance : {}", id);
        echeanceRepository.delete(id);
    }
}
