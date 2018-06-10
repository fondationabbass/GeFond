package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bdi.fondation.domain.ElementFinancement;

import com.bdi.fondation.repository.ElementFinancementRepository;
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
 * REST controller for managing ElementFinancement.
 */
@RestController
@RequestMapping("/api")
public class ElementFinancementResource {

    private final Logger log = LoggerFactory.getLogger(ElementFinancementResource.class);

    private static final String ENTITY_NAME = "elementFinancement";

    private final ElementFinancementRepository elementFinancementRepository;

    public ElementFinancementResource(ElementFinancementRepository elementFinancementRepository) {
        this.elementFinancementRepository = elementFinancementRepository;
    }

    /**
     * POST  /element-financements : Create a new elementFinancement.
     *
     * @param elementFinancement the elementFinancement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new elementFinancement, or with status 400 (Bad Request) if the elementFinancement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/element-financements")
    @Timed
    public ResponseEntity<ElementFinancement> createElementFinancement(@Valid @RequestBody ElementFinancement elementFinancement) throws URISyntaxException {
        log.debug("REST request to save ElementFinancement : {}", elementFinancement);
        if (elementFinancement.getId() != null) {
            throw new BadRequestAlertException("A new elementFinancement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElementFinancement result = elementFinancementRepository.save(elementFinancement);
        return ResponseEntity.created(new URI("/api/element-financements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /element-financements : Updates an existing elementFinancement.
     *
     * @param elementFinancement the elementFinancement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated elementFinancement,
     * or with status 400 (Bad Request) if the elementFinancement is not valid,
     * or with status 500 (Internal Server Error) if the elementFinancement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/element-financements")
    @Timed
    public ResponseEntity<ElementFinancement> updateElementFinancement(@Valid @RequestBody ElementFinancement elementFinancement) throws URISyntaxException {
        log.debug("REST request to update ElementFinancement : {}", elementFinancement);
        if (elementFinancement.getId() == null) {
            return createElementFinancement(elementFinancement);
        }
        ElementFinancement result = elementFinancementRepository.save(elementFinancement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, elementFinancement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /element-financements : get all the elementFinancements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of elementFinancements in body
     */
    @GetMapping("/element-financements")
    @Timed
    public List<ElementFinancement> getAllElementFinancements() {
        log.debug("REST request to get all ElementFinancements");
        return elementFinancementRepository.findAll();
        }

    /**
     * GET  /element-financements/:id : get the "id" elementFinancement.
     *
     * @param id the id of the elementFinancement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the elementFinancement, or with status 404 (Not Found)
     */
    @GetMapping("/element-financements/{id}")
    @Timed
    public ResponseEntity<ElementFinancement> getElementFinancement(@PathVariable Long id) {
        log.debug("REST request to get ElementFinancement : {}", id);
        ElementFinancement elementFinancement = elementFinancementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(elementFinancement));
    }

    /**
     * DELETE  /element-financements/:id : delete the "id" elementFinancement.
     *
     * @param id the id of the elementFinancement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/element-financements/{id}")
    @Timed
    public ResponseEntity<Void> deleteElementFinancement(@PathVariable Long id) {
        log.debug("REST request to delete ElementFinancement : {}", id);
        elementFinancementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
