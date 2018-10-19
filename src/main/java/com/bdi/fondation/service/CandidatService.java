package com.bdi.fondation.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.repository.CandidatRepository;
import com.bdi.fondation.repository.ExperienceCandidatRepository;
import com.bdi.fondation.service.dto.CandidatAggregate;

/**
 * Service Implementation for managing Candidat.
 */
@Service
@Transactional
public class CandidatService {

    private final Logger log = LoggerFactory.getLogger(CandidatService.class);

    private final CandidatRepository candidatRepository;

    private ExperienceCandidatRepository experienceCandidatRepository;

    public CandidatService(CandidatRepository candidatRepository, ExperienceCandidatRepository experienceCandidatRepository) {
        this.candidatRepository = candidatRepository;
        this.experienceCandidatRepository = experienceCandidatRepository;
    }

    /**
     * Save a candidat.
     *
     * @param candidat the entity to save
     * @return the persisted entity
     */
    public Candidat save(Candidat candidat) {
        log.debug("Request to save Candidat : {}", candidat);
        return candidatRepository.save(candidat);
    }

    /**
     * Get all the candidats.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Candidat> findAll() {
        log.debug("Request to get all Candidats");
        return candidatRepository.findAll();
    }

    /**
     * Get one candidat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Candidat findOne(Long id) {
        log.debug("Request to get Candidat : {}", id);
        return candidatRepository.findOne(id);
    }

    /**
     * Delete the candidat by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Candidat : {}", id);
        candidatRepository.delete(id);
    }

    public Candidat save(CandidatAggregate aggregate) {
        Candidat candidat = candidatRepository.save(aggregate.getCandidat());
        Arrays.stream(aggregate.getExps()).forEach(i->experienceCandidatRepository.save(i.candidat(candidat)));
        return candidat;
    }
}
