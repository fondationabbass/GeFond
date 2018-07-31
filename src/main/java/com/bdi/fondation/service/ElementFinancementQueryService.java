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

import com.bdi.fondation.domain.ElementFinancement;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.ElementFinancementRepository;
import com.bdi.fondation.service.dto.ElementFinancementCriteria;


/**
 * Service for executing complex queries for ElementFinancement entities in the database.
 * The main input is a {@link ElementFinancementCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ElementFinancement} or a {@link Page} of {@link ElementFinancement} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ElementFinancementQueryService extends QueryService<ElementFinancement> {

    private final Logger log = LoggerFactory.getLogger(ElementFinancementQueryService.class);


    private final ElementFinancementRepository elementFinancementRepository;

    public ElementFinancementQueryService(ElementFinancementRepository elementFinancementRepository) {
        this.elementFinancementRepository = elementFinancementRepository;
    }

    /**
     * Return a {@link List} of {@link ElementFinancement} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ElementFinancement> findByCriteria(ElementFinancementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ElementFinancement> specification = createSpecification(criteria);
        return elementFinancementRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ElementFinancement} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ElementFinancement> findByCriteria(ElementFinancementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ElementFinancement> specification = createSpecification(criteria);
        return elementFinancementRepository.findAll(specification, page);
    }

    /**
     * Function to convert ElementFinancementCriteria to a {@link Specifications}
     */
    private Specifications<ElementFinancement> createSpecification(ElementFinancementCriteria criteria) {
        Specifications<ElementFinancement> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ElementFinancement_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ElementFinancement_.type));
            }
            if (criteria.getMontant() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontant(), ElementFinancement_.montant));
            }
            if (criteria.getDateFinancement() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFinancement(), ElementFinancement_.dateFinancement));
            }
            if (criteria.getPretId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPretId(), ElementFinancement_.pret, Pret_.id));
            }
        }
        return specification;
    }

}
