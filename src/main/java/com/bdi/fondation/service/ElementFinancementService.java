package com.bdi.fondation.service;

import com.bdi.fondation.domain.ElementFinancement;
import com.bdi.fondation.repository.ElementFinancementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ElementFinancement.
 */
@Service
@Transactional
public class ElementFinancementService {

    private final Logger log = LoggerFactory.getLogger(ElementFinancementService.class);

    private final ElementFinancementRepository elementFinancementRepository;

    public ElementFinancementService(ElementFinancementRepository elementFinancementRepository) {
        this.elementFinancementRepository = elementFinancementRepository;
    }

    /**
     * Save a elementFinancement.
     *
     * @param elementFinancement the entity to save
     * @return the persisted entity
     */
    public ElementFinancement save(ElementFinancement elementFinancement) {
        log.debug("Request to save ElementFinancement : {}", elementFinancement);
        return elementFinancementRepository.save(elementFinancement);
    }

    /**
     * Get all the elementFinancements.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ElementFinancement> findAll() {
        log.debug("Request to get all ElementFinancements");
        return elementFinancementRepository.findAll();
    }

    /**
     * Get one elementFinancement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ElementFinancement findOne(Long id) {
        log.debug("Request to get ElementFinancement : {}", id);
        return elementFinancementRepository.findOne(id);
    }

    /**
     * Delete the elementFinancement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ElementFinancement : {}", id);
        elementFinancementRepository.delete(id);
    }
}
