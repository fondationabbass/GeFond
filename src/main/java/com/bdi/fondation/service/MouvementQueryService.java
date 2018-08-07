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

import com.bdi.fondation.domain.Mouvement;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.MouvementRepository;
import com.bdi.fondation.service.dto.MouvementCriteria;


/**
 * Service for executing complex queries for Mouvement entities in the database.
 * The main input is a {@link MouvementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Mouvement} or a {@link Page} of {@link Mouvement} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MouvementQueryService extends QueryService<Mouvement> {

    private final Logger log = LoggerFactory.getLogger(MouvementQueryService.class);

    private final MouvementRepository mouvementRepository;

    public MouvementQueryService(MouvementRepository mouvementRepository) {
        this.mouvementRepository = mouvementRepository;
    }

    /**
     * Return a {@link List} of {@link Mouvement} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Mouvement> findByCriteria(MouvementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Mouvement> specification = createSpecification(criteria);
        return mouvementRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Mouvement} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Mouvement> findByCriteria(MouvementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Mouvement> specification = createSpecification(criteria);
        return mouvementRepository.findAll(specification, page);
    }

    /**
     * Function to convert MouvementCriteria to a {@link Specification}
     */
    private Specification<Mouvement> createSpecification(MouvementCriteria criteria) {
        Specifications<Mouvement> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Mouvement_.id));
            }
            if (criteria.getDateMvt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateMvt(), Mouvement_.dateMvt));
            }
            if (criteria.getLib() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLib(), Mouvement_.lib));
            }
            if (criteria.getMontant() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontant(), Mouvement_.montant));
            }
            if (criteria.getSens() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSens(), Mouvement_.sens));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtat(), Mouvement_.etat));
            }
            if (criteria.getCompteId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCompteId(), Mouvement_.compte, Compte_.id));
            }
            if (criteria.getCompteDestinataireId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCompteDestinataireId(), Mouvement_.compteDestinataire, Compte_.id));
            }
            if (criteria.getPretId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPretId(), Mouvement_.pret, Pret_.id));
            }
            if (criteria.getEcheanceId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getEcheanceId(), Mouvement_.echeances, Echeance_.id));
            }
        }
        return specification;
    }

}
