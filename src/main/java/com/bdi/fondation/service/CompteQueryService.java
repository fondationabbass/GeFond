package com.bdi.fondation.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bdi.fondation.domain.Compte;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.CompteRepository;
import com.bdi.fondation.service.dto.CompteCriteria;


/**
 * Service for executing complex queries for Compte entities in the database.
 * The main input is a {@link CompteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Compte} or a {@link Page} of {@link Compte} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompteQueryService extends QueryService<Compte> {

    private final Logger log = LoggerFactory.getLogger(CompteQueryService.class);

    private final CompteRepository compteRepository;

    public CompteQueryService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    /**
     * Return a {@link List} of {@link Compte} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Compte> findByCriteria(CompteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Compte> specification = createSpecification(criteria);
        return compteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Compte} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Compte> findByCriteria(CompteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Compte> specification = createSpecification(criteria);
        return compteRepository.findAll(specification, page);
    }

    /**
     * Function to convert CompteCriteria to a {@link Specification}
     */
    private Specification<Compte> createSpecification(CompteCriteria criteria) {
        Specifications<Compte> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Compte_.id));
            }
            if (criteria.getIntituleCompte() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIntituleCompte(), Compte_.intituleCompte));
            }
            if (criteria.getDateOuverture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOuverture(), Compte_.dateOuverture));
            }
            if (criteria.getSolde() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSolde(), Compte_.solde));
            }
            if (criteria.getDateDernierCredit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDernierCredit(), Compte_.dateDernierCredit));
            }
            if (criteria.getDateDernierDebit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDernierDebit(), Compte_.dateDernierDebit));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClientId(), Compte_.client, Client_.id));
            }
            if (criteria.getPretId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPretId(), Compte_.pret, Pret_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), Compte_.user, User_.id));
            }
            if (criteria.getChapitreId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getChapitreId(), Compte_.chapitre, Chapitre_.id));
            }
        }
        return specification;
    }

}
