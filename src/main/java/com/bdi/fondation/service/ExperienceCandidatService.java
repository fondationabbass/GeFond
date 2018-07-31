package com.bdi.fondation.service;

import com.bdi.fondation.domain.ExperienceCandidat;
import com.bdi.fondation.repository.ExperienceCandidatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ExperienceCandidat.
 */
@Service
@Transactional
public class ExperienceCandidatService {

    private final Logger log = LoggerFactory.getLogger(ExperienceCandidatService.class);

    private final ExperienceCandidatRepository experienceCandidatRepository;

    public ExperienceCandidatService(ExperienceCandidatRepository experienceCandidatRepository) {
        this.experienceCandidatRepository = experienceCandidatRepository;
    }

    /**
     * Save a experienceCandidat.
     *
     * @param experienceCandidat the entity to save
     * @return the persisted entity
     */
    public ExperienceCandidat save(ExperienceCandidat experienceCandidat) {
        log.debug("Request to save ExperienceCandidat : {}", experienceCandidat);
        return experienceCandidatRepository.save(experienceCandidat);
    }

    /**
     * Get all the experienceCandidats.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ExperienceCandidat> findAll() {
        log.debug("Request to get all ExperienceCandidats");
        return experienceCandidatRepository.findAll();
    }

    /**
     * Get one experienceCandidat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ExperienceCandidat findOne(Long id) {
        log.debug("Request to get ExperienceCandidat : {}", id);
        return experienceCandidatRepository.findOne(id);
    }

    /**
     * Delete the experienceCandidat by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExperienceCandidat : {}", id);
        experienceCandidatRepository.delete(id);
    }
}
