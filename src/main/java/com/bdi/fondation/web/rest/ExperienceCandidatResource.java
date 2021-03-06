package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bdi.fondation.domain.ExperienceCandidat;
import com.bdi.fondation.service.ExperienceCandidatService;
import com.bdi.fondation.web.rest.errors.BadRequestAlertException;
import com.bdi.fondation.web.rest.util.HeaderUtil;
import com.bdi.fondation.service.dto.ExperienceCandidatCriteria;
import com.bdi.fondation.service.ExperienceCandidatQueryService;
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
 * REST controller for managing ExperienceCandidat.
 */
@RestController
@RequestMapping("/api")
public class ExperienceCandidatResource {

    private final Logger log = LoggerFactory.getLogger(ExperienceCandidatResource.class);

    private static final String ENTITY_NAME = "experienceCandidat";

    private final ExperienceCandidatService experienceCandidatService;

    private final ExperienceCandidatQueryService experienceCandidatQueryService;

    public ExperienceCandidatResource(ExperienceCandidatService experienceCandidatService, ExperienceCandidatQueryService experienceCandidatQueryService) {
        this.experienceCandidatService = experienceCandidatService;
        this.experienceCandidatQueryService = experienceCandidatQueryService;
    }

    /**
     * POST  /experience-candidats : Create a new experienceCandidat.
     *
     * @param experienceCandidat the experienceCandidat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new experienceCandidat, or with status 400 (Bad Request) if the experienceCandidat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experience-candidats")
    @Timed
    public ResponseEntity<ExperienceCandidat> createExperienceCandidat(@Valid @RequestBody ExperienceCandidat experienceCandidat) throws URISyntaxException {
        log.debug("REST request to save ExperienceCandidat : {}", experienceCandidat);
        if (experienceCandidat.getId() != null) {
            throw new BadRequestAlertException("A new experienceCandidat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExperienceCandidat result = experienceCandidatService.save(experienceCandidat);
        return ResponseEntity.created(new URI("/api/experience-candidats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experience-candidats : Updates an existing experienceCandidat.
     *
     * @param experienceCandidat the experienceCandidat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated experienceCandidat,
     * or with status 400 (Bad Request) if the experienceCandidat is not valid,
     * or with status 500 (Internal Server Error) if the experienceCandidat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experience-candidats")
    @Timed
    public ResponseEntity<ExperienceCandidat> updateExperienceCandidat(@Valid @RequestBody ExperienceCandidat experienceCandidat) throws URISyntaxException {
        log.debug("REST request to update ExperienceCandidat : {}", experienceCandidat);
        if (experienceCandidat.getId() == null) {
            return createExperienceCandidat(experienceCandidat);
        }
        ExperienceCandidat result = experienceCandidatService.save(experienceCandidat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, experienceCandidat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experience-candidats : get all the experienceCandidats.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of experienceCandidats in body
     */
    @GetMapping("/experience-candidats")
    @Timed
    public ResponseEntity<List<ExperienceCandidat>> getAllExperienceCandidats(ExperienceCandidatCriteria criteria) {
        log.debug("REST request to get ExperienceCandidats by criteria: {}", criteria);
        List<ExperienceCandidat> entityList = experienceCandidatQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /experience-candidats/:id : get the "id" experienceCandidat.
     *
     * @param id the id of the experienceCandidat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the experienceCandidat, or with status 404 (Not Found)
     */
    @GetMapping("/experience-candidats/{id}")
    @Timed
    public ResponseEntity<ExperienceCandidat> getExperienceCandidat(@PathVariable Long id) {
        log.debug("REST request to get ExperienceCandidat : {}", id);
        ExperienceCandidat experienceCandidat = experienceCandidatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(experienceCandidat));
    }

    /**
     * DELETE  /experience-candidats/:id : delete the "id" experienceCandidat.
     *
     * @param id the id of the experienceCandidat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experience-candidats/{id}")
    @Timed
    public ResponseEntity<Void> deleteExperienceCandidat(@PathVariable Long id) {
        log.debug("REST request to delete ExperienceCandidat : {}", id);
        experienceCandidatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
