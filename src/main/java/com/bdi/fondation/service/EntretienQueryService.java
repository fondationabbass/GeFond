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

import com.bdi.fondation.domain.Entretien;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.EntretienRepository;
import com.bdi.fondation.service.dto.EntretienCriteria;


/**
 * Service for executing complex queries for Entretien entities in the database.
 * The main input is a {@link EntretienCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Entretien} or a {@link Page} of {@link Entretien} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EntretienQueryService extends QueryService<Entretien> {

    private final Logger log = LoggerFactory.getLogger(EntretienQueryService.class);


    private final EntretienRepository entretienRepository;

    public EntretienQueryService(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    /**
     * Return a {@link List} of {@link Entretien} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Entretien> findByCriteria(EntretienCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Entretien> specification = createSpecification(criteria);
        return entretienRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Entretien} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Entretien> findByCriteria(EntretienCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Entretien> specification = createSpecification(criteria);
        return entretienRepository.findAll(specification, page);
    }

    /**
     * Function to convert EntretienCriteria to a {@link Specifications}
     */
    private Specifications<Entretien> createSpecification(EntretienCriteria criteria) {
        Specifications<Entretien> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Entretien_.id));
            }
            if (criteria.getCadre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCadre(), Entretien_.cadre));
            }
            if (criteria.getResultat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResultat(), Entretien_.resultat));
            }
            if (criteria.getInterlocuteur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInterlocuteur(), Entretien_.interlocuteur));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtat(), Entretien_.etat));
            }
            if (criteria.getDateEntretien() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateEntretien(), Entretien_.dateEntretien));
            }
            if (criteria.getCandidatureId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCandidatureId(), Entretien_.candidature, Candidature_.id));
            }
        }
        return specification;
    }

}
