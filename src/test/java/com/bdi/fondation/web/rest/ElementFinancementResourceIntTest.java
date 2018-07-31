package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.ElementFinancement;
import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.repository.ElementFinancementRepository;
import com.bdi.fondation.service.ElementFinancementService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.ElementFinancementCriteria;
import com.bdi.fondation.service.ElementFinancementQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.bdi.fondation.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ElementFinancementResource REST controller.
 *
 * @see ElementFinancementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class ElementFinancementResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final LocalDate DEFAULT_DATE_FINANCEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FINANCEMENT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ElementFinancementRepository elementFinancementRepository;

    @Autowired
    private ElementFinancementService elementFinancementService;

    @Autowired
    private ElementFinancementQueryService elementFinancementQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restElementFinancementMockMvc;

    private ElementFinancement elementFinancement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ElementFinancementResource elementFinancementResource = new ElementFinancementResource(elementFinancementService, elementFinancementQueryService);
        this.restElementFinancementMockMvc = MockMvcBuilders.standaloneSetup(elementFinancementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementFinancement createEntity(EntityManager em) {
        ElementFinancement elementFinancement = new ElementFinancement()
            .type(DEFAULT_TYPE)
            .montant(DEFAULT_MONTANT)
            .dateFinancement(DEFAULT_DATE_FINANCEMENT);
        return elementFinancement;
    }

    @Before
    public void initTest() {
        elementFinancement = createEntity(em);
    }

    @Test
    @Transactional
    public void createElementFinancement() throws Exception {
        int databaseSizeBeforeCreate = elementFinancementRepository.findAll().size();

        // Create the ElementFinancement
        restElementFinancementMockMvc.perform(post("/api/element-financements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementFinancement)))
            .andExpect(status().isCreated());

        // Validate the ElementFinancement in the database
        List<ElementFinancement> elementFinancementList = elementFinancementRepository.findAll();
        assertThat(elementFinancementList).hasSize(databaseSizeBeforeCreate + 1);
        ElementFinancement testElementFinancement = elementFinancementList.get(elementFinancementList.size() - 1);
        assertThat(testElementFinancement.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testElementFinancement.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testElementFinancement.getDateFinancement()).isEqualTo(DEFAULT_DATE_FINANCEMENT);
    }

    @Test
    @Transactional
    public void createElementFinancementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elementFinancementRepository.findAll().size();

        // Create the ElementFinancement with an existing ID
        elementFinancement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementFinancementMockMvc.perform(post("/api/element-financements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementFinancement)))
            .andExpect(status().isBadRequest());

        // Validate the ElementFinancement in the database
        List<ElementFinancement> elementFinancementList = elementFinancementRepository.findAll();
        assertThat(elementFinancementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementFinancementRepository.findAll().size();
        // set the field null
        elementFinancement.setType(null);

        // Create the ElementFinancement, which fails.

        restElementFinancementMockMvc.perform(post("/api/element-financements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementFinancement)))
            .andExpect(status().isBadRequest());

        List<ElementFinancement> elementFinancementList = elementFinancementRepository.findAll();
        assertThat(elementFinancementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementFinancementRepository.findAll().size();
        // set the field null
        elementFinancement.setMontant(null);

        // Create the ElementFinancement, which fails.

        restElementFinancementMockMvc.perform(post("/api/element-financements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementFinancement)))
            .andExpect(status().isBadRequest());

        List<ElementFinancement> elementFinancementList = elementFinancementRepository.findAll();
        assertThat(elementFinancementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllElementFinancements() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList
        restElementFinancementMockMvc.perform(get("/api/element-financements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementFinancement.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateFinancement").value(hasItem(DEFAULT_DATE_FINANCEMENT.toString())));
    }

    @Test
    @Transactional
    public void getElementFinancement() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get the elementFinancement
        restElementFinancementMockMvc.perform(get("/api/element-financements/{id}", elementFinancement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(elementFinancement.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.dateFinancement").value(DEFAULT_DATE_FINANCEMENT.toString()));
    }

    @Test
    @Transactional
    public void getAllElementFinancementsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList where type equals to DEFAULT_TYPE
        defaultElementFinancementShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the elementFinancementList where type equals to UPDATED_TYPE
        defaultElementFinancementShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllElementFinancementsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultElementFinancementShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the elementFinancementList where type equals to UPDATED_TYPE
        defaultElementFinancementShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllElementFinancementsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList where type is not null
        defaultElementFinancementShouldBeFound("type.specified=true");

        // Get all the elementFinancementList where type is null
        defaultElementFinancementShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllElementFinancementsByMontantIsEqualToSomething() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList where montant equals to DEFAULT_MONTANT
        defaultElementFinancementShouldBeFound("montant.equals=" + DEFAULT_MONTANT);

        // Get all the elementFinancementList where montant equals to UPDATED_MONTANT
        defaultElementFinancementShouldNotBeFound("montant.equals=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void getAllElementFinancementsByMontantIsInShouldWork() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList where montant in DEFAULT_MONTANT or UPDATED_MONTANT
        defaultElementFinancementShouldBeFound("montant.in=" + DEFAULT_MONTANT + "," + UPDATED_MONTANT);

        // Get all the elementFinancementList where montant equals to UPDATED_MONTANT
        defaultElementFinancementShouldNotBeFound("montant.in=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void getAllElementFinancementsByMontantIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList where montant is not null
        defaultElementFinancementShouldBeFound("montant.specified=true");

        // Get all the elementFinancementList where montant is null
        defaultElementFinancementShouldNotBeFound("montant.specified=false");
    }

    @Test
    @Transactional
    public void getAllElementFinancementsByDateFinancementIsEqualToSomething() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList where dateFinancement equals to DEFAULT_DATE_FINANCEMENT
        defaultElementFinancementShouldBeFound("dateFinancement.equals=" + DEFAULT_DATE_FINANCEMENT);

        // Get all the elementFinancementList where dateFinancement equals to UPDATED_DATE_FINANCEMENT
        defaultElementFinancementShouldNotBeFound("dateFinancement.equals=" + UPDATED_DATE_FINANCEMENT);
    }

    @Test
    @Transactional
    public void getAllElementFinancementsByDateFinancementIsInShouldWork() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList where dateFinancement in DEFAULT_DATE_FINANCEMENT or UPDATED_DATE_FINANCEMENT
        defaultElementFinancementShouldBeFound("dateFinancement.in=" + DEFAULT_DATE_FINANCEMENT + "," + UPDATED_DATE_FINANCEMENT);

        // Get all the elementFinancementList where dateFinancement equals to UPDATED_DATE_FINANCEMENT
        defaultElementFinancementShouldNotBeFound("dateFinancement.in=" + UPDATED_DATE_FINANCEMENT);
    }

    @Test
    @Transactional
    public void getAllElementFinancementsByDateFinancementIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList where dateFinancement is not null
        defaultElementFinancementShouldBeFound("dateFinancement.specified=true");

        // Get all the elementFinancementList where dateFinancement is null
        defaultElementFinancementShouldNotBeFound("dateFinancement.specified=false");
    }

    @Test
    @Transactional
    public void getAllElementFinancementsByDateFinancementIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList where dateFinancement greater than or equals to DEFAULT_DATE_FINANCEMENT
        defaultElementFinancementShouldBeFound("dateFinancement.greaterOrEqualThan=" + DEFAULT_DATE_FINANCEMENT);

        // Get all the elementFinancementList where dateFinancement greater than or equals to UPDATED_DATE_FINANCEMENT
        defaultElementFinancementShouldNotBeFound("dateFinancement.greaterOrEqualThan=" + UPDATED_DATE_FINANCEMENT);
    }

    @Test
    @Transactional
    public void getAllElementFinancementsByDateFinancementIsLessThanSomething() throws Exception {
        // Initialize the database
        elementFinancementRepository.saveAndFlush(elementFinancement);

        // Get all the elementFinancementList where dateFinancement less than or equals to DEFAULT_DATE_FINANCEMENT
        defaultElementFinancementShouldNotBeFound("dateFinancement.lessThan=" + DEFAULT_DATE_FINANCEMENT);

        // Get all the elementFinancementList where dateFinancement less than or equals to UPDATED_DATE_FINANCEMENT
        defaultElementFinancementShouldBeFound("dateFinancement.lessThan=" + UPDATED_DATE_FINANCEMENT);
    }


    @Test
    @Transactional
    public void getAllElementFinancementsByPretIsEqualToSomething() throws Exception {
        // Initialize the database
        Pret pret = PretResourceIntTest.createEntity(em);
        em.persist(pret);
        em.flush();
        elementFinancement.setPret(pret);
        elementFinancementRepository.saveAndFlush(elementFinancement);
        Long pretId = pret.getId();

        // Get all the elementFinancementList where pret equals to pretId
        defaultElementFinancementShouldBeFound("pretId.equals=" + pretId);

        // Get all the elementFinancementList where pret equals to pretId + 1
        defaultElementFinancementShouldNotBeFound("pretId.equals=" + (pretId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultElementFinancementShouldBeFound(String filter) throws Exception {
        restElementFinancementMockMvc.perform(get("/api/element-financements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementFinancement.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateFinancement").value(hasItem(DEFAULT_DATE_FINANCEMENT.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultElementFinancementShouldNotBeFound(String filter) throws Exception {
        restElementFinancementMockMvc.perform(get("/api/element-financements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingElementFinancement() throws Exception {
        // Get the elementFinancement
        restElementFinancementMockMvc.perform(get("/api/element-financements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElementFinancement() throws Exception {
        // Initialize the database
        elementFinancementService.save(elementFinancement);

        int databaseSizeBeforeUpdate = elementFinancementRepository.findAll().size();

        // Update the elementFinancement
        ElementFinancement updatedElementFinancement = elementFinancementRepository.findOne(elementFinancement.getId());
        // Disconnect from session so that the updates on updatedElementFinancement are not directly saved in db
        em.detach(updatedElementFinancement);
        updatedElementFinancement
            .type(UPDATED_TYPE)
            .montant(UPDATED_MONTANT)
            .dateFinancement(UPDATED_DATE_FINANCEMENT);

        restElementFinancementMockMvc.perform(put("/api/element-financements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedElementFinancement)))
            .andExpect(status().isOk());

        // Validate the ElementFinancement in the database
        List<ElementFinancement> elementFinancementList = elementFinancementRepository.findAll();
        assertThat(elementFinancementList).hasSize(databaseSizeBeforeUpdate);
        ElementFinancement testElementFinancement = elementFinancementList.get(elementFinancementList.size() - 1);
        assertThat(testElementFinancement.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testElementFinancement.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testElementFinancement.getDateFinancement()).isEqualTo(UPDATED_DATE_FINANCEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingElementFinancement() throws Exception {
        int databaseSizeBeforeUpdate = elementFinancementRepository.findAll().size();

        // Create the ElementFinancement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restElementFinancementMockMvc.perform(put("/api/element-financements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementFinancement)))
            .andExpect(status().isCreated());

        // Validate the ElementFinancement in the database
        List<ElementFinancement> elementFinancementList = elementFinancementRepository.findAll();
        assertThat(elementFinancementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteElementFinancement() throws Exception {
        // Initialize the database
        elementFinancementService.save(elementFinancement);

        int databaseSizeBeforeDelete = elementFinancementRepository.findAll().size();

        // Get the elementFinancement
        restElementFinancementMockMvc.perform(delete("/api/element-financements/{id}", elementFinancement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ElementFinancement> elementFinancementList = elementFinancementRepository.findAll();
        assertThat(elementFinancementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementFinancement.class);
        ElementFinancement elementFinancement1 = new ElementFinancement();
        elementFinancement1.setId(1L);
        ElementFinancement elementFinancement2 = new ElementFinancement();
        elementFinancement2.setId(elementFinancement1.getId());
        assertThat(elementFinancement1).isEqualTo(elementFinancement2);
        elementFinancement2.setId(2L);
        assertThat(elementFinancement1).isNotEqualTo(elementFinancement2);
        elementFinancement1.setId(null);
        assertThat(elementFinancement1).isNotEqualTo(elementFinancement2);
    }
}
