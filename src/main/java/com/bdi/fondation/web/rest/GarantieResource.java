package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bdi.fondation.domain.Garantie;

import com.bdi.fondation.repository.GarantieRepository;
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
 * REST controller for managing Garantie.
 */
@RestController
@RequestMapping("/api")
public class GarantieResource {

    private final Logger log = LoggerFactory.getLogger(GarantieResource.class);

    private static final String ENTITY_NAME = "garantie";

    private final GarantieRepository garantieRepository;

    public GarantieResource(GarantieRepository garantieRepository) {
        this.garantieRepository = garantieRepository;
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
    public ResponseEntity<Garantie> createGarantie(@RequestBody Garantie garantie) throws URISyntaxException {
        log.debug("REST request to save Garantie : {}", garantie);
        if (garantie.getId() != null) {
            throw new BadRequestAlertException("A new garantie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Garantie result = garantieRepository.save(garantie);
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
    public ResponseEntity<Garantie> updateGarantie(@RequestBody Garantie garantie) throws URISyntaxException {
        log.debug("REST request to update Garantie : {}", garantie);
        if (garantie.getId() == null) {
            return createGarantie(garantie);
        }
        Garantie result = garantieRepository.save(garantie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, garantie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /garanties : get all the garanties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of garanties in body
     */
    @GetMapping("/garanties")
    @Timed
    public List<Garantie> getAllGaranties() {
        log.debug("REST request to get all Garanties");
        return garantieRepository.findAll();
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
        Garantie garantie = garantieRepository.findOne(id);
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
        garantieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
