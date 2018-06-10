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
import com.bdi.fondation.domain.Client;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.ClientRepository;
import com.bdi.fondation.service.dto.ClientCriteria;


/**
 * Service for executing complex queries for Client entities in the database.
 * The main input is a {@link ClientCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Client} or a {@link Page} of {@link Client} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientQueryService extends QueryService<Client> {

    private final Logger log = LoggerFactory.getLogger(ClientQueryService.class);


    private final ClientRepository clientRepository;

    public ClientQueryService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Return a {@link List} of {@link Client} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Client> findByCriteria(ClientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Client> specification = createSpecification(criteria);
        return clientRepository.findAll(specification);
    }
    @Transactional(readOnly = true)
    public List<Client> findLast3() {
        log.debug("find last three3");
        return  clientRepository.findFirst3ByOrderByIdDesc();
    }
    /**
     * Return a {@link Page} of {@link Client} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Client> findByCriteria(ClientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Client> specification = createSpecification(criteria);
        return clientRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClientCriteria to a {@link Specifications}
     */
    private Specifications<Client> createSpecification(ClientCriteria criteria) {
        Specifications<Client> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Client_.id));
            }
            if (criteria.getDateCreat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateCreat(), Client_.dateCreat));
            }
            if (criteria.getLieuResid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLieuResid(), Client_.lieuResid));
            }
            if (criteria.getTypeResid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeResid(), Client_.typeResid));
            }
            if (criteria.getArrondResid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArrondResid(), Client_.arrondResid));
            }
            if (criteria.getNomPersonneContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomPersonneContact(), Client_.nomPersonneContact));
            }
            if (criteria.getTelPersonneContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelPersonneContact(), Client_.telPersonneContact));
            }
            if (criteria.getAdressPersonneContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdressPersonneContact(), Client_.adressPersonneContact));
            }
            if (criteria.getTypeClient() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeClient(), Client_.typeClient));
            }
            if (criteria.getPointsFidel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPointsFidel(), Client_.pointsFidel));
            }
            if (criteria.getDateMaj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateMaj(), Client_.dateMaj));
            }
            if (criteria.getCandidatId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCandidatId(), Client_.candidat, Candidat_.id));
            }
        }
        return specification;
    }

}
