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
import com.bdi.fondation.domain.Candidat_;
import com.bdi.fondation.domain.Candidature;
import com.bdi.fondation.domain.Candidature_;
import com.bdi.fondation.domain.Document;
import com.bdi.fondation.domain.Entretien;
import com.bdi.fondation.domain.Projet;
import com.bdi.fondation.domain.SessionProjet_;
import com.bdi.fondation.domain.Visite;
import com.bdi.fondation.repository.CandidatureRepository;
import com.bdi.fondation.service.dto.CandidatureAggregate;
import com.bdi.fondation.service.dto.CandidatureCriteria;
import com.bdi.fondation.service.dto.DocumentCriteria;
import com.bdi.fondation.service.dto.EntretienCriteria;
import com.bdi.fondation.service.dto.ProjetCriteria;
import com.bdi.fondation.service.dto.VisiteCriteria;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.LongFilter;


/**
 * Service for executing complex queries for Candidature entities in the database.
 * The main input is a {@link CandidatureCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Candidature} or a {@link Page} of {@link Candidature} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CandidatureQueryService extends QueryService<Candidature> {

    private final Logger log = LoggerFactory.getLogger(CandidatureQueryService.class);


    private final CandidatureRepository candidatureRepository;
    private DocumentQueryService documentQueryService;
    private EntretienQueryService entretienQueryService;
    private VisiteQueryService visiteQueryService;
    private ProjetQueryService projetQueryService;

    public CandidatureQueryService(CandidatureRepository candidatureRepository, DocumentQueryService documentQueryService,
            EntretienQueryService entretienQueryService, VisiteQueryService visiteQueryService, ProjetQueryService projetQueryService) {
        this.candidatureRepository = candidatureRepository;
        this.documentQueryService = documentQueryService;
        this.entretienQueryService = entretienQueryService;
        this.visiteQueryService = visiteQueryService;
        this.projetQueryService = projetQueryService;
    }

    /**
     * Return a {@link List} of {@link Candidature} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Candidature> findByCriteria(CandidatureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Candidature> specification = createSpecification(criteria);
        return candidatureRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Candidature} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Candidature> findByCriteria(CandidatureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Candidature> specification = createSpecification(criteria);
        return candidatureRepository.findAll(specification, page);
    }

    /**
     * Function to convert CandidatureCriteria to a {@link Specifications}
     */
    private Specifications<Candidature> createSpecification(CandidatureCriteria criteria) {
        Specifications<Candidature> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Candidature_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Candidature_.type));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Candidature_.status));
            }
            if (criteria.getSessionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSessionId(), Candidature_.session, SessionProjet_.id));
            }
            if (criteria.getCandidatId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCandidatId(), Candidature_.candidat, Candidat_.id));
            }
        }
        return specification;
    }

    public CandidatureAggregate findAggregate(Long id) {

        CandidatureAggregate result = new CandidatureAggregate();
        Candidature candidature = candidatureRepository.findOne(id);
        result.setCandidature(candidature);

        LongFilter candidatId = new LongFilter();
        candidatId.setEquals(id);

        appendProject(result, candidatId);
        appendDocuments(result, candidatId);
        appendEntretien(result, candidatId);
        appendVisite(result, candidatId);

        return result;
    }

    private void appendProject(CandidatureAggregate result, LongFilter candidatId) {
        ProjetCriteria criteria = new ProjetCriteria();
        criteria.setCandidatureId(candidatId );
        List<Projet> list = projetQueryService.findByCriteria(criteria );
        result.setProjet(list.get(0));
    }
    private void appendDocuments(CandidatureAggregate result, LongFilter candidatId) {
        DocumentCriteria criteria = new DocumentCriteria();
        criteria.setCandidatureId(candidatId );
        List<Document> list = documentQueryService.findByCriteria(criteria );
        result.setDocuments(list.toArray(new Document[list.size()]));
    }
    private void appendEntretien(CandidatureAggregate result, LongFilter candidatId) {
        EntretienCriteria criteria = new EntretienCriteria();
        criteria.setCandidatureId(candidatId );
        List<Entretien> list = entretienQueryService.findByCriteria(criteria );
        result.setEntretiens(list.toArray(new Entretien[list.size()]));
    }
    private void appendVisite(CandidatureAggregate result, LongFilter candidatId) {
        VisiteCriteria criteria = new VisiteCriteria();
        criteria.setCandidatureId(candidatId );
        List<Visite> list = visiteQueryService.findByCriteria(criteria );
        result.setVisites(list.toArray(new Visite[list.size()]));
    }

}
