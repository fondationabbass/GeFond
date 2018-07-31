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

import com.bdi.fondation.domain.Parametrage;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.ParametrageRepository;
import com.bdi.fondation.service.dto.ParametrageCriteria;


/**
 * Service for executing complex queries for Parametrage entities in the database.
 * The main input is a {@link ParametrageCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Parametrage} or a {@link Page} of {@link Parametrage} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParametrageQueryService extends QueryService<Parametrage> {

    private final Logger log = LoggerFactory.getLogger(ParametrageQueryService.class);


    private final ParametrageRepository parametrageRepository;

    public ParametrageQueryService(ParametrageRepository parametrageRepository) {
        this.parametrageRepository = parametrageRepository;
    }

    /**
     * Return a {@link List} of {@link Parametrage} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Parametrage> findByCriteria(ParametrageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Parametrage> specification = createSpecification(criteria);
        return parametrageRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Parametrage} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Parametrage> findByCriteria(ParametrageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Parametrage> specification = createSpecification(criteria);
        return parametrageRepository.findAll(specification, page);
    }

    /**
     * Function to convert ParametrageCriteria to a {@link Specifications}
     */
    private Specifications<Parametrage> createSpecification(ParametrageCriteria criteria) {
        Specifications<Parametrage> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Parametrage_.id));
            }
            if (criteria.getCodeTypeParam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeTypeParam(), Parametrage_.codeTypeParam));
            }
            if (criteria.getCodeParam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeParam(), Parametrage_.codeParam));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Parametrage_.libelle));
            }
            if (criteria.getLib1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLib1(), Parametrage_.lib1));
            }
            if (criteria.getLib2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLib2(), Parametrage_.lib2));
            }
            if (criteria.getLib3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLib3(), Parametrage_.lib3));
            }
            if (criteria.getMnt1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMnt1(), Parametrage_.mnt1));
            }
            if (criteria.getMnt2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMnt2(), Parametrage_.mnt2));
            }
            if (criteria.getMnt3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMnt3(), Parametrage_.mnt3));
            }
        }
        return specification;
    }

}
