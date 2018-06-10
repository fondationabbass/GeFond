package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bdi.fondation.domain.Entretien;

import com.bdi.fondation.repository.EntretienRepository;
import com.bdi.fondation.web.rest.errors.BadRequestAlertException;
import com.bdi.fondation.web.rest.util.HeaderUtil;
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
 * REST controller for managing Entretien.
 */
@RestController
@RequestMapping("/api")
public class EntretienResource {

    private final Logger log = LoggerFactory.getLogger(EntretienResource.class);

    private static final String ENTITY_NAME = "entretien";

    private final EntretienRepository entretienRepository;

    public EntretienResource(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    /**
     * POST  /entretiens : Create a new entretien.
     *
     * @param entretien the entretien to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entretien, or with status 400 (Bad Request) if the entretien has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entretiens")
    @Timed
    public ResponseEntity<Entretien> createEntretien(@Valid @RequestBody Entretien entretien) throws URISyntaxException {
        log.debug("REST request to save Entretien : {}", entretien);
        if (entretien.getId() != null) {
            throw new BadRequestAlertException("A new entretien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entretien result = entretienRepository.save(entretien);
        return ResponseEntity.created(new URI("/api/entretiens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entretiens : Updates an existing entretien.
     *
     * @param entretien the entretien to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entretien,
     * or with status 400 (Bad Request) if the entretien is not valid,
     * or with status 500 (Internal Server Error) if the entretien couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entretiens")
    @Timed
    public ResponseEntity<Entretien> updateEntretien(@Valid @RequestBody Entretien entretien) throws URISyntaxException {
        log.debug("REST request to update Entretien : {}", entretien);
        if (entretien.getId() == null) {
            return createEntretien(entretien);
        }
        Entretien result = entretienRepository.save(entretien);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entretien.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entretiens : get all the entretiens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entretiens in body
     */
    @GetMapping("/entretiens")
    @Timed
    public List<Entretien> getAllEntretiens() {
        log.debug("REST request to get all Entretiens");
        return entretienRepository.findAll();
        }

    /**
     * GET  /entretiens/:id : get the "id" entretien.
     *
     * @param id the id of the entretien to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entretien, or with status 404 (Not Found)
     */
    @GetMapping("/entretiens/{id}")
    @Timed
    public ResponseEntity<Entretien> getEntretien(@PathVariable Long id) {
        log.debug("REST request to get Entretien : {}", id);
        Entretien entretien = entretienRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entretien));
    }

    /**
     * DELETE  /entretiens/:id : delete the "id" entretien.
     *
     * @param id the id of the entretien to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entretiens/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntretien(@PathVariable Long id) {
        log.debug("REST request to delete Entretien : {}", id);
        entretienRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
