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

import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.PretRepository;
import com.bdi.fondation.service.dto.PretCriteria;


/**
 * Service for executing complex queries for Pret entities in the database.
 * The main input is a {@link PretCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Pret} or a {@link Page} of {@link Pret} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PretQueryService extends QueryService<Pret> {

    private final Logger log = LoggerFactory.getLogger(PretQueryService.class);


    private final PretRepository pretRepository;

    public PretQueryService(PretRepository pretRepository) {
        this.pretRepository = pretRepository;
    }

    /**
     * Return a {@link List} of {@link Pret} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Pret> findByCriteria(PretCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Pret> specification = createSpecification(criteria);
        return pretRepository.findAll(specification);
    }
    @Transactional(readOnly = true)
    public List<Pret> findLast3() {
        log.debug("find last three3");
        return pretRepository.findFirst3ByOrderByIdDesc();
    }
    /**
     * Return a {@link Page} of {@link Pret} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Pret> findByCriteria(PretCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Pret> specification = createSpecification(criteria);
        return pretRepository.findAll(specification, page);
    }

    /**
     * Function to convert PretCriteria to a {@link Specifications}
     */
    private Specifications<Pret> createSpecification(PretCriteria criteria) {
        Specifications<Pret> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Pret_.id));
            }
            if (criteria.getTypPret() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypPret(), Pret_.typPret));
            }
            if (criteria.getMontAaccord() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontAaccord(), Pret_.montAaccord));
            }
            if (criteria.getMontDebloq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontDebloq(), Pret_.montDebloq));
            }
            if (criteria.getNbrEcheance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbrEcheance(), Pret_.nbrEcheance));
            }
            if (criteria.getPeriodicite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPeriodicite(), Pret_.periodicite));
            }
            if (criteria.getDateMisePlace() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateMisePlace(), Pret_.dateMisePlace));
            }
            if (criteria.getDatePremiereEcheance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePremiereEcheance(), Pret_.datePremiereEcheance));
            }
            if (criteria.getDateDerniereEcheance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDerniereEcheance(), Pret_.dateDerniereEcheance));
            }
            if (criteria.getDateDernierDebloq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDernierDebloq(), Pret_.dateDernierDebloq));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtat(), Pret_.etat));
            }
            if (criteria.getEncours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEncours(), Pret_.encours));
            }
            if (criteria.getUserInitial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserInitial(), Pret_.userInitial));
            }
            if (criteria.getUserDecideur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserDecideur(), Pret_.userDecideur));
            }
            if (criteria.getUserDebloq() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserDebloq(), Pret_.userDebloq));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClientId(), Pret_.client, Client_.id));
            }
        }
        return specification;
    }

}
