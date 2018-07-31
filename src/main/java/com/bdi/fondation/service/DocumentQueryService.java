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

import com.bdi.fondation.domain.Document;
import com.bdi.fondation.domain.*; // for static metamodels
import com.bdi.fondation.repository.DocumentRepository;
import com.bdi.fondation.service.dto.DocumentCriteria;


/**
 * Service for executing complex queries for Document entities in the database.
 * The main input is a {@link DocumentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Document} or a {@link Page} of {@link Document} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentQueryService extends QueryService<Document> {

    private final Logger log = LoggerFactory.getLogger(DocumentQueryService.class);


    private final DocumentRepository documentRepository;

    public DocumentQueryService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    /**
     * Return a {@link List} of {@link Document} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Document> findByCriteria(DocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Document> specification = createSpecification(criteria);
        return documentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Document} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Document> findByCriteria(DocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Document> specification = createSpecification(criteria);
        return documentRepository.findAll(specification, page);
    }

    /**
     * Function to convert DocumentCriteria to a {@link Specifications}
     */
    private Specifications<Document> createSpecification(DocumentCriteria criteria) {
        Specifications<Document> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Document_.id));
            }
            if (criteria.getDateEnreg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateEnreg(), Document_.dateEnreg));
            }
            if (criteria.getLib() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLib(), Document_.lib));
            }
            if (criteria.getTypeDocument() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeDocument(), Document_.typeDocument));
            }
            if (criteria.getModule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModule(), Document_.module));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtat(), Document_.etat));
            }
            if (criteria.getFichier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFichier(), Document_.fichier));
            }
            if (criteria.getTail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTail(), Document_.tail));
            }
            if (criteria.getCandidatureId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCandidatureId(), Document_.candidature, Candidature_.id));
            }
        }
        return specification;
    }

}
