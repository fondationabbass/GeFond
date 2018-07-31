package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bdi.fondation.domain.Garantie;
import com.bdi.fondation.service.GarantieService;
import com.bdi.fondation.web.rest.errors.BadRequestAlertException;
import com.bdi.fondation.web.rest.util.HeaderUtil;
import com.bdi.fondation.service.dto.GarantieCriteria;
import com.bdi.fondation.service.GarantieQueryService;
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
 * REST controller for managing Garantie.
 */
@RestController
@RequestMapping("/api")
public class GarantieResource {

    private final Logger log = LoggerFactory.getLogger(GarantieResource.class);

    private static final String ENTITY_NAME = "garantie";

    private final GarantieService garantieService;

    private final GarantieQueryService garantieQueryService;

    public GarantieResource(GarantieService garantieService, GarantieQueryService garantieQueryService) {
        this.garantieService = garantieService;
        this.garantieQueryService = garantieQueryService;
    }

    /**
     * POST  /garanties : Create a new garantie.
     *
     * @param garantie the garantie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new garantie, or with status 400 (Bad Request) if the garantie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/garanties")
    @Timed
    public ResponseEntity<Garantie> createGarantie(@Valid @RequestBody Garantie garantie) throws URISyntaxException {
        log.debug("REST request to save Garantie : {}", garantie);
        if (garantie.getId() != null) {
            throw new BadRequestAlertException("A new garantie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Garantie result = garantieService.save(garantie);
        return ResponseEntity.created(new URI("/api/garanties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /garanties : Updates an existing garantie.
     *
     * @param garantie the garantie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated garantie,
     * or with status 400 (Bad Request) if the garantie is not valid,
     * or with status 500 (Internal Server Error) if the garantie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/garanties")
    @Timed
    public ResponseEntity<Garantie> updateGarantie(@Valid @RequestBody Garantie garantie) throws URISyntaxException {
        log.debug("REST request to update Garantie : {}", garantie);
        if (garantie.getId() == null) {
            return createGarantie(garantie);
        }
        Garantie result = garantieService.save(garantie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, garantie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /garanties : get all the garanties.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of garanties in body
     */
    @GetMapping("/garanties")
    @Timed
    public ResponseEntity<List<Garantie>> getAllGaranties(GarantieCriteria criteria) {
        log.debug("REST request to get Garanties by criteria: {}", criteria);
        List<Garantie> entityList = garantieQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /garanties/:id : get the "id" garantie.
     *
     * @param id the id of the garantie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the garantie, or with status 404 (Not Found)
     */
    @GetMapping("/garanties/{id}")
    @Timed
    public ResponseEntity<Garantie> getGarantie(@PathVariable Long id) {
        log.debug("REST request to get Garantie : {}", id);
        Garantie garantie = garantieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(garantie));
    }

    /**
     * DELETE  /garanties/:id : delete the "id" garantie.
     *
     * @param id the id of the garantie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/garanties/{id}")
    @Timed
    public ResponseEntity<Void> deleteGarantie(@PathVariable Long id) {
        log.debug("REST request to delete Garantie : {}", id);
        garantieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
