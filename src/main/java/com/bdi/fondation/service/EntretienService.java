package com.bdi.fondation.service;

import com.bdi.fondation.domain.Entretien;
import com.bdi.fondation.repository.EntretienRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Entretien.
 */
@Service
@Transactional
public class EntretienService {

    private final Logger log = LoggerFactory.getLogger(EntretienService.class);

    private final EntretienRepository entretienRepository;

    public EntretienService(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    /**
     * Save a entretien.
     *
     * @param entretien the entity to save
     * @return the persisted entity
     */
    public Entretien save(Entretien entretien) {
        log.debug("Request to save Entretien : {}", entretien);
        return entretienRepository.save(entretien);
    }

    /**
     * Get all the entretiens.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Entretien> findAll() {
        log.debug("Request to get all Entretiens");
        return entretienRepository.findAll();
    }

    /**
     * Get one entretien by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Entretien findOne(Long id) {
        log.debug("Request to get Entretien : {}", id);
        return entretienRepository.findOne(id);
    }

    /**
     * Delete the entretien by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Entretien : {}", id);
        entretienRepository.delete(id);
    }
}
