package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bdi.fondation.domain.Echeance;

import com.bdi.fondation.repository.EcheanceRepository;
import com.bdi.fondation.web.rest.errors.BadRequestAlertException;
import com.bdi.fondation.web.rest.util.HeaderUtil;
import com.bdi.fondation.web.rest.util.PaginationUtil;
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
 * REST controller for managing Echeance.
 */
@RestController
@RequestMapping("/api")
public class EcheanceResource {

    private final Logger log = LoggerFactory.getLogger(EcheanceResource.class);

    private static final String ENTITY_NAME = "echeance";

    private final EcheanceRepository echeanceRepository;

    public EcheanceResource(EcheanceRepository echeanceRepository) {
        this.echeanceRepository = echeanceRepository;
    }

    /**
     * POST  /echeances : Create a new echeance.
     *
     * @param echeance the echeance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new echeance, or with status 400 (Bad Request) if the echeance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/echeances")
    @Timed
    public ResponseEntity<Echeance> createEcheance(@Valid @RequestBody Echeance echeance) throws URISyntaxException {
        log.debug("REST request to save Echeance : {}", echeance);
        if (echeance.getId() != null) {
            throw new BadRequestAlertException("A new echeance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Echeance result = echeanceRepository.save(echeance);
        return ResponseEntity.created(new URI("/api/echeances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /echeances : Updates an existing echeance.
     *
     * @param echeance the echeance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated echeance,
     * or with status 400 (Bad Request) if the echeance is not valid,
     * or with status 500 (Internal Server Error) if the echeance couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/echeances")
    @Timed
    public ResponseEntity<Echeance> updateEcheance(@Valid @RequestBody Echeance echeance) throws URISyntaxException {
        log.debug("REST request to update Echeance : {}", echeance);
        if (echeance.getId() == null) {
            return createEcheance(echeance);
        }
        Echeance result = echeanceRepository.save(echeance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, echeance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /echeances : get all the echeances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of echeances in body
     */
    @GetMapping("/echeances")
    @Timed
    public ResponseEntity<List<Echeance>> getAllEcheances(Pageable pageable) {
        log.debug("REST request to get a page of Echeances");
        Page<Echeance> page = echeanceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/echeances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /echeances/:id : get the "id" echeance.
     *
     * @param id the id of the echeance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the echeance, or with status 404 (Not Found)
     */
    @GetMapping("/echeances/{id}")
    @Timed
    public ResponseEntity<Echeance> getEcheance(@PathVariable Long id) {
        log.debug("REST request to get Echeance : {}", id);
        Echeance echeance = echeanceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(echeance));
    }

    /**
     * DELETE  /echeances/:id : delete the "id" echeance.
     *
     * @param id the id of the echeance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/echeances/{id}")
    @Timed
    public ResponseEntity<Void> deleteEcheance(@PathVariable Long id) {
        log.debug("REST request to delete Echeance : {}", id);
        echeanceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
