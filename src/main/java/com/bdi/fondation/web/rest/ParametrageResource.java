package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bdi.fondation.domain.Parametrage;
import com.bdi.fondation.service.ParametrageService;
import com.bdi.fondation.web.rest.errors.BadRequestAlertException;
import com.bdi.fondation.web.rest.util.HeaderUtil;
import com.bdi.fondation.service.dto.ParametrageCriteria;
import com.bdi.fondation.service.ParametrageQueryService;
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
 * REST controller for managing Parametrage.
 */
@RestController
@RequestMapping("/api")
public class ParametrageResource {

    private final Logger log = LoggerFactory.getLogger(ParametrageResource.class);

    private static final String ENTITY_NAME = "parametrage";

    private final ParametrageService parametrageService;

    private final ParametrageQueryService parametrageQueryService;

    public ParametrageResource(ParametrageService parametrageService, ParametrageQueryService parametrageQueryService) {
        this.parametrageService = parametrageService;
        this.parametrageQueryService = parametrageQueryService;
    }

    /**
     * POST  /parametrages : Create a new parametrage.
     *
     * @param parametrage the parametrage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parametrage, or with status 400 (Bad Request) if the parametrage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parametrages")
    @Timed
    public ResponseEntity<Parametrage> createParametrage(@RequestBody Parametrage parametrage) throws URISyntaxException {
        log.debug("REST request to save Parametrage : {}", parametrage);
        if (parametrage.getId() != null) {
            throw new BadRequestAlertException("A new parametrage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parametrage result = parametrageService.save(parametrage);
        return ResponseEntity.created(new URI("/api/parametrages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parametrages : Updates an existing parametrage.
     *
     * @param parametrage the parametrage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parametrage,
     * or with status 400 (Bad Request) if the parametrage is not valid,
     * or with status 500 (Internal Server Error) if the parametrage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parametrages")
    @Timed
    public ResponseEntity<Parametrage> updateParametrage(@RequestBody Parametrage parametrage) throws URISyntaxException {
        log.debug("REST request to update Parametrage : {}", parametrage);
        if (parametrage.getId() == null) {
            return createParametrage(parametrage);
        }
        Parametrage result = parametrageService.save(parametrage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parametrage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parametrages : get all the parametrages.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of parametrages in body
     */
    @GetMapping("/parametrages")
    @Timed
    public ResponseEntity<List<Parametrage>> getAllParametrages(ParametrageCriteria criteria) {
        log.debug("REST request to get Parametrages by criteria: {}", criteria);
        List<Parametrage> entityList = parametrageQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /parametrages/:id : get the "id" parametrage.
     *
     * @param id the id of the parametrage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parametrage, or with status 404 (Not Found)
     */
    @GetMapping("/parametrages/{id}")
    @Timed
    public ResponseEntity<Parametrage> getParametrage(@PathVariable Long id) {
        log.debug("REST request to get Parametrage : {}", id);
        Parametrage parametrage = parametrageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parametrage));
    }

    /**
     * DELETE  /parametrages/:id : delete the "id" parametrage.
     *
     * @param id the id of the parametrage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parametrages/{id}")
    @Timed
    public ResponseEntity<Void> deleteParametrage(@PathVariable Long id) {
        log.debug("REST request to delete Parametrage : {}", id);
        parametrageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
