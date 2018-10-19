package com.bdi.fondation.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bdi.fondation.domain.SessionProjet;
import com.bdi.fondation.service.SessionProjetQueryService;
import com.bdi.fondation.service.SessionProjetService;
import com.bdi.fondation.service.dto.SessionProjetCriteria;
import com.bdi.fondation.web.rest.errors.BadRequestAlertException;
import com.bdi.fondation.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing SessionProjet.
 */
@RestController
@RequestMapping("/api")
public class SessionProjetResource {

    private final Logger log = LoggerFactory.getLogger(SessionProjetResource.class);

    private static final String ENTITY_NAME = "sessionProjet";

    private final SessionProjetService sessionProjetService;

    private final SessionProjetQueryService sessionProjetQueryService;

    public SessionProjetResource(SessionProjetService sessionProjetService, SessionProjetQueryService sessionProjetQueryService) {
        this.sessionProjetService = sessionProjetService;
        this.sessionProjetQueryService = sessionProjetQueryService;
    }

    /**
     * POST  /session-projets : Create a new sessionProjet.
     *
     * @param sessionProjet the sessionProjet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sessionProjet, or with status 400 (Bad Request) if the sessionProjet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/session-projets")
    @Timed
    public ResponseEntity<SessionProjet> createSessionProjet(@Valid @RequestBody SessionProjet sessionProjet) throws URISyntaxException {
        log.debug("REST request to save SessionProjet : {}", sessionProjet);
        if (sessionProjet.getId() != null) {
            throw new BadRequestAlertException("A new sessionProjet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SessionProjet result = sessionProjetService.save(sessionProjet);
        return ResponseEntity.created(new URI("/api/session-projets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /session-projets : Updates an existing sessionProjet.
     *
     * @param sessionProjet the sessionProjet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sessionProjet,
     * or with status 400 (Bad Request) if the sessionProjet is not valid,
     * or with status 500 (Internal Server Error) if the sessionProjet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/session-projets")
    @Timed
    public ResponseEntity<SessionProjet> updateSessionProjet(@Valid @RequestBody SessionProjet sessionProjet) throws URISyntaxException {
        log.debug("REST request to update SessionProjet : {}", sessionProjet);
        if (sessionProjet.getId() == null) {
            return createSessionProjet(sessionProjet);
        }
        SessionProjet result = sessionProjetService.save(sessionProjet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sessionProjet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /session-projets : get all the sessionProjets.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of sessionProjets in body
     */
    @GetMapping("/session-projets")
    @Timed
    public ResponseEntity<List<SessionProjet>> getAllSessionProjets(SessionProjetCriteria criteria) {
        log.debug("REST request to get SessionProjets by criteria: {}", criteria);
        List<SessionProjet> entityList = sessionProjetQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /session-projets/:id : get the "id" sessionProjet.
     *
     * @param id the id of the sessionProjet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sessionProjet, or with status 404 (Not Found)
     */
    @GetMapping("/session-projets/{id}")
    @Timed
    public ResponseEntity<SessionProjet> getSessionProjet(@PathVariable Long id) {
        log.debug("REST request to get SessionProjet : {}", id);
        SessionProjet sessionProjet = sessionProjetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sessionProjet));
    }

    /**
     * DELETE  /session-projets/:id : delete the "id" sessionProjet.
     *
     * @param id the id of the sessionProjet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/session-projets/{id}")
    @Timed
    public ResponseEntity<Void> deleteSessionProjet(@PathVariable Long id) {
        log.debug("REST request to delete SessionProjet : {}", id);
        sessionProjetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
