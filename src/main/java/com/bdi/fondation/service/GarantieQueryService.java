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

import com.bdi.fondation.domain.Garantie;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.GarantieRepository;
import com.bdi.fondation.service.dto.GarantieCriteria;


/**
 * Service for executing complex queries for Garantie entities in the database.
 * The main input is a {@link GarantieCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Garantie} or a {@link Page} of {@link Garantie} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GarantieQueryService extends QueryService<Garantie> {

    private final Logger log = LoggerFactory.getLogger(GarantieQueryService.class);


    private final GarantieRepository garantieRepository;

    public GarantieQueryService(GarantieRepository garantieRepository) {
        this.garantieRepository = garantieRepository;
    }

    /**
     * Return a {@link List} of {@link Garantie} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Garantie> findByCriteria(GarantieCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Garantie> specification = createSpecification(criteria);
        return garantieRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Garantie} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Garantie> findByCriteria(GarantieCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Garantie> specification = createSpecification(criteria);
        return garantieRepository.findAll(specification, page);
    }

    /**
     * Function to convert GarantieCriteria to a {@link Specifications}
     */
    private Specifications<Garantie> createSpecification(GarantieCriteria criteria) {
        Specifications<Garantie> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Garantie_.id));
            }
            if (criteria.getTypeGar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeGar(), Garantie_.typeGar));
            }
            if (criteria.getMontantEvalue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontantEvalue(), Garantie_.montantEvalue));
            }
            if (criteria.getMontantAfect() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontantAfect(), Garantie_.montantAfect));
            }
            if (criteria.getDateDepot() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDepot(), Garantie_.dateDepot));
            }
            if (criteria.getNumDocument() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumDocument(), Garantie_.numDocument));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtat(), Garantie_.etat));
            }
            if (criteria.getDateRetrait() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRetrait(), Garantie_.dateRetrait));
            }
            if (criteria.getPretId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPretId(), Garantie_.pret, Pret_.id));
            }
        }
        return specification;
    }

}
