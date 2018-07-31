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

import com.bdi.fondation.domain.Visite;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.VisiteRepository;
import com.bdi.fondation.service.dto.VisiteCriteria;


/**
 * Service for executing complex queries for Visite entities in the database.
 * The main input is a {@link VisiteCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Visite} or a {@link Page} of {@link Visite} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VisiteQueryService extends QueryService<Visite> {

    private final Logger log = LoggerFactory.getLogger(VisiteQueryService.class);


    private final VisiteRepository visiteRepository;

    public VisiteQueryService(VisiteRepository visiteRepository) {
        this.visiteRepository = visiteRepository;
    }

    /**
     * Return a {@link List} of {@link Visite} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Visite> findByCriteria(VisiteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Visite> specification = createSpecification(criteria);
        return visiteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Visite} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Visite> findByCriteria(VisiteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Visite> specification = createSpecification(criteria);
        return visiteRepository.findAll(specification, page);
    }

    /**
     * Function to convert VisiteCriteria to a {@link Specifications}
     */
    private Specifications<Visite> createSpecification(VisiteCriteria criteria) {
        Specifications<Visite> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Visite_.id));
            }
            if (criteria.getLieuVisite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLieuVisite(), Visite_.lieuVisite));
            }
            if (criteria.getDateVisite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateVisite(), Visite_.dateVisite));
            }
            if (criteria.getPersRencontre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPersRencontre(), Visite_.persRencontre));
            }
            if (criteria.getCadreVisite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCadreVisite(), Visite_.cadreVisite));
            }
            if (criteria.getEtatLieu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtatLieu(), Visite_.etatLieu));
            }
            if (criteria.getVisiteur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVisiteur(), Visite_.visiteur));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtat(), Visite_.etat));
            }
            if (criteria.getRecomendation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRecomendation(), Visite_.recomendation));
            }
            if (criteria.getRapport() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRapport(), Visite_.rapport));
            }
            if (criteria.getCandidatureId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCandidatureId(), Visite_.candidature, Candidature_.id));
            }
        }
        return specification;
    }

}
