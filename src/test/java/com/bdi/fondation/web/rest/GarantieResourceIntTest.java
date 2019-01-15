package com.bdi.fondation.web.rest;

import static com.bdi.fondation.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;

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

import com.bdi.fondation.GeFondApp;
import com.bdi.fondation.domain.Garantie;
import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.repository.GarantieRepository;
import com.bdi.fondation.service.GarantieQueryService;
import com.bdi.fondation.service.GarantieService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the GarantieResource REST controller.
 *
 * @see GarantieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class GarantieResourceIntTest {

    private static final String DEFAULT_TYPE_GAR = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_GAR = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT_EVALUE = 1D;
    private static final Double UPDATED_MONTANT_EVALUE = 2D;

    private static final Double DEFAULT_MONTANT_AFECT = 1D;
    private static final Double UPDATED_MONTANT_AFECT = 2D;

    private static final LocalDate DEFAULT_DATE_DEPOT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEPOT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUM_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_NUM_DOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RETRAIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RETRAIT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private GarantieRepository garantieRepository;

    @Autowired
    private GarantieService garantieService;

    @Autowired
    private GarantieQueryService garantieQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGarantieMockMvc;

    private Garantie garantie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GarantieResource garantieResource = new GarantieResource(garantieService, garantieQueryService);
        restGarantieMockMvc = MockMvcBuilders.standaloneSetup(garantieResource)
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
    public static Garantie createEntity(EntityManager em) {
        Garantie garantie = new Garantie()
            .typeGar(DEFAULT_TYPE_GAR)
            .dateDepot(DEFAULT_DATE_DEPOT)
            .numDocument(DEFAULT_NUM_DOCUMENT)
            .etat(DEFAULT_ETAT)
            .dateRetrait(DEFAULT_DATE_RETRAIT);
        return garantie;
    }

    @Before
    public void initTest() {
        garantie = createEntity(em);
    }

    @Test
    @Transactional
    public void createGarantie() throws Exception {
        int databaseSizeBeforeCreate = garantieRepository.findAll().size();

        // Create the Garantie
        restGarantieMockMvc.perform(post("/api/garanties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garantie)))
            .andExpect(status().isCreated());

        // Validate the Garantie in the database
        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeCreate + 1);
        Garantie testGarantie = garantieList.get(garantieList.size() - 1);
        assertThat(testGarantie.getTypeGar()).isEqualTo(DEFAULT_TYPE_GAR);
        assertThat(testGarantie.getDateDepot()).isEqualTo(DEFAULT_DATE_DEPOT);
        assertThat(testGarantie.getNumDocument()).isEqualTo(DEFAULT_NUM_DOCUMENT);
        assertThat(testGarantie.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testGarantie.getDateRetrait()).isEqualTo(DEFAULT_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void createGarantieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = garantieRepository.findAll().size();

        // Create the Garantie with an existing ID
        garantie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGarantieMockMvc.perform(post("/api/garanties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garantie)))
            .andExpect(status().isBadRequest());

        // Validate the Garantie in the database
        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeGarIsRequired() throws Exception {
        int databaseSizeBeforeTest = garantieRepository.findAll().size();
        // set the field null
        garantie.setTypeGar(null);

        // Create the Garantie, which fails.

        restGarantieMockMvc.perform(post("/api/garanties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garantie)))
            .andExpect(status().isBadRequest());

        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumDocumentIsRequired() throws Exception {
        int databaseSizeBeforeTest = garantieRepository.findAll().size();
        // set the field null
        garantie.setNumDocument(null);

        // Create the Garantie, which fails.

        restGarantieMockMvc.perform(post("/api/garanties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garantie)))
            .andExpect(status().isBadRequest());

        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = garantieRepository.findAll().size();
        // set the field null
        garantie.setEtat(null);

        // Create the Garantie, which fails.

        restGarantieMockMvc.perform(post("/api/garanties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garantie)))
            .andExpect(status().isBadRequest());

        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGaranties() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList
        restGarantieMockMvc.perform(get("/api/garanties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garantie.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeGar").value(hasItem(DEFAULT_TYPE_GAR.toString())))
            .andExpect(jsonPath("$.[*].montantEvalue").value(hasItem(DEFAULT_MONTANT_EVALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].montantAfect").value(hasItem(DEFAULT_MONTANT_AFECT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateDepot").value(hasItem(DEFAULT_DATE_DEPOT.toString())))
            .andExpect(jsonPath("$.[*].numDocument").value(hasItem(DEFAULT_NUM_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].dateRetrait").value(hasItem(DEFAULT_DATE_RETRAIT.toString())));
    }

    @Test
    @Transactional
    public void getGarantie() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get the garantie
        restGarantieMockMvc.perform(get("/api/garanties/{id}", garantie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(garantie.getId().intValue()))
            .andExpect(jsonPath("$.typeGar").value(DEFAULT_TYPE_GAR.toString()))
            .andExpect(jsonPath("$.montantEvalue").value(DEFAULT_MONTANT_EVALUE.doubleValue()))
            .andExpect(jsonPath("$.montantAfect").value(DEFAULT_MONTANT_AFECT.doubleValue()))
            .andExpect(jsonPath("$.dateDepot").value(DEFAULT_DATE_DEPOT.toString()))
            .andExpect(jsonPath("$.numDocument").value(DEFAULT_NUM_DOCUMENT.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.dateRetrait").value(DEFAULT_DATE_RETRAIT.toString()));
    }

    @Test
    @Transactional
    public void getAllGarantiesByTypeGarIsEqualToSomething() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where typeGar equals to DEFAULT_TYPE_GAR
        defaultGarantieShouldBeFound("typeGar.equals=" + DEFAULT_TYPE_GAR);

        // Get all the garantieList where typeGar equals to UPDATED_TYPE_GAR
        defaultGarantieShouldNotBeFound("typeGar.equals=" + UPDATED_TYPE_GAR);
    }

    @Test
    @Transactional
    public void getAllGarantiesByTypeGarIsInShouldWork() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where typeGar in DEFAULT_TYPE_GAR or UPDATED_TYPE_GAR
        defaultGarantieShouldBeFound("typeGar.in=" + DEFAULT_TYPE_GAR + "," + UPDATED_TYPE_GAR);

        // Get all the garantieList where typeGar equals to UPDATED_TYPE_GAR
        defaultGarantieShouldNotBeFound("typeGar.in=" + UPDATED_TYPE_GAR);
    }

    @Test
    @Transactional
    public void getAllGarantiesByTypeGarIsNullOrNotNull() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where typeGar is not null
        defaultGarantieShouldBeFound("typeGar.specified=true");

        // Get all the garantieList where typeGar is null
        defaultGarantieShouldNotBeFound("typeGar.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarantiesByMontantEvalueIsEqualToSomething() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where montantEvalue equals to DEFAULT_MONTANT_EVALUE
        defaultGarantieShouldBeFound("montantEvalue.equals=" + DEFAULT_MONTANT_EVALUE);

        // Get all the garantieList where montantEvalue equals to UPDATED_MONTANT_EVALUE
        defaultGarantieShouldNotBeFound("montantEvalue.equals=" + UPDATED_MONTANT_EVALUE);
    }

    @Test
    @Transactional
    public void getAllGarantiesByMontantEvalueIsInShouldWork() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where montantEvalue in DEFAULT_MONTANT_EVALUE or UPDATED_MONTANT_EVALUE
        defaultGarantieShouldBeFound("montantEvalue.in=" + DEFAULT_MONTANT_EVALUE + "," + UPDATED_MONTANT_EVALUE);

        // Get all the garantieList where montantEvalue equals to UPDATED_MONTANT_EVALUE
        defaultGarantieShouldNotBeFound("montantEvalue.in=" + UPDATED_MONTANT_EVALUE);
    }

    @Test
    @Transactional
    public void getAllGarantiesByMontantEvalueIsNullOrNotNull() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where montantEvalue is not null
        defaultGarantieShouldBeFound("montantEvalue.specified=true");

        // Get all the garantieList where montantEvalue is null
        defaultGarantieShouldNotBeFound("montantEvalue.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarantiesByMontantAfectIsEqualToSomething() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where montantAfect equals to DEFAULT_MONTANT_AFECT
        defaultGarantieShouldBeFound("montantAfect.equals=" + DEFAULT_MONTANT_AFECT);

        // Get all the garantieList where montantAfect equals to UPDATED_MONTANT_AFECT
        defaultGarantieShouldNotBeFound("montantAfect.equals=" + UPDATED_MONTANT_AFECT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByMontantAfectIsInShouldWork() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where montantAfect in DEFAULT_MONTANT_AFECT or UPDATED_MONTANT_AFECT
        defaultGarantieShouldBeFound("montantAfect.in=" + DEFAULT_MONTANT_AFECT + "," + UPDATED_MONTANT_AFECT);

        // Get all the garantieList where montantAfect equals to UPDATED_MONTANT_AFECT
        defaultGarantieShouldNotBeFound("montantAfect.in=" + UPDATED_MONTANT_AFECT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByMontantAfectIsNullOrNotNull() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where montantAfect is not null
        defaultGarantieShouldBeFound("montantAfect.specified=true");

        // Get all the garantieList where montantAfect is null
        defaultGarantieShouldNotBeFound("montantAfect.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarantiesByDateDepotIsEqualToSomething() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where dateDepot equals to DEFAULT_DATE_DEPOT
        defaultGarantieShouldBeFound("dateDepot.equals=" + DEFAULT_DATE_DEPOT);

        // Get all the garantieList where dateDepot equals to UPDATED_DATE_DEPOT
        defaultGarantieShouldNotBeFound("dateDepot.equals=" + UPDATED_DATE_DEPOT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByDateDepotIsInShouldWork() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where dateDepot in DEFAULT_DATE_DEPOT or UPDATED_DATE_DEPOT
        defaultGarantieShouldBeFound("dateDepot.in=" + DEFAULT_DATE_DEPOT + "," + UPDATED_DATE_DEPOT);

        // Get all the garantieList where dateDepot equals to UPDATED_DATE_DEPOT
        defaultGarantieShouldNotBeFound("dateDepot.in=" + UPDATED_DATE_DEPOT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByDateDepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where dateDepot is not null
        defaultGarantieShouldBeFound("dateDepot.specified=true");

        // Get all the garantieList where dateDepot is null
        defaultGarantieShouldNotBeFound("dateDepot.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarantiesByDateDepotIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where dateDepot greater than or equals to DEFAULT_DATE_DEPOT
        defaultGarantieShouldBeFound("dateDepot.greaterOrEqualThan=" + DEFAULT_DATE_DEPOT);

        // Get all the garantieList where dateDepot greater than or equals to UPDATED_DATE_DEPOT
        defaultGarantieShouldNotBeFound("dateDepot.greaterOrEqualThan=" + UPDATED_DATE_DEPOT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByDateDepotIsLessThanSomething() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where dateDepot less than or equals to DEFAULT_DATE_DEPOT
        defaultGarantieShouldNotBeFound("dateDepot.lessThan=" + DEFAULT_DATE_DEPOT);

        // Get all the garantieList where dateDepot less than or equals to UPDATED_DATE_DEPOT
        defaultGarantieShouldBeFound("dateDepot.lessThan=" + UPDATED_DATE_DEPOT);
    }


    @Test
    @Transactional
    public void getAllGarantiesByNumDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where numDocument equals to DEFAULT_NUM_DOCUMENT
        defaultGarantieShouldBeFound("numDocument.equals=" + DEFAULT_NUM_DOCUMENT);

        // Get all the garantieList where numDocument equals to UPDATED_NUM_DOCUMENT
        defaultGarantieShouldNotBeFound("numDocument.equals=" + UPDATED_NUM_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByNumDocumentIsInShouldWork() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where numDocument in DEFAULT_NUM_DOCUMENT or UPDATED_NUM_DOCUMENT
        defaultGarantieShouldBeFound("numDocument.in=" + DEFAULT_NUM_DOCUMENT + "," + UPDATED_NUM_DOCUMENT);

        // Get all the garantieList where numDocument equals to UPDATED_NUM_DOCUMENT
        defaultGarantieShouldNotBeFound("numDocument.in=" + UPDATED_NUM_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByNumDocumentIsNullOrNotNull() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where numDocument is not null
        defaultGarantieShouldBeFound("numDocument.specified=true");

        // Get all the garantieList where numDocument is null
        defaultGarantieShouldNotBeFound("numDocument.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarantiesByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where etat equals to DEFAULT_ETAT
        defaultGarantieShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the garantieList where etat equals to UPDATED_ETAT
        defaultGarantieShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultGarantieShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the garantieList where etat equals to UPDATED_ETAT
        defaultGarantieShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where etat is not null
        defaultGarantieShouldBeFound("etat.specified=true");

        // Get all the garantieList where etat is null
        defaultGarantieShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarantiesByDateRetraitIsEqualToSomething() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where dateRetrait equals to DEFAULT_DATE_RETRAIT
        defaultGarantieShouldBeFound("dateRetrait.equals=" + DEFAULT_DATE_RETRAIT);

        // Get all the garantieList where dateRetrait equals to UPDATED_DATE_RETRAIT
        defaultGarantieShouldNotBeFound("dateRetrait.equals=" + UPDATED_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByDateRetraitIsInShouldWork() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where dateRetrait in DEFAULT_DATE_RETRAIT or UPDATED_DATE_RETRAIT
        defaultGarantieShouldBeFound("dateRetrait.in=" + DEFAULT_DATE_RETRAIT + "," + UPDATED_DATE_RETRAIT);

        // Get all the garantieList where dateRetrait equals to UPDATED_DATE_RETRAIT
        defaultGarantieShouldNotBeFound("dateRetrait.in=" + UPDATED_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByDateRetraitIsNullOrNotNull() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where dateRetrait is not null
        defaultGarantieShouldBeFound("dateRetrait.specified=true");

        // Get all the garantieList where dateRetrait is null
        defaultGarantieShouldNotBeFound("dateRetrait.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarantiesByDateRetraitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where dateRetrait greater than or equals to DEFAULT_DATE_RETRAIT
        defaultGarantieShouldBeFound("dateRetrait.greaterOrEqualThan=" + DEFAULT_DATE_RETRAIT);

        // Get all the garantieList where dateRetrait greater than or equals to UPDATED_DATE_RETRAIT
        defaultGarantieShouldNotBeFound("dateRetrait.greaterOrEqualThan=" + UPDATED_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void getAllGarantiesByDateRetraitIsLessThanSomething() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList where dateRetrait less than or equals to DEFAULT_DATE_RETRAIT
        defaultGarantieShouldNotBeFound("dateRetrait.lessThan=" + DEFAULT_DATE_RETRAIT);

        // Get all the garantieList where dateRetrait less than or equals to UPDATED_DATE_RETRAIT
        defaultGarantieShouldBeFound("dateRetrait.lessThan=" + UPDATED_DATE_RETRAIT);
    }


    @Test
    @Transactional
    public void getAllGarantiesByPretIsEqualToSomething() throws Exception {
        // Initialize the database
        Pret pret = PretResourceIntTest.createEntity(em);
        em.persist(pret);
        em.flush();
        garantie.setPret(pret);
        garantieRepository.saveAndFlush(garantie);
        Long pretId = pret.getId();

        // Get all the garantieList where pret equals to pretId
        defaultGarantieShouldBeFound("pretId.equals=" + pretId);

        // Get all the garantieList where pret equals to pretId + 1
        defaultGarantieShouldNotBeFound("pretId.equals=" + (pretId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGarantieShouldBeFound(String filter) throws Exception {
        restGarantieMockMvc.perform(get("/api/garanties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garantie.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeGar").value(hasItem(DEFAULT_TYPE_GAR.toString())))
            .andExpect(jsonPath("$.[*].montantEvalue").value(hasItem(DEFAULT_MONTANT_EVALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].montantAfect").value(hasItem(DEFAULT_MONTANT_AFECT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateDepot").value(hasItem(DEFAULT_DATE_DEPOT.toString())))
            .andExpect(jsonPath("$.[*].numDocument").value(hasItem(DEFAULT_NUM_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].dateRetrait").value(hasItem(DEFAULT_DATE_RETRAIT.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGarantieShouldNotBeFound(String filter) throws Exception {
        restGarantieMockMvc.perform(get("/api/garanties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingGarantie() throws Exception {
        // Get the garantie
        restGarantieMockMvc.perform(get("/api/garanties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGarantie() throws Exception {
        // Initialize the database
        garantieService.save(garantie);

        int databaseSizeBeforeUpdate = garantieRepository.findAll().size();

        // Update the garantie
        Garantie updatedGarantie = garantieRepository.findOne(garantie.getId());
        // Disconnect from session so that the updates on updatedGarantie are not directly saved in db
        em.detach(updatedGarantie);
        updatedGarantie
            .typeGar(UPDATED_TYPE_GAR)
            .dateDepot(UPDATED_DATE_DEPOT)
            .numDocument(UPDATED_NUM_DOCUMENT)
            .etat(UPDATED_ETAT)
            .dateRetrait(UPDATED_DATE_RETRAIT);

        restGarantieMockMvc.perform(put("/api/garanties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGarantie)))
            .andExpect(status().isOk());

        // Validate the Garantie in the database
        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeUpdate);
        Garantie testGarantie = garantieList.get(garantieList.size() - 1);
        assertThat(testGarantie.getTypeGar()).isEqualTo(UPDATED_TYPE_GAR);
        assertThat(testGarantie.getDateDepot()).isEqualTo(UPDATED_DATE_DEPOT);
        assertThat(testGarantie.getNumDocument()).isEqualTo(UPDATED_NUM_DOCUMENT);
        assertThat(testGarantie.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testGarantie.getDateRetrait()).isEqualTo(UPDATED_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void updateNonExistingGarantie() throws Exception {
        int databaseSizeBeforeUpdate = garantieRepository.findAll().size();

        // Create the Garantie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGarantieMockMvc.perform(put("/api/garanties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garantie)))
            .andExpect(status().isCreated());

        // Validate the Garantie in the database
        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGarantie() throws Exception {
        // Initialize the database
        garantieService.save(garantie);

        int databaseSizeBeforeDelete = garantieRepository.findAll().size();

        // Get the garantie
        restGarantieMockMvc.perform(delete("/api/garanties/{id}", garantie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Garantie.class);
        Garantie garantie1 = new Garantie();
        garantie1.setId(1L);
        Garantie garantie2 = new Garantie();
        garantie2.setId(garantie1.getId());
        assertThat(garantie1).isEqualTo(garantie2);
        garantie2.setId(2L);
        assertThat(garantie1).isNotEqualTo(garantie2);
        garantie1.setId(null);
        assertThat(garantie1).isNotEqualTo(garantie2);
    }
}
