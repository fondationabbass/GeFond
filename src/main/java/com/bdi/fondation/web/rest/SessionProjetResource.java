package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bdi.fondation.domain.SessionProjet;

import com.bdi.fondation.repository.SessionProjetRepository;
import com.bdi.fondation.web.rest.errors.BadRequestAlertException;
import com.bdi.fondation.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SessionProjet.
 */
@RestController
@RequestMapping("/api")
public class SessionProjetResource {

    private final Logger log = LoggerFactory.getLogger(SessionProjetResource.class);

    private static final String ENTITY_NAME = "sessionProjet";

    private final SessionProjetRepository sessionProjetRepository;

    public SessionProjetResource(SessionProjetRepository sessionProjetRepository) {
        this.sessionProjetRepository = sessionProjetRepository;
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
    public ResponseEntity<SessionProjet> createSessionProjet(@RequestBody SessionProjet sessionProjet) throws URISyntaxException {
        log.debug("REST request to save SessionProjet : {}", sessionProjet);
        if (sessionProjet.getId() != null) {
            throw new BadRequestAlertException("A new sessionProjet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SessionProjet result = sessionProjetRepository.save(sessionProjet);
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
    public ResponseEntity<SessionProjet> updateSessionProjet(@RequestBody SessionProjet sessionProjet) throws URISyntaxException {
        log.debug("REST request to update SessionProjet : {}", sessionProjet);
        if (sessionProjet.getId() == null) {
            return createSessionProjet(sessionProjet);
        }
        SessionProjet result = sessionProjetRepository.save(sessionProjet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sessionProjet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /session-projets : get all the sessionProjets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sessionProjets in body
     */
    @GetMapping("/session-projets")
    @Timed
    public List<SessionProjet> getAllSessionProjets() {
        log.debug("REST request to get all SessionProjets");
        return sessionProjetRepository.findAll();
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
        SessionProjet sessionProjet = sessionProjetRepository.findOne(id);
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
        sessionProjetRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
