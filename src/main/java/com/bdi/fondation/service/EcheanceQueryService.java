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
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;

import com.bdi.fondation.domain.Echeance;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.EcheanceRepository;
import com.bdi.fondation.service.dto.EcheanceCriteria;


/**
 * Service for executing complex queries for Echeance entities in the database.
 * The main input is a {@link EcheanceCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Echeance} or a {@link Page} of {@link Echeance} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EcheanceQueryService extends QueryService<Echeance> {

    private final Logger log = LoggerFactory.getLogger(EcheanceQueryService.class);


    private final EcheanceRepository echeanceRepository;

    public EcheanceQueryService(EcheanceRepository echeanceRepository) {
        this.echeanceRepository = echeanceRepository;
    }

    /**
     * Return a {@link List} of {@link Echeance} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Echeance> findByCriteria(EcheanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Echeance> specification = createSpecification(criteria);
        return echeanceRepository.findAll(specification);
    }
    @Transactional(readOnly = true)
    public List<Echeance> findNotPayedByPretId(long pretId) {
    	EcheanceCriteria criteria = new EcheanceCriteria();
		LongFilter longFilter = new LongFilter();
		longFilter.setEquals(pretId);
		criteria.setPretId(longFilter);
		LocalDateFilter datePayement = new LocalDateFilter();
		datePayement.setSpecified(false);
		criteria.setDatePayement(datePayement );
    	return findByCriteria(criteria);
    }

    /**
     * Return a {@link Page} of {@link Echeance} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Echeance> findByCriteria(EcheanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Echeance> specification = createSpecification(criteria);
        return echeanceRepository.findAll(specification, page);
    }

    /**
     * Function to convert EcheanceCriteria to a {@link Specifications}
     */
    private Specifications<Echeance> createSpecification(EcheanceCriteria criteria) {
        Specifications<Echeance> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Echeance_.id));
            }
            if (criteria.getDateTombe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTombe(), Echeance_.dateTombe));
            }
            if (criteria.getMontant() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontant(), Echeance_.montant));
            }
            if (criteria.getEtatEcheance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtatEcheance(), Echeance_.etatEcheance));
            }
            if (criteria.getDatePayement() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePayement(), Echeance_.datePayement));
            }
            if (criteria.getDateRetrait() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRetrait(), Echeance_.dateRetrait));
            }
            if (criteria.getPretId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPretId(), Echeance_.pret, Pret_.id));
            }
            if (criteria.getMouvementId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMouvementId(), Echeance_.mouvements, Mouvement_.id));
            }
        }
        return specification;
    }

}
