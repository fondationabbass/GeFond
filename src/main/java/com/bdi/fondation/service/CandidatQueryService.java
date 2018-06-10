package com.bdi.fondation.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.CandidatRepository;
import com.bdi.fondation.service.dto.CandidatCriteria;


/**
 * Service for executing complex queries for Candidat entities in the database.
 * The main input is a {@link CandidatCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Candidat} or a {@link Page} of {@link Candidat} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CandidatQueryService extends QueryService<Candidat> {

    private final Logger log = LoggerFactory.getLogger(CandidatQueryService.class);


    private final CandidatRepository candidatRepository;

    public CandidatQueryService(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    /**
     * Return a {@link List} of {@link Candidat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Candidat> findByCriteria(CandidatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Candidat> specification = createSpecification(criteria);
        return candidatRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Candidat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Candidat> findByCriteria(CandidatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Candidat> specification = createSpecification(criteria);
        return candidatRepository.findAll(specification, page);
    }

    /**
     * Function to convert CandidatCriteria to a {@link Specifications}
     */
    private Specifications<Candidat> createSpecification(CandidatCriteria criteria) {
        Specifications<Candidat> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Candidat_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Candidat_.nom));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), Candidat_.prenom));
            }
            if (criteria.getDateNaissance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateNaissance(), Candidat_.dateNaissance));
            }
            if (criteria.getLieuNaissance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLieuNaissance(), Candidat_.lieuNaissance));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), Candidat_.adresse));
            }
            if (criteria.getTel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTel(), Candidat_.tel));
            }
            if (criteria.getSituation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSituation(), Candidat_.situation));
            }
        }
        return specification;
    }

}