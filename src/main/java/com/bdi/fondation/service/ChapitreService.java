package com.bdi.fondation.service;

import com.bdi.fondation.domain.Chapitre;
import com.bdi.fondation.repository.ChapitreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Chapitre.
 */
@Service
@Transactional
public class ChapitreService {

    private final Logger log = LoggerFactory.getLogger(ChapitreService.class);

    private final ChapitreRepository chapitreRepository;

    public ChapitreService(ChapitreRepository chapitreRepository) {
        this.chapitreRepository = chapitreRepository;
    }

    /**
     * Save a chapitre.
     *
     * @param chapitre the entity to save
     * @return the persisted entity
     */
    public Chapitre save(Chapitre chapitre) {
        log.debug("Request to save Chapitre : {}", chapitre);
        return chapitreRepository.save(chapitre);
    }

    /**
     * Get all the chapitres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Chapitre> findAll(Pageable pageable) {
        log.debug("Request to get all Chapitres");
        return chapitreRepository.findAll(pageable);
    }

    /**
     * Get one chapitre by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Chapitre findOne(Long id) {
        log.debug("Request to get Chapitre : {}", id);
        return chapitreRepository.findOne(id);
    }

    /**
     * Delete the chapitre by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Chapitre : {}", id);
        chapitreRepository.delete(id);
    }
}
