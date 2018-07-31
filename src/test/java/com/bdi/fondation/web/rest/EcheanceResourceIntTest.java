package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Echeance;
import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.domain.Mouvement;
import com.bdi.fondation.repository.EcheanceRepository;
import com.bdi.fondation.service.EcheanceService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.EcheanceCriteria;
import com.bdi.fondation.service.EcheanceQueryService;

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
 * Test class for the EcheanceResource REST controller.
 *
 * @see EcheanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class EcheanceResourceIntTest {

    private static final LocalDate DEFAULT_DATE_TOMBE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TOMBE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final String DEFAULT_ETAT_ECHEANCE = "AAAAAAAAAA";
    private static final String UPDATED_ETAT_ECHEANCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_PAYEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PAYEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RETRAIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RETRAIT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EcheanceRepository echeanceRepository;

    @Autowired
    private EcheanceService echeanceService;

    @Autowired
    private EcheanceQueryService echeanceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEcheanceMockMvc;

    private Echeance echeance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EcheanceResource echeanceResource = new EcheanceResource(echeanceService, echeanceQueryService);
        this.restEcheanceMockMvc = MockMvcBuilders.standaloneSetup(echeanceResource)
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
    public static Echeance createEntity(EntityManager em) {
        Echeance echeance = new Echeance()
            .dateTombe(DEFAULT_DATE_TOMBE)
            .montant(DEFAULT_MONTANT)
            .etatEcheance(DEFAULT_ETAT_ECHEANCE)
            .datePayement(DEFAULT_DATE_PAYEMENT)
            .dateRetrait(DEFAULT_DATE_RETRAIT);
        return echeance;
    }

    @Before
    public void initTest() {
        echeance = createEntity(em);
    }

    @Test
    @Transactional
    public void createEcheance() throws Exception {
        int databaseSizeBeforeCreate = echeanceRepository.findAll().size();

        // Create the Echeance
        restEcheanceMockMvc.perform(post("/api/echeances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(echeance)))
            .andExpect(status().isCreated());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeCreate + 1);
        Echeance testEcheance = echeanceList.get(echeanceList.size() - 1);
        assertThat(testEcheance.getDateTombe()).isEqualTo(DEFAULT_DATE_TOMBE);
        assertThat(testEcheance.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testEcheance.getEtatEcheance()).isEqualTo(DEFAULT_ETAT_ECHEANCE);
        assertThat(testEcheance.getDatePayement()).isEqualTo(DEFAULT_DATE_PAYEMENT);
        assertThat(testEcheance.getDateRetrait()).isEqualTo(DEFAULT_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void createEcheanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = echeanceRepository.findAll().size();

        // Create the Echeance with an existing ID
        echeance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcheanceMockMvc.perform(post("/api/echeances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(echeance)))
            .andExpect(status().isBadRequest());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = echeanceRepository.findAll().size();
        // set the field null
        echeance.setMontant(null);

        // Create the Echeance, which fails.

        restEcheanceMockMvc.perform(post("/api/echeances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(echeance)))
            .andExpect(status().isBadRequest());

        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtatEcheanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = echeanceRepository.findAll().size();
        // set the field null
        echeance.setEtatEcheance(null);

        // Create the Echeance, which fails.

        restEcheanceMockMvc.perform(post("/api/echeances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(echeance)))
            .andExpect(status().isBadRequest());

        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatePayementIsRequired() throws Exception {
        int databaseSizeBeforeTest = echeanceRepository.findAll().size();
        // set the field null
        echeance.setDatePayement(null);

        // Create the Echeance, which fails.

        restEcheanceMockMvc.perform(post("/api/echeances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(echeance)))
            .andExpect(status().isBadRequest());

        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEcheances() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList
        restEcheanceMockMvc.perform(get("/api/echeances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(echeance.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTombe").value(hasItem(DEFAULT_DATE_TOMBE.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].etatEcheance").value(hasItem(DEFAULT_ETAT_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].datePayement").value(hasItem(DEFAULT_DATE_PAYEMENT.toString())))
            .andExpect(jsonPath("$.[*].dateRetrait").value(hasItem(DEFAULT_DATE_RETRAIT.toString())));
    }

    @Test
    @Transactional
    public void getEcheance() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get the echeance
        restEcheanceMockMvc.perform(get("/api/echeances/{id}", echeance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(echeance.getId().intValue()))
            .andExpect(jsonPath("$.dateTombe").value(DEFAULT_DATE_TOMBE.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.etatEcheance").value(DEFAULT_ETAT_ECHEANCE.toString()))
            .andExpect(jsonPath("$.datePayement").value(DEFAULT_DATE_PAYEMENT.toString()))
            .andExpect(jsonPath("$.dateRetrait").value(DEFAULT_DATE_RETRAIT.toString()));
    }

    @Test
    @Transactional
    public void getAllEcheancesByDateTombeIsEqualToSomething() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where dateTombe equals to DEFAULT_DATE_TOMBE
        defaultEcheanceShouldBeFound("dateTombe.equals=" + DEFAULT_DATE_TOMBE);

        // Get all the echeanceList where dateTombe equals to UPDATED_DATE_TOMBE
        defaultEcheanceShouldNotBeFound("dateTombe.equals=" + UPDATED_DATE_TOMBE);
    }

    @Test
    @Transactional
    public void getAllEcheancesByDateTombeIsInShouldWork() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where dateTombe in DEFAULT_DATE_TOMBE or UPDATED_DATE_TOMBE
        defaultEcheanceShouldBeFound("dateTombe.in=" + DEFAULT_DATE_TOMBE + "," + UPDATED_DATE_TOMBE);

        // Get all the echeanceList where dateTombe equals to UPDATED_DATE_TOMBE
        defaultEcheanceShouldNotBeFound("dateTombe.in=" + UPDATED_DATE_TOMBE);
    }

    @Test
    @Transactional
    public void getAllEcheancesByDateTombeIsNullOrNotNull() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where dateTombe is not null
        defaultEcheanceShouldBeFound("dateTombe.specified=true");

        // Get all the echeanceList where dateTombe is null
        defaultEcheanceShouldNotBeFound("dateTombe.specified=false");
    }

    @Test
    @Transactional
    public void getAllEcheancesByDateTombeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where dateTombe greater than or equals to DEFAULT_DATE_TOMBE
        defaultEcheanceShouldBeFound("dateTombe.greaterOrEqualThan=" + DEFAULT_DATE_TOMBE);

        // Get all the echeanceList where dateTombe greater than or equals to UPDATED_DATE_TOMBE
        defaultEcheanceShouldNotBeFound("dateTombe.greaterOrEqualThan=" + UPDATED_DATE_TOMBE);
    }

    @Test
    @Transactional
    public void getAllEcheancesByDateTombeIsLessThanSomething() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where dateTombe less than or equals to DEFAULT_DATE_TOMBE
        defaultEcheanceShouldNotBeFound("dateTombe.lessThan=" + DEFAULT_DATE_TOMBE);

        // Get all the echeanceList where dateTombe less than or equals to UPDATED_DATE_TOMBE
        defaultEcheanceShouldBeFound("dateTombe.lessThan=" + UPDATED_DATE_TOMBE);
    }


    @Test
    @Transactional
    public void getAllEcheancesByMontantIsEqualToSomething() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where montant equals to DEFAULT_MONTANT
        defaultEcheanceShouldBeFound("montant.equals=" + DEFAULT_MONTANT);

        // Get all the echeanceList where montant equals to UPDATED_MONTANT
        defaultEcheanceShouldNotBeFound("montant.equals=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void getAllEcheancesByMontantIsInShouldWork() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where montant in DEFAULT_MONTANT or UPDATED_MONTANT
        defaultEcheanceShouldBeFound("montant.in=" + DEFAULT_MONTANT + "," + UPDATED_MONTANT);

        // Get all the echeanceList where montant equals to UPDATED_MONTANT
        defaultEcheanceShouldNotBeFound("montant.in=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void getAllEcheancesByMontantIsNullOrNotNull() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where montant is not null
        defaultEcheanceShouldBeFound("montant.specified=true");

        // Get all the echeanceList where montant is null
        defaultEcheanceShouldNotBeFound("montant.specified=false");
    }

    @Test
    @Transactional
    public void getAllEcheancesByEtatEcheanceIsEqualToSomething() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where etatEcheance equals to DEFAULT_ETAT_ECHEANCE
        defaultEcheanceShouldBeFound("etatEcheance.equals=" + DEFAULT_ETAT_ECHEANCE);

        // Get all the echeanceList where etatEcheance equals to UPDATED_ETAT_ECHEANCE
        defaultEcheanceShouldNotBeFound("etatEcheance.equals=" + UPDATED_ETAT_ECHEANCE);
    }

    @Test
    @Transactional
    public void getAllEcheancesByEtatEcheanceIsInShouldWork() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where etatEcheance in DEFAULT_ETAT_ECHEANCE or UPDATED_ETAT_ECHEANCE
        defaultEcheanceShouldBeFound("etatEcheance.in=" + DEFAULT_ETAT_ECHEANCE + "," + UPDATED_ETAT_ECHEANCE);

        // Get all the echeanceList where etatEcheance equals to UPDATED_ETAT_ECHEANCE
        defaultEcheanceShouldNotBeFound("etatEcheance.in=" + UPDATED_ETAT_ECHEANCE);
    }

    @Test
    @Transactional
    public void getAllEcheancesByEtatEcheanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where etatEcheance is not null
        defaultEcheanceShouldBeFound("etatEcheance.specified=true");

        // Get all the echeanceList where etatEcheance is null
        defaultEcheanceShouldNotBeFound("etatEcheance.specified=false");
    }

    @Test
    @Transactional
    public void getAllEcheancesByDatePayementIsEqualToSomething() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where datePayement equals to DEFAULT_DATE_PAYEMENT
        defaultEcheanceShouldBeFound("datePayement.equals=" + DEFAULT_DATE_PAYEMENT);

        // Get all the echeanceList where datePayement equals to UPDATED_DATE_PAYEMENT
        defaultEcheanceShouldNotBeFound("datePayement.equals=" + UPDATED_DATE_PAYEMENT);
    }

    @Test
    @Transactional
    public void getAllEcheancesByDatePayementIsInShouldWork() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where datePayement in DEFAULT_DATE_PAYEMENT or UPDATED_DATE_PAYEMENT
        defaultEcheanceShouldBeFound("datePayement.in=" + DEFAULT_DATE_PAYEMENT + "," + UPDATED_DATE_PAYEMENT);

        // Get all the echeanceList where datePayement equals to UPDATED_DATE_PAYEMENT
        defaultEcheanceShouldNotBeFound("datePayement.in=" + UPDATED_DATE_PAYEMENT);
    }

    @Test
    @Transactional
    public void getAllEcheancesByDatePayementIsNullOrNotNull() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where datePayement is not null
        defaultEcheanceShouldBeFound("datePayement.specified=true");

        // Get all the echeanceList where datePayement is null
        defaultEcheanceShouldNotBeFound("datePayement.specified=false");
    }

    @Test
    @Transactional
    public void getAllEcheancesByDatePayementIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where datePayement greater than or equals to DEFAULT_DATE_PAYEMENT
        defaultEcheanceShouldBeFound("datePayement.greaterOrEqualThan=" + DEFAULT_DATE_PAYEMENT);

        // Get all the echeanceList where datePayement greater than or equals to UPDATED_DATE_PAYEMENT
        defaultEcheanceShouldNotBeFound("datePayement.greaterOrEqualThan=" + UPDATED_DATE_PAYEMENT);
    }

    @Test
    @Transactional
    public void getAllEcheancesByDatePayementIsLessThanSomething() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where datePayement less than or equals to DEFAULT_DATE_PAYEMENT
        defaultEcheanceShouldNotBeFound("datePayement.lessThan=" + DEFAULT_DATE_PAYEMENT);

        // Get all the echeanceList where datePayement less than or equals to UPDATED_DATE_PAYEMENT
        defaultEcheanceShouldBeFound("datePayement.lessThan=" + UPDATED_DATE_PAYEMENT);
    }


    @Test
    @Transactional
    public void getAllEcheancesByDateRetraitIsEqualToSomething() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where dateRetrait equals to DEFAULT_DATE_RETRAIT
        defaultEcheanceShouldBeFound("dateRetrait.equals=" + DEFAULT_DATE_RETRAIT);

        // Get all the echeanceList where dateRetrait equals to UPDATED_DATE_RETRAIT
        defaultEcheanceShouldNotBeFound("dateRetrait.equals=" + UPDATED_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void getAllEcheancesByDateRetraitIsInShouldWork() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where dateRetrait in DEFAULT_DATE_RETRAIT or UPDATED_DATE_RETRAIT
        defaultEcheanceShouldBeFound("dateRetrait.in=" + DEFAULT_DATE_RETRAIT + "," + UPDATED_DATE_RETRAIT);

        // Get all the echeanceList where dateRetrait equals to UPDATED_DATE_RETRAIT
        defaultEcheanceShouldNotBeFound("dateRetrait.in=" + UPDATED_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void getAllEcheancesByDateRetraitIsNullOrNotNull() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where dateRetrait is not null
        defaultEcheanceShouldBeFound("dateRetrait.specified=true");

        // Get all the echeanceList where dateRetrait is null
        defaultEcheanceShouldNotBeFound("dateRetrait.specified=false");
    }

    @Test
    @Transactional
    public void getAllEcheancesByDateRetraitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where dateRetrait greater than or equals to DEFAULT_DATE_RETRAIT
        defaultEcheanceShouldBeFound("dateRetrait.greaterOrEqualThan=" + DEFAULT_DATE_RETRAIT);

        // Get all the echeanceList where dateRetrait greater than or equals to UPDATED_DATE_RETRAIT
        defaultEcheanceShouldNotBeFound("dateRetrait.greaterOrEqualThan=" + UPDATED_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void getAllEcheancesByDateRetraitIsLessThanSomething() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList where dateRetrait less than or equals to DEFAULT_DATE_RETRAIT
        defaultEcheanceShouldNotBeFound("dateRetrait.lessThan=" + DEFAULT_DATE_RETRAIT);

        // Get all the echeanceList where dateRetrait less than or equals to UPDATED_DATE_RETRAIT
        defaultEcheanceShouldBeFound("dateRetrait.lessThan=" + UPDATED_DATE_RETRAIT);
    }


    @Test
    @Transactional
    public void getAllEcheancesByPretIsEqualToSomething() throws Exception {
        // Initialize the database
        Pret pret = PretResourceIntTest.createEntity(em);
        em.persist(pret);
        em.flush();
        echeance.setPret(pret);
        echeanceRepository.saveAndFlush(echeance);
        Long pretId = pret.getId();

        // Get all the echeanceList where pret equals to pretId
        defaultEcheanceShouldBeFound("pretId.equals=" + pretId);

        // Get all the echeanceList where pret equals to pretId + 1
        defaultEcheanceShouldNotBeFound("pretId.equals=" + (pretId + 1));
    }


    @Test
    @Transactional
    public void getAllEcheancesByMouvementIsEqualToSomething() throws Exception {
        // Initialize the database
        Mouvement mouvement = MouvementResourceIntTest.createEntity(em);
        em.persist(mouvement);
        em.flush();
        echeance.addMouvement(mouvement);
        echeanceRepository.saveAndFlush(echeance);
        Long mouvementId = mouvement.getId();

        // Get all the echeanceList where mouvement equals to mouvementId
        defaultEcheanceShouldBeFound("mouvementId.equals=" + mouvementId);

        // Get all the echeanceList where mouvement equals to mouvementId + 1
        defaultEcheanceShouldNotBeFound("mouvementId.equals=" + (mouvementId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEcheanceShouldBeFound(String filter) throws Exception {
        restEcheanceMockMvc.perform(get("/api/echeances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(echeance.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTombe").value(hasItem(DEFAULT_DATE_TOMBE.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].etatEcheance").value(hasItem(DEFAULT_ETAT_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].datePayement").value(hasItem(DEFAULT_DATE_PAYEMENT.toString())))
            .andExpect(jsonPath("$.[*].dateRetrait").value(hasItem(DEFAULT_DATE_RETRAIT.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEcheanceShouldNotBeFound(String filter) throws Exception {
        restEcheanceMockMvc.perform(get("/api/echeances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingEcheance() throws Exception {
        // Get the echeance
        restEcheanceMockMvc.perform(get("/api/echeances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEcheance() throws Exception {
        // Initialize the database
        echeanceService.save(echeance);

        int databaseSizeBeforeUpdate = echeanceRepository.findAll().size();

        // Update the echeance
        Echeance updatedEcheance = echeanceRepository.findOne(echeance.getId());
        // Disconnect from session so that the updates on updatedEcheance are not directly saved in db
        em.detach(updatedEcheance);
        updatedEcheance
            .dateTombe(UPDATED_DATE_TOMBE)
            .montant(UPDATED_MONTANT)
            .etatEcheance(UPDATED_ETAT_ECHEANCE)
            .datePayement(UPDATED_DATE_PAYEMENT)
            .dateRetrait(UPDATED_DATE_RETRAIT);

        restEcheanceMockMvc.perform(put("/api/echeances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEcheance)))
            .andExpect(status().isOk());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeUpdate);
        Echeance testEcheance = echeanceList.get(echeanceList.size() - 1);
        assertThat(testEcheance.getDateTombe()).isEqualTo(UPDATED_DATE_TOMBE);
        assertThat(testEcheance.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testEcheance.getEtatEcheance()).isEqualTo(UPDATED_ETAT_ECHEANCE);
        assertThat(testEcheance.getDatePayement()).isEqualTo(UPDATED_DATE_PAYEMENT);
        assertThat(testEcheance.getDateRetrait()).isEqualTo(UPDATED_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void updateNonExistingEcheance() throws Exception {
        int databaseSizeBeforeUpdate = echeanceRepository.findAll().size();

        // Create the Echeance

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEcheanceMockMvc.perform(put("/api/echeances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(echeance)))
            .andExpect(status().isCreated());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEcheance() throws Exception {
        // Initialize the database
        echeanceService.save(echeance);

        int databaseSizeBeforeDelete = echeanceRepository.findAll().size();

        // Get the echeance
        restEcheanceMockMvc.perform(delete("/api/echeances/{id}", echeance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Echeance.class);
        Echeance echeance1 = new Echeance();
        echeance1.setId(1L);
        Echeance echeance2 = new Echeance();
        echeance2.setId(echeance1.getId());
        assertThat(echeance1).isEqualTo(echeance2);
        echeance2.setId(2L);
        assertThat(echeance1).isNotEqualTo(echeance2);
        echeance1.setId(null);
        assertThat(echeance1).isNotEqualTo(echeance2);
    }
}
