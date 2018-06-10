package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.service.CandidatService;
import com.bdi.fondation.web.rest.errors.BadRequestAlertException;
import com.bdi.fondation.web.rest.util.HeaderUtil;
import com.bdi.fondation.service.dto.CandidatCriteria;
import com.bdi.fondation.service.CandidatQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Candidat.
 */
@RestController
@RequestMapping("/api")
public class CandidatResource {

    private final Logger log = LoggerFactory.getLogger(CandidatResource.class);

    private static final String ENTITY_NAME = "candidat";

    private final CandidatService candidatService;

    private final CandidatQueryService candidatQueryService;

    public CandidatResource(CandidatService candidatService, CandidatQueryService candidatQueryService) {
        this.candidatService = candidatService;
        this.candidatQueryService = candidatQueryService;
    }

    /**
     * POST  /candidats : Create a new candidat.
     *
     * @param candidat the candidat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidat, or with status 400 (Bad Request) if the candidat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidats")
    @Timed
    public ResponseEntity<Candidat> createCandidat(@Valid @RequestBody Candidat candidat) throws URISyntaxException {
        log.debug("REST request to save Candidat : {}", candidat);
        if (candidat.getId() != null) {
            throw new BadRequestAlertException("A new candidat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Candidat result = candidatService.save(candidat);
        return ResponseEntity.created(new URI("/api/candidats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidats : Updates an existing candidat.
     *
     * @param candidat the candidat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidat,
     * or with status 400 (Bad Request) if the candidat is not valid,
     * or with status 500 (Internal Server Error) if the candidat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidats")
    @Timed
    public ResponseEntity<Candidat> updateCandidat(@Valid @RequestBody Candidat candidat) throws URISyntaxException {
        log.debug("REST request to update Candidat : {}", candidat);
        if (candidat.getId() == null) {
            return createCandidat(candidat);
        }
        Candidat result = candidatService.save(candidat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidats : get all the candidats.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of candidats in body
     */
    @GetMapping("/candidats")
    @Timed
    public ResponseEntity<List<Candidat>> getAllCandidats(CandidatCriteria criteria) {
        log.debug("REST request to get Candidats by criteria: {}", criteria);
        List<Candidat> entityList = candidatQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }
    @GetMapping("/candidats/last")
    @Timed
    public ResponseEntity<List<Candidat>> getLast3Candidats() {
    	log.debug("REST request to get last 3 candidats");
    	List<Candidat> entityList = candidatQueryService.findLast3();
    	return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /candidats/:id : get the "id" candidat.
     *
     * @param id the id of the candidat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidat, or with status 404 (Not Found)
     */
    @GetMapping("/candidats/{id}")
    @Timed
    public ResponseEntity<Candidat> getCandidat(@PathVariable Long id) {
        log.debug("REST request to get Candidat : {}", id);
        Candidat candidat = candidatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidat));
    }

    /**
     * DELETE  /candidats/:id : delete the "id" candidat.
     *
     * @param id the id of the candidat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidats/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidat(@PathVariable Long id) {
        log.debug("REST request to delete Candidat : {}", id);
        candidatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
