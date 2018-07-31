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

import com.bdi.fondation.domain.ExperienceCandidat;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.ExperienceCandidatRepository;
import com.bdi.fondation.service.dto.ExperienceCandidatCriteria;


/**
 * Service for executing complex queries for ExperienceCandidat entities in the database.
 * The main input is a {@link ExperienceCandidatCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExperienceCandidat} or a {@link Page} of {@link ExperienceCandidat} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExperienceCandidatQueryService extends QueryService<ExperienceCandidat> {

    private final Logger log = LoggerFactory.getLogger(ExperienceCandidatQueryService.class);


    private final ExperienceCandidatRepository experienceCandidatRepository;

    public ExperienceCandidatQueryService(ExperienceCandidatRepository experienceCandidatRepository) {
        this.experienceCandidatRepository = experienceCandidatRepository;
    }

    /**
     * Return a {@link List} of {@link ExperienceCandidat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExperienceCandidat> findByCriteria(ExperienceCandidatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ExperienceCandidat> specification = createSpecification(criteria);
        return experienceCandidatRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ExperienceCandidat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExperienceCandidat> findByCriteria(ExperienceCandidatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ExperienceCandidat> specification = createSpecification(criteria);
        return experienceCandidatRepository.findAll(specification, page);
    }

    /**
     * Function to convert ExperienceCandidatCriteria to a {@link Specifications}
     */
    private Specifications<ExperienceCandidat> createSpecification(ExperienceCandidatCriteria criteria) {
        Specifications<ExperienceCandidat> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ExperienceCandidat_.id));
            }
            if (criteria.getTypeInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeInfo(), ExperienceCandidat_.typeInfo));
            }
            if (criteria.getTitre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitre(), ExperienceCandidat_.titre));
            }
            if (criteria.getEtab() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtab(), ExperienceCandidat_.etab));
            }
            if (criteria.getAdressEtab() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdressEtab(), ExperienceCandidat_.adressEtab));
            }
            if (criteria.getDateDeb() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDeb(), ExperienceCandidat_.dateDeb));
            }
            if (criteria.getDateFin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFin(), ExperienceCandidat_.dateFin));
            }
            if (criteria.getCandidatId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCandidatId(), ExperienceCandidat_.candidat, Candidat_.id));
            }
        }
        return specification;
    }

}
