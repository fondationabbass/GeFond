package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bdi.fondation.domain.Mouvement;
import com.bdi.fondation.service.MouvementService;
import com.bdi.fondation.web.rest.errors.BadRequestAlertException;
import com.bdi.fondation.web.rest.util.HeaderUtil;
import com.bdi.fondation.web.rest.util.PaginationUtil;
import com.bdi.fondation.service.dto.MouvementCriteria;
import com.bdi.fondation.service.MouvementQueryService;
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
 * REST controller for managing Mouvement.
 */
@RestController
@RequestMapping("/api")
public class MouvementResource {

    private final Logger log = LoggerFactory.getLogger(MouvementResource.class);

    private static final String ENTITY_NAME = "mouvement";

    private final MouvementService mouvementService;

    private final MouvementQueryService mouvementQueryService;

    public MouvementResource(MouvementService mouvementService, MouvementQueryService mouvementQueryService) {
        this.mouvementService = mouvementService;
        this.mouvementQueryService = mouvementQueryService;
    }

    /**
     * POST  /mouvements : Create a new mouvement.
     *
     * @param mouvement the mouvement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mouvement, or with status 400 (Bad Request) if the mouvement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mouvements")
    @Timed
    public ResponseEntity<Mouvement> createMouvement(@Valid @RequestBody Mouvement mouvement) throws URISyntaxException {
        log.debug("REST request to save Mouvement : {}", mouvement);
        if (mouvement.getId() != null) {
            throw new BadRequestAlertException("A new mouvement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mouvement result = mouvementService.save(mouvement);
        return ResponseEntity.created(new URI("/api/mouvements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mouvements : Updates an existing mouvement.
     *
     * @param mouvement the mouvement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mouvement,
     * or with status 400 (Bad Request) if the mouvement is not valid,
     * or with status 500 (Internal Server Error) if the mouvement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mouvements")
    @Timed
    public ResponseEntity<Mouvement> updateMouvement(@Valid @RequestBody Mouvement mouvement) throws URISyntaxException {
        log.debug("REST request to update Mouvement : {}", mouvement);
        if (mouvement.getId() == null) {
            return createMouvement(mouvement);
        }
        Mouvement result = mouvementService.save(mouvement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mouvement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mouvements : get all the mouvements.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mouvements in body
     */
    @GetMapping("/mouvements")
    @Timed
    public ResponseEntity<List<Mouvement>> getAllMouvements(MouvementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Mouvements by criteria: {}", criteria);
        Page<Mouvement> page = mouvementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mouvements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mouvements/:id : get the "id" mouvement.
     *
     * @param id the id of the mouvement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mouvement, or with status 404 (Not Found)
     */
    @GetMapping("/mouvements/{id}")
    @Timed
    public ResponseEntity<Mouvement> getMouvement(@PathVariable Long id) {
        log.debug("REST request to get Mouvement : {}", id);
        Mouvement mouvement = mouvementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mouvement));
    }

    /**
     * DELETE  /mouvements/:id : delete the "id" mouvement.
     *
     * @param id the id of the mouvement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mouvements/{id}")
    @Timed
    public ResponseEntity<Void> deleteMouvement(@PathVariable Long id) {
        log.debug("REST request to delete Mouvement : {}", id);
        mouvementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
