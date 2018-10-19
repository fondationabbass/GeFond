package com.bdi.fondation.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.bdi.fondation.domain.SessionProjet;
import com.bdi.fondation.domain.SessionProjet_;
import com.bdi.fondation.repository.SessionProjetRepository;
import com.bdi.fondation.service.dto.SessionProjetCriteria;

import io.github.jhipster.service.QueryService;


/**
 * Service for executing complex queries for SessionProjet entities in the database.
 * The main input is a {@link SessionProjetCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SessionProjet} or a {@link Page} of {@link SessionProjet} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SessionProjetQueryService extends QueryService<SessionProjet> {

    private final Logger log = LoggerFactory.getLogger(SessionProjetQueryService.class);


    private final SessionProjetRepository sessionProjetRepository;

    public SessionProjetQueryService(SessionProjetRepository sessionProjetRepository) {
        this.sessionProjetRepository = sessionProjetRepository;
    }

    /**
     * Return a {@link List} of {@link SessionProjet} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SessionProjet> findByCriteria(SessionProjetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<SessionProjet> specification = createSpecification(criteria);
        return sessionProjetRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SessionProjet} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SessionProjet> findByCriteria(SessionProjetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<SessionProjet> specification = createSpecification(criteria);
        return sessionProjetRepository.findAll(specification, page);
    }

    /**
     * Function to convert SessionProjetCriteria to a {@link Specifications}
     */
    private Specifications<SessionProjet> createSpecification(SessionProjetCriteria criteria) {
        Specifications<SessionProjet> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SessionProjet_.id));
            }
            if (criteria.getDateOuvert() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOuvert(), SessionProjet_.dateOuvert));
            }
            if (criteria.getDateFermeture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFermeture(), SessionProjet_.dateFermeture));
            }
            if (criteria.getPlafondFinance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlafondFinance(), SessionProjet_.plafondFinance));
            }
            if (criteria.getNombreClient() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNombreClient(), SessionProjet_.nombreClient));
            }
            if (criteria.getPlafondClient() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlafondClient(), SessionProjet_.plafondClient));
            }
            if (criteria.getDateCreat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateCreat(), SessionProjet_.dateCreat));
            }
            if (criteria.getDateMaj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateMaj(), SessionProjet_.dateMaj));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtat(), SessionProjet_.etat));
            }
        }
        return specification;
    }

}
