package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Caisse;
import com.bdi.fondation.domain.User;
import com.bdi.fondation.repository.CaisseRepository;
import com.bdi.fondation.service.CaisseService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.CaisseCriteria;
import com.bdi.fondation.service.CaisseQueryService;

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
 * Test class for the CaisseResource REST controller.
 *
 * @see CaisseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class CaisseResourceIntTest {

    private static final String DEFAULT_INTITULE_CAISSE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_CAISSE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OUVERTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OUVERTURE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CaisseRepository caisseRepository;

    @Autowired
    private CaisseService caisseService;

    @Autowired
    private CaisseQueryService caisseQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCaisseMockMvc;

    private Caisse caisse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaisseResource caisseResource = new CaisseResource(caisseService, caisseQueryService);
        this.restCaisseMockMvc = MockMvcBuilders.standaloneSetup(caisseResource)
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
    public static Caisse createEntity(EntityManager em) {
        Caisse caisse = new Caisse()
            .intituleCaisse(DEFAULT_INTITULE_CAISSE)
            .dateOuverture(DEFAULT_DATE_OUVERTURE);
        return caisse;
    }

    @Before
    public void initTest() {
        caisse = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaisse() throws Exception {
        int databaseSizeBeforeCreate = caisseRepository.findAll().size();

        // Create the Caisse
        restCaisseMockMvc.perform(post("/api/caisses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caisse)))
            .andExpect(status().isCreated());

        // Validate the Caisse in the database
        List<Caisse> caisseList = caisseRepository.findAll();
        assertThat(caisseList).hasSize(databaseSizeBeforeCreate + 1);
        Caisse testCaisse = caisseList.get(caisseList.size() - 1);
        assertThat(testCaisse.getIntituleCaisse()).isEqualTo(DEFAULT_INTITULE_CAISSE);
        assertThat(testCaisse.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
    }

    @Test
    @Transactional
    public void createCaisseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caisseRepository.findAll().size();

        // Create the Caisse with an existing ID
        caisse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaisseMockMvc.perform(post("/api/caisses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caisse)))
            .andExpect(status().isBadRequest());

        // Validate the Caisse in the database
        List<Caisse> caisseList = caisseRepository.findAll();
        assertThat(caisseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCaisses() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get all the caisseList
        restCaisseMockMvc.perform(get("/api/caisses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caisse.getId().intValue())))
            .andExpect(jsonPath("$.[*].intituleCaisse").value(hasItem(DEFAULT_INTITULE_CAISSE.toString())))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())));
    }

    @Test
    @Transactional
    public void getCaisse() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get the caisse
        restCaisseMockMvc.perform(get("/api/caisses/{id}", caisse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caisse.getId().intValue()))
            .andExpect(jsonPath("$.intituleCaisse").value(DEFAULT_INTITULE_CAISSE.toString()))
            .andExpect(jsonPath("$.dateOuverture").value(DEFAULT_DATE_OUVERTURE.toString()));
    }

    @Test
    @Transactional
    public void getAllCaissesByIntituleCaisseIsEqualToSomething() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get all the caisseList where intituleCaisse equals to DEFAULT_INTITULE_CAISSE
        defaultCaisseShouldBeFound("intituleCaisse.equals=" + DEFAULT_INTITULE_CAISSE);

        // Get all the caisseList where intituleCaisse equals to UPDATED_INTITULE_CAISSE
        defaultCaisseShouldNotBeFound("intituleCaisse.equals=" + UPDATED_INTITULE_CAISSE);
    }

    @Test
    @Transactional
    public void getAllCaissesByIntituleCaisseIsInShouldWork() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get all the caisseList where intituleCaisse in DEFAULT_INTITULE_CAISSE or UPDATED_INTITULE_CAISSE
        defaultCaisseShouldBeFound("intituleCaisse.in=" + DEFAULT_INTITULE_CAISSE + "," + UPDATED_INTITULE_CAISSE);

        // Get all the caisseList where intituleCaisse equals to UPDATED_INTITULE_CAISSE
        defaultCaisseShouldNotBeFound("intituleCaisse.in=" + UPDATED_INTITULE_CAISSE);
    }

    @Test
    @Transactional
    public void getAllCaissesByIntituleCaisseIsNullOrNotNull() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get all the caisseList where intituleCaisse is not null
        defaultCaisseShouldBeFound("intituleCaisse.specified=true");

        // Get all the caisseList where intituleCaisse is null
        defaultCaisseShouldNotBeFound("intituleCaisse.specified=false");
    }

    @Test
    @Transactional
    public void getAllCaissesByDateOuvertureIsEqualToSomething() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get all the caisseList where dateOuverture equals to DEFAULT_DATE_OUVERTURE
        defaultCaisseShouldBeFound("dateOuverture.equals=" + DEFAULT_DATE_OUVERTURE);

        // Get all the caisseList where dateOuverture equals to UPDATED_DATE_OUVERTURE
        defaultCaisseShouldNotBeFound("dateOuverture.equals=" + UPDATED_DATE_OUVERTURE);
    }

    @Test
    @Transactional
    public void getAllCaissesByDateOuvertureIsInShouldWork() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get all the caisseList where dateOuverture in DEFAULT_DATE_OUVERTURE or UPDATED_DATE_OUVERTURE
        defaultCaisseShouldBeFound("dateOuverture.in=" + DEFAULT_DATE_OUVERTURE + "," + UPDATED_DATE_OUVERTURE);

        // Get all the caisseList where dateOuverture equals to UPDATED_DATE_OUVERTURE
        defaultCaisseShouldNotBeFound("dateOuverture.in=" + UPDATED_DATE_OUVERTURE);
    }

    @Test
    @Transactional
    public void getAllCaissesByDateOuvertureIsNullOrNotNull() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get all the caisseList where dateOuverture is not null
        defaultCaisseShouldBeFound("dateOuverture.specified=true");

        // Get all the caisseList where dateOuverture is null
        defaultCaisseShouldNotBeFound("dateOuverture.specified=false");
    }

    @Test
    @Transactional
    public void getAllCaissesByDateOuvertureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get all the caisseList where dateOuverture greater than or equals to DEFAULT_DATE_OUVERTURE
        defaultCaisseShouldBeFound("dateOuverture.greaterOrEqualThan=" + DEFAULT_DATE_OUVERTURE);

        // Get all the caisseList where dateOuverture greater than or equals to UPDATED_DATE_OUVERTURE
        defaultCaisseShouldNotBeFound("dateOuverture.greaterOrEqualThan=" + UPDATED_DATE_OUVERTURE);
    }

    @Test
    @Transactional
    public void getAllCaissesByDateOuvertureIsLessThanSomething() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get all the caisseList where dateOuverture less than or equals to DEFAULT_DATE_OUVERTURE
        defaultCaisseShouldNotBeFound("dateOuverture.lessThan=" + DEFAULT_DATE_OUVERTURE);

        // Get all the caisseList where dateOuverture less than or equals to UPDATED_DATE_OUVERTURE
        defaultCaisseShouldBeFound("dateOuverture.lessThan=" + UPDATED_DATE_OUVERTURE);
    }


    @Test
    @Transactional
    public void getAllCaissesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        caisse.setUser(user);
        caisseRepository.saveAndFlush(caisse);
        Long userId = user.getId();

        // Get all the caisseList where user equals to userId
        defaultCaisseShouldBeFound("userId.equals=" + userId);

        // Get all the caisseList where user equals to userId + 1
        defaultCaisseShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCaisseShouldBeFound(String filter) throws Exception {
        restCaisseMockMvc.perform(get("/api/caisses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caisse.getId().intValue())))
            .andExpect(jsonPath("$.[*].intituleCaisse").value(hasItem(DEFAULT_INTITULE_CAISSE.toString())))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCaisseShouldNotBeFound(String filter) throws Exception {
        restCaisseMockMvc.perform(get("/api/caisses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCaisse() throws Exception {
        // Get the caisse
        restCaisseMockMvc.perform(get("/api/caisses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaisse() throws Exception {
        // Initialize the database
        caisseService.save(caisse);

        int databaseSizeBeforeUpdate = caisseRepository.findAll().size();

        // Update the caisse
        Caisse updatedCaisse = caisseRepository.findOne(caisse.getId());
        // Disconnect from session so that the updates on updatedCaisse are not directly saved in db
        em.detach(updatedCaisse);
        updatedCaisse
            .intituleCaisse(UPDATED_INTITULE_CAISSE)
            .dateOuverture(UPDATED_DATE_OUVERTURE);

        restCaisseMockMvc.perform(put("/api/caisses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCaisse)))
            .andExpect(status().isOk());

        // Validate the Caisse in the database
        List<Caisse> caisseList = caisseRepository.findAll();
        assertThat(caisseList).hasSize(databaseSizeBeforeUpdate);
        Caisse testCaisse = caisseList.get(caisseList.size() - 1);
        assertThat(testCaisse.getIntituleCaisse()).isEqualTo(UPDATED_INTITULE_CAISSE);
        assertThat(testCaisse.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
    }

    @Test
    @Transactional
    public void updateNonExistingCaisse() throws Exception {
        int databaseSizeBeforeUpdate = caisseRepository.findAll().size();

        // Create the Caisse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCaisseMockMvc.perform(put("/api/caisses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caisse)))
            .andExpect(status().isCreated());

        // Validate the Caisse in the database
        List<Caisse> caisseList = caisseRepository.findAll();
        assertThat(caisseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCaisse() throws Exception {
        // Initialize the database
        caisseService.save(caisse);

        int databaseSizeBeforeDelete = caisseRepository.findAll().size();

        // Get the caisse
        restCaisseMockMvc.perform(delete("/api/caisses/{id}", caisse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Caisse> caisseList = caisseRepository.findAll();
        assertThat(caisseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caisse.class);
        Caisse caisse1 = new Caisse();
        caisse1.setId(1L);
        Caisse caisse2 = new Caisse();
        caisse2.setId(caisse1.getId());
        assertThat(caisse1).isEqualTo(caisse2);
        caisse2.setId(2L);
        assertThat(caisse1).isNotEqualTo(caisse2);
        caisse1.setId(null);
        assertThat(caisse1).isNotEqualTo(caisse2);
    }
}
