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

import com.bdi.fondation.domain.Projet;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.ProjetRepository;
import com.bdi.fondation.service.dto.ProjetCriteria;


/**
 * Service for executing complex queries for Projet entities in the database.
 * The main input is a {@link ProjetCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Projet} or a {@link Page} of {@link Projet} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjetQueryService extends QueryService<Projet> {

    private final Logger log = LoggerFactory.getLogger(ProjetQueryService.class);


    private final ProjetRepository projetRepository;

    public ProjetQueryService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    /**
     * Return a {@link List} of {@link Projet} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Projet> findByCriteria(ProjetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Projet> specification = createSpecification(criteria);
        return projetRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Projet} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Projet> findByCriteria(ProjetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Projet> specification = createSpecification(criteria);
        return projetRepository.findAll(specification, page);
    }

    /**
     * Function to convert ProjetCriteria to a {@link Specifications}
     */
    private Specifications<Projet> createSpecification(ProjetCriteria criteria) {
        Specifications<Projet> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Projet_.id));
            }
            if (criteria.getIntitule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIntitule(), Projet_.intitule));
            }
            if (criteria.getMontEstime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontEstime(), Projet_.montEstime));
            }
            if (criteria.getMontApp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontApp(), Projet_.montApp));
            }
            if (criteria.getDomaine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDomaine(), Projet_.domaine));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Projet_.type));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Projet_.description));
            }
            if (criteria.getDateCreation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateCreation(), Projet_.dateCreation));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtat(), Projet_.etat));
            }
            if (criteria.getLieu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLieu(), Projet_.lieu));
            }
            if (criteria.getCandidatureId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCandidatureId(), Projet_.candidature, Candidature_.id));
            }
        }
        return specification;
    }

}
