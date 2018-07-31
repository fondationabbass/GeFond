package com.bdi.fondation.service;

import com.bdi.fondation.domain.Parametrage;
import com.bdi.fondation.repository.ParametrageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Parametrage.
 */
@Service
@Transactional
public class ParametrageService {

    private final Logger log = LoggerFactory.getLogger(ParametrageService.class);

    private final ParametrageRepository parametrageRepository;

    public ParametrageService(ParametrageRepository parametrageRepository) {
        this.parametrageRepository = parametrageRepository;
    }

    /**
     * Save a parametrage.
     *
     * @param parametrage the entity to save
     * @return the persisted entity
     */
    public Parametrage save(Parametrage parametrage) {
        log.debug("Request to save Parametrage : {}", parametrage);
        return parametrageRepository.save(parametrage);
    }

    /**
     * Get all the parametrages.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Parametrage> findAll() {
        log.debug("Request to get all Parametrages");
        return parametrageRepository.findAll();
    }

    /**
     * Get one parametrage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Parametrage findOne(Long id) {
        log.debug("Request to get Parametrage : {}", id);
        return parametrageRepository.findOne(id);
    }

    /**
     * Delete the parametrage by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Parametrage : {}", id);
        parametrageRepository.delete(id);
    }
}
