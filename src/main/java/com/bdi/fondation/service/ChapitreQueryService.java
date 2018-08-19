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

import com.bdi.fondation.domain.Chapitre;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.ChapitreRepository;
import com.bdi.fondation.service.dto.ChapitreCriteria;


/**
 * Service for executing complex queries for Chapitre entities in the database.
 * The main input is a {@link ChapitreCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Chapitre} or a {@link Page} of {@link Chapitre} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChapitreQueryService extends QueryService<Chapitre> {

    private final Logger log = LoggerFactory.getLogger(ChapitreQueryService.class);


    private final ChapitreRepository chapitreRepository;

    public ChapitreQueryService(ChapitreRepository chapitreRepository) {
        this.chapitreRepository = chapitreRepository;
    }

    /**
     * Return a {@link List} of {@link Chapitre} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Chapitre> findByCriteria(ChapitreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Chapitre> specification = createSpecification(criteria);
        return chapitreRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Chapitre} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Chapitre> findByCriteria(ChapitreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Chapitre> specification = createSpecification(criteria);
        return chapitreRepository.findAll(specification, page);
    }

    /**
     * Function to convert ChapitreCriteria to a {@link Specifications}
     */
    private Specifications<Chapitre> createSpecification(ChapitreCriteria criteria) {
        Specifications<Chapitre> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Chapitre_.id));
            }
            if (criteria.getLibChapitre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibChapitre(), Chapitre_.libChapitre));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumero(), Chapitre_.numero));
            }
            if (criteria.getCategorieCompte() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategorieCompte(), Chapitre_.categorieCompte));
            }
        }
        return specification;
    }

}
