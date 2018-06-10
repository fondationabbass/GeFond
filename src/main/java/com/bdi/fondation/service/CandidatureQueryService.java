package com.bdi.fondation.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bdi.fondation.domain.Candidature;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.CandidatureRepository;
import com.bdi.fondation.service.dto.CandidatureCriteria;


/**
 * Service for executing complex queries for Candidature entities in the database.
 * The main input is a {@link CandidatureCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Candidature} or a {@link Page} of {@link Candidature} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CandidatureQueryService extends QueryService<Candidature> {

    private final Logger log = LoggerFactory.getLogger(CandidatureQueryService.class);


    private final CandidatureRepository candidatureRepository;

    public CandidatureQueryService(CandidatureRepository candidatureRepository) {
        this.candidatureRepository = candidatureRepository;
    }

    /**
     * Return a {@link List} of {@link Candidature} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Candidature> findByCriteria(CandidatureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Candidature> specification = createSpecification(criteria);
        return candidatureRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Candidature} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Candidature> findByCriteria(CandidatureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Candidature> specification = createSpecification(criteria);
        return candidatureRepository.findAll(specification, page);
    }

    /**
     * Function to convert CandidatureCriteria to a {@link Specifications}
     */
    private Specifications<Candidature> createSpecification(CandidatureCriteria criteria) {
        Specifications<Candidature> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Candidature_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Candidature_.type));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Candidature_.status));
            }
            if (criteria.getSessionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSessionId(), Candidature_.session, SessionProjet_.id));
            }
            if (criteria.getCandidatId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCandidatId(), Candidature_.candidat, Candidat_.id));
            }
        }
        return specification;
    }

}
