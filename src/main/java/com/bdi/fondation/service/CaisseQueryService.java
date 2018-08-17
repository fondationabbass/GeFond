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
import com.bdi.fondation.domain.Caisse;
import com.bdi.fondation.domain.Caisse_;
import com.bdi.fondation.domain.User_;
import com.bdi.fondation.repository.CaisseRepository;
import com.bdi.fondation.service.dto.CaisseCriteria;

import io.github.jhipster.service.QueryService;


/**
 * Service for executing complex queries for Caisse entities in the database.
 * The main input is a {@link CaisseCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Caisse} or a {@link Page} of {@link Caisse} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CaisseQueryService extends QueryService<Caisse> {

    private final Logger log = LoggerFactory.getLogger(CaisseQueryService.class);


    private final CaisseRepository caisseRepository;

    public CaisseQueryService(CaisseRepository caisseRepository) {
        this.caisseRepository = caisseRepository;
    }

    /**
     * Return a {@link List} of {@link Caisse} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Caisse> findByCriteria(CaisseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Caisse> specification = createSpecification(criteria);
        return caisseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Caisse} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Caisse> findByCriteria(CaisseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Caisse> specification = createSpecification(criteria);
        return caisseRepository.findAll(specification, page);
    }

    /**
     * Function to convert CaisseCriteria to a {@link Specifications}
     */
    private Specifications<Caisse> createSpecification(CaisseCriteria criteria) {
        Specifications<Caisse> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Caisse_.id));
            }
            if (criteria.getIntituleCaisse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIntituleCaisse(), Caisse_.intituleCaisse));
            }
            if (criteria.getDateOuverture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOuverture(), Caisse_.dateOuverture));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), Caisse_.user, User_.id));
            }
        }
        return specification;
    }

    @Transactional(readOnly = true)
    public Caisse getCurrentCaisse() {
        List<Caisse> list = caisseRepository.findByUserIsCurrentUser();
        if(list!=null && list.size()==1) {
            return list.get(0);
        }
        throw new IllegalStateException("Trouvé "+list+" caisse(s) pour votre utilisateur");
    }

}
