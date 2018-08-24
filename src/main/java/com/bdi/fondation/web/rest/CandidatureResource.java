package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bdi.fondation.domain.Candidature;
import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.service.CandidatureService;
import com.bdi.fondation.web.rest.errors.BadRequestAlertException;
import com.bdi.fondation.web.rest.util.HeaderUtil;
import com.bdi.fondation.web.rest.util.PaginationUtil;
import com.bdi.fondation.service.dto.CandidatureAggregate;
import com.bdi.fondation.service.dto.CandidatureCriteria;
import com.bdi.fondation.service.dto.PretAggregate;
import com.bdi.fondation.service.CandidatureQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Candidature.
 */
@RestController
@RequestMapping("/api")
public class CandidatureResource {

    private final Logger log = LoggerFactory.getLogger(CandidatureResource.class);

    private static final String ENTITY_NAME = "candidature";

    private final CandidatureService candidatureService;

    private final CandidatureQueryService candidatureQueryService;

    public CandidatureResource(CandidatureService candidatureService, CandidatureQueryService candidatureQueryService) {
        this.candidatureService = candidatureService;
        this.candidatureQueryService = candidatureQueryService;
    }

    /**
     * POST  /candidatures : Create a new candidature.
     *
     * @param candidature the candidature to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidature, or with status 400 (Bad Request) if the candidature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidatures")
    @Timed
    public ResponseEntity<Candidature> createCandidature(@Valid @RequestBody Candidature candidature) throws URISyntaxException {
        log.debug("REST request to save Candidature : {}", candidature);
        if (candidature.getId() != null) {
            throw new BadRequestAlertException("A new candidature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Candidature result = candidatureService.save(candidature);
        return ResponseEntity.created(new URI("/api/candidatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
    @PostMapping("/candidatures/aggregate")
    @Timed
    public ResponseEntity<Candidature> createFullCandidature(@Valid @RequestBody CandidatureAggregate aggregate) throws URISyntaxException {
        log.debug("REST request to save Full Candidature : {}", aggregate);
        
        Candidature result = candidatureService.save(aggregate);
        return ResponseEntity.created(new URI("/api/candidatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidatures : Updates an existing candidature.
     *
     * @param candidature the candidature to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidature,
     * or with status 400 (Bad Request) if the candidature is not valid,
     * or with status 500 (Internal Server Error) if the candidature couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidatures")
    @Timed
    public ResponseEntity<Candidature> updateCandidature(@Valid @RequestBody Candidature candidature) throws URISyntaxException {
        log.debug("REST request to update Candidature : {}", candidature);
        if (candidature.getId() == null) {
            return createCandidature(candidature);
        }
        Candidature result = candidatureService.save(candidature);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidature.getId().toString()))
            .body(result);
    }
    @PutMapping("/candidature/validate")
    @Timed
    public ResponseEntity<Candidature> validateCandidature(@Valid @RequestBody Candidature candidature) throws URISyntaxException {
    	log.debug("REST request to validate Candidature : {}", candidature);
    	if (candidature.getId() == null) {
    		return createCandidature(candidature);
    	}
    	Candidature result = candidatureService.validate(candidature);
    	return ResponseEntity.ok()
    			.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidature.getId().toString()))
    			.body(result);
    }

    /**
     * GET  /candidatures : get all the candidatures.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of candidatures in body
     */
    @GetMapping("/candidatures")
    @Timed
    public ResponseEntity<List<Candidature>> getAllCandidatures(CandidatureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Candidatures by criteria: {}", criteria);
        Page<Candidature> page = candidatureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/candidatures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /candidatures/:id : get the "id" candidature.
     *
     * @param id the id of the candidature to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidature, or with status 404 (Not Found)
     */
    @GetMapping("/candidatures/{id}")
    @Timed
    public ResponseEntity<Candidature> getCandidature(@PathVariable Long id) {
        log.debug("REST request to get Candidature : {}", id);
        Candidature candidature = candidatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidature));
    }

    /**
     * DELETE  /candidatures/:id : delete the "id" candidature.
     *
     * @param id the id of the candidature to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidatures/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidature(@PathVariable Long id) {
        log.debug("REST request to delete Candidature : {}", id);
        candidatureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
