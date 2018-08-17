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

import com.bdi.fondation.domain.Operation;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.OperationRepository;
import com.bdi.fondation.service.dto.OperationCriteria;


/**
 * Service for executing complex queries for Operation entities in the database.
 * The main input is a {@link OperationCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Operation} or a {@link Page} of {@link Operation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OperationQueryService extends QueryService<Operation> {

    private final Logger log = LoggerFactory.getLogger(OperationQueryService.class);


    private final OperationRepository operationRepository;

    public OperationQueryService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    /**
     * Return a {@link List} of {@link Operation} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Operation> findByCriteria(OperationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Operation> specification = createSpecification(criteria);
        return operationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Operation} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Operation> findByCriteria(OperationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Operation> specification = createSpecification(criteria);
        return operationRepository.findAll(specification, page);
    }

    /**
     * Function to convert OperationCriteria to a {@link Specifications}
     */
    private Specifications<Operation> createSpecification(OperationCriteria criteria) {
        Specifications<Operation> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Operation_.id));
            }
            if (criteria.getDateOperation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOperation(), Operation_.dateOperation));
            }
            if (criteria.getTypeOperation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeOperation(), Operation_.typeOperation));
            }
            if (criteria.getMontant() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontant(), Operation_.montant));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtat(), Operation_.etat));
            }
            if (criteria.getMoyenPaiement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMoyenPaiement(), Operation_.moyenPaiement));
            }
            if (criteria.getCommentaire() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommentaire(), Operation_.commentaire));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Operation_.description));
            }
            if (criteria.getCompteOriginId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCompteOriginId(), Operation_.compteOrigin, Compte_.id));
            }
            if (criteria.getCompteDestinataireId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCompteDestinataireId(), Operation_.compteDestinataire, Compte_.id));
            }
            if (criteria.getPretId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPretId(), Operation_.pret, Pret_.id));
            }
            if (criteria.getCaisseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCaisseId(), Operation_.caisse, Caisse_.id));
            }
            if (criteria.getEcheanceId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getEcheanceId(), Operation_.echeances, Echeance_.id));
            }
        }
        return specification;
    }

}
