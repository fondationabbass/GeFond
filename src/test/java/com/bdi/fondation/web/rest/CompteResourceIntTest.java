package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Compte;
import com.bdi.fondation.domain.Client;
import com.bdi.fondation.domain.Chapitre;
import com.bdi.fondation.repository.CompteRepository;
import com.bdi.fondation.service.CompteService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.CompteCriteria;
import com.bdi.fondation.service.CompteQueryService;

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
 * Test class for the CompteResource REST controller.
 *
 * @see CompteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class CompteResourceIntTest {

    private static final String DEFAULT_INTITULE_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_COMPTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OUVERTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OUVERTURE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_SOLDE = 1D;
    private static final Double UPDATED_SOLDE = 2D;

    private static final LocalDate DEFAULT_DATE_DERNIER_CREDIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DERNIER_CREDIT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DERNIER_DEBIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DERNIER_DEBIT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteService compteService;

    @Autowired
    private CompteQueryService compteQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompteMockMvc;

    private Compte compte;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompteResource compteResource = new CompteResource(compteService, compteQueryService);
        this.restCompteMockMvc = MockMvcBuilders.standaloneSetup(compteResource)
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
    public static Compte createEntity(EntityManager em) {
        Compte compte = new Compte()
            .intituleCompte(DEFAULT_INTITULE_COMPTE)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .solde(DEFAULT_SOLDE)
            .dateDernierCredit(DEFAULT_DATE_DERNIER_CREDIT)
            .dateDernierDebit(DEFAULT_DATE_DERNIER_DEBIT);
        return compte;
    }

    @Before
    public void initTest() {
        compte = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompte() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();

        // Create the Compte
        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isCreated());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate + 1);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getIntituleCompte()).isEqualTo(DEFAULT_INTITULE_COMPTE);
        assertThat(testCompte.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testCompte.getSolde()).isEqualTo(DEFAULT_SOLDE);
        assertThat(testCompte.getDateDernierCredit()).isEqualTo(DEFAULT_DATE_DERNIER_CREDIT);
        assertThat(testCompte.getDateDernierDebit()).isEqualTo(DEFAULT_DATE_DERNIER_DEBIT);
    }

    @Test
    @Transactional
    public void createCompteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();

        // Create the Compte with an existing ID
        compte.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIntituleCompteIsRequired() throws Exception {
        int databaseSizeBeforeTest = compteRepository.findAll().size();
        // set the field null
        compte.setIntituleCompte(null);

        // Create the Compte, which fails.

        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isBadRequest());

        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSoldeIsRequired() throws Exception {
        int databaseSizeBeforeTest = compteRepository.findAll().size();
        // set the field null
        compte.setSolde(null);

        // Create the Compte, which fails.

        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isBadRequest());

        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDernierCreditIsRequired() throws Exception {
        int databaseSizeBeforeTest = compteRepository.findAll().size();
        // set the field null
        compte.setDateDernierCredit(null);

        // Create the Compte, which fails.

        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isBadRequest());

        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDernierDebitIsRequired() throws Exception {
        int databaseSizeBeforeTest = compteRepository.findAll().size();
        // set the field null
        compte.setDateDernierDebit(null);

        // Create the Compte, which fails.

        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isBadRequest());

        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComptes() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList
        restCompteMockMvc.perform(get("/api/comptes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compte.getId().intValue())))
            .andExpect(jsonPath("$.[*].intituleCompte").value(hasItem(DEFAULT_INTITULE_COMPTE.toString())))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].solde").value(hasItem(DEFAULT_SOLDE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateDernierCredit").value(hasItem(DEFAULT_DATE_DERNIER_CREDIT.toString())))
            .andExpect(jsonPath("$.[*].dateDernierDebit").value(hasItem(DEFAULT_DATE_DERNIER_DEBIT.toString())));
    }

    @Test
    @Transactional
    public void getCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get the compte
        restCompteMockMvc.perform(get("/api/comptes/{id}", compte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compte.getId().intValue()))
            .andExpect(jsonPath("$.intituleCompte").value(DEFAULT_INTITULE_COMPTE.toString()))
            .andExpect(jsonPath("$.dateOuverture").value(DEFAULT_DATE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.solde").value(DEFAULT_SOLDE.doubleValue()))
            .andExpect(jsonPath("$.dateDernierCredit").value(DEFAULT_DATE_DERNIER_CREDIT.toString()))
            .andExpect(jsonPath("$.dateDernierDebit").value(DEFAULT_DATE_DERNIER_DEBIT.toString()));
    }

    @Test
    @Transactional
    public void getAllComptesByIntituleCompteIsEqualToSomething() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where intituleCompte equals to DEFAULT_INTITULE_COMPTE
        defaultCompteShouldBeFound("intituleCompte.equals=" + DEFAULT_INTITULE_COMPTE);

        // Get all the compteList where intituleCompte equals to UPDATED_INTITULE_COMPTE
        defaultCompteShouldNotBeFound("intituleCompte.equals=" + UPDATED_INTITULE_COMPTE);
    }

    @Test
    @Transactional
    public void getAllComptesByIntituleCompteIsInShouldWork() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where intituleCompte in DEFAULT_INTITULE_COMPTE or UPDATED_INTITULE_COMPTE
        defaultCompteShouldBeFound("intituleCompte.in=" + DEFAULT_INTITULE_COMPTE + "," + UPDATED_INTITULE_COMPTE);

        // Get all the compteList where intituleCompte equals to UPDATED_INTITULE_COMPTE
        defaultCompteShouldNotBeFound("intituleCompte.in=" + UPDATED_INTITULE_COMPTE);
    }

    @Test
    @Transactional
    public void getAllComptesByIntituleCompteIsNullOrNotNull() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where intituleCompte is not null
        defaultCompteShouldBeFound("intituleCompte.specified=true");

        // Get all the compteList where intituleCompte is null
        defaultCompteShouldNotBeFound("intituleCompte.specified=false");
    }

    @Test
    @Transactional
    public void getAllComptesByDateOuvertureIsEqualToSomething() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateOuverture equals to DEFAULT_DATE_OUVERTURE
        defaultCompteShouldBeFound("dateOuverture.equals=" + DEFAULT_DATE_OUVERTURE);

        // Get all the compteList where dateOuverture equals to UPDATED_DATE_OUVERTURE
        defaultCompteShouldNotBeFound("dateOuverture.equals=" + UPDATED_DATE_OUVERTURE);
    }

    @Test
    @Transactional
    public void getAllComptesByDateOuvertureIsInShouldWork() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateOuverture in DEFAULT_DATE_OUVERTURE or UPDATED_DATE_OUVERTURE
        defaultCompteShouldBeFound("dateOuverture.in=" + DEFAULT_DATE_OUVERTURE + "," + UPDATED_DATE_OUVERTURE);

        // Get all the compteList where dateOuverture equals to UPDATED_DATE_OUVERTURE
        defaultCompteShouldNotBeFound("dateOuverture.in=" + UPDATED_DATE_OUVERTURE);
    }

    @Test
    @Transactional
    public void getAllComptesByDateOuvertureIsNullOrNotNull() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateOuverture is not null
        defaultCompteShouldBeFound("dateOuverture.specified=true");

        // Get all the compteList where dateOuverture is null
        defaultCompteShouldNotBeFound("dateOuverture.specified=false");
    }

    @Test
    @Transactional
    public void getAllComptesByDateOuvertureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateOuverture greater than or equals to DEFAULT_DATE_OUVERTURE
        defaultCompteShouldBeFound("dateOuverture.greaterOrEqualThan=" + DEFAULT_DATE_OUVERTURE);

        // Get all the compteList where dateOuverture greater than or equals to UPDATED_DATE_OUVERTURE
        defaultCompteShouldNotBeFound("dateOuverture.greaterOrEqualThan=" + UPDATED_DATE_OUVERTURE);
    }

    @Test
    @Transactional
    public void getAllComptesByDateOuvertureIsLessThanSomething() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateOuverture less than or equals to DEFAULT_DATE_OUVERTURE
        defaultCompteShouldNotBeFound("dateOuverture.lessThan=" + DEFAULT_DATE_OUVERTURE);

        // Get all the compteList where dateOuverture less than or equals to UPDATED_DATE_OUVERTURE
        defaultCompteShouldBeFound("dateOuverture.lessThan=" + UPDATED_DATE_OUVERTURE);
    }


    @Test
    @Transactional
    public void getAllComptesBySoldeIsEqualToSomething() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where solde equals to DEFAULT_SOLDE
        defaultCompteShouldBeFound("solde.equals=" + DEFAULT_SOLDE);

        // Get all the compteList where solde equals to UPDATED_SOLDE
        defaultCompteShouldNotBeFound("solde.equals=" + UPDATED_SOLDE);
    }

    @Test
    @Transactional
    public void getAllComptesBySoldeIsInShouldWork() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where solde in DEFAULT_SOLDE or UPDATED_SOLDE
        defaultCompteShouldBeFound("solde.in=" + DEFAULT_SOLDE + "," + UPDATED_SOLDE);

        // Get all the compteList where solde equals to UPDATED_SOLDE
        defaultCompteShouldNotBeFound("solde.in=" + UPDATED_SOLDE);
    }

    @Test
    @Transactional
    public void getAllComptesBySoldeIsNullOrNotNull() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where solde is not null
        defaultCompteShouldBeFound("solde.specified=true");

        // Get all the compteList where solde is null
        defaultCompteShouldNotBeFound("solde.specified=false");
    }

    @Test
    @Transactional
    public void getAllComptesByDateDernierCreditIsEqualToSomething() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateDernierCredit equals to DEFAULT_DATE_DERNIER_CREDIT
        defaultCompteShouldBeFound("dateDernierCredit.equals=" + DEFAULT_DATE_DERNIER_CREDIT);

        // Get all the compteList where dateDernierCredit equals to UPDATED_DATE_DERNIER_CREDIT
        defaultCompteShouldNotBeFound("dateDernierCredit.equals=" + UPDATED_DATE_DERNIER_CREDIT);
    }

    @Test
    @Transactional
    public void getAllComptesByDateDernierCreditIsInShouldWork() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateDernierCredit in DEFAULT_DATE_DERNIER_CREDIT or UPDATED_DATE_DERNIER_CREDIT
        defaultCompteShouldBeFound("dateDernierCredit.in=" + DEFAULT_DATE_DERNIER_CREDIT + "," + UPDATED_DATE_DERNIER_CREDIT);

        // Get all the compteList where dateDernierCredit equals to UPDATED_DATE_DERNIER_CREDIT
        defaultCompteShouldNotBeFound("dateDernierCredit.in=" + UPDATED_DATE_DERNIER_CREDIT);
    }

    @Test
    @Transactional
    public void getAllComptesByDateDernierCreditIsNullOrNotNull() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateDernierCredit is not null
        defaultCompteShouldBeFound("dateDernierCredit.specified=true");

        // Get all the compteList where dateDernierCredit is null
        defaultCompteShouldNotBeFound("dateDernierCredit.specified=false");
    }

    @Test
    @Transactional
    public void getAllComptesByDateDernierCreditIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateDernierCredit greater than or equals to DEFAULT_DATE_DERNIER_CREDIT
        defaultCompteShouldBeFound("dateDernierCredit.greaterOrEqualThan=" + DEFAULT_DATE_DERNIER_CREDIT);

        // Get all the compteList where dateDernierCredit greater than or equals to UPDATED_DATE_DERNIER_CREDIT
        defaultCompteShouldNotBeFound("dateDernierCredit.greaterOrEqualThan=" + UPDATED_DATE_DERNIER_CREDIT);
    }

    @Test
    @Transactional
    public void getAllComptesByDateDernierCreditIsLessThanSomething() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateDernierCredit less than or equals to DEFAULT_DATE_DERNIER_CREDIT
        defaultCompteShouldNotBeFound("dateDernierCredit.lessThan=" + DEFAULT_DATE_DERNIER_CREDIT);

        // Get all the compteList where dateDernierCredit less than or equals to UPDATED_DATE_DERNIER_CREDIT
        defaultCompteShouldBeFound("dateDernierCredit.lessThan=" + UPDATED_DATE_DERNIER_CREDIT);
    }


    @Test
    @Transactional
    public void getAllComptesByDateDernierDebitIsEqualToSomething() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateDernierDebit equals to DEFAULT_DATE_DERNIER_DEBIT
        defaultCompteShouldBeFound("dateDernierDebit.equals=" + DEFAULT_DATE_DERNIER_DEBIT);

        // Get all the compteList where dateDernierDebit equals to UPDATED_DATE_DERNIER_DEBIT
        defaultCompteShouldNotBeFound("dateDernierDebit.equals=" + UPDATED_DATE_DERNIER_DEBIT);
    }

    @Test
    @Transactional
    public void getAllComptesByDateDernierDebitIsInShouldWork() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateDernierDebit in DEFAULT_DATE_DERNIER_DEBIT or UPDATED_DATE_DERNIER_DEBIT
        defaultCompteShouldBeFound("dateDernierDebit.in=" + DEFAULT_DATE_DERNIER_DEBIT + "," + UPDATED_DATE_DERNIER_DEBIT);

        // Get all the compteList where dateDernierDebit equals to UPDATED_DATE_DERNIER_DEBIT
        defaultCompteShouldNotBeFound("dateDernierDebit.in=" + UPDATED_DATE_DERNIER_DEBIT);
    }

    @Test
    @Transactional
    public void getAllComptesByDateDernierDebitIsNullOrNotNull() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateDernierDebit is not null
        defaultCompteShouldBeFound("dateDernierDebit.specified=true");

        // Get all the compteList where dateDernierDebit is null
        defaultCompteShouldNotBeFound("dateDernierDebit.specified=false");
    }

    @Test
    @Transactional
    public void getAllComptesByDateDernierDebitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateDernierDebit greater than or equals to DEFAULT_DATE_DERNIER_DEBIT
        defaultCompteShouldBeFound("dateDernierDebit.greaterOrEqualThan=" + DEFAULT_DATE_DERNIER_DEBIT);

        // Get all the compteList where dateDernierDebit greater than or equals to UPDATED_DATE_DERNIER_DEBIT
        defaultCompteShouldNotBeFound("dateDernierDebit.greaterOrEqualThan=" + UPDATED_DATE_DERNIER_DEBIT);
    }

    @Test
    @Transactional
    public void getAllComptesByDateDernierDebitIsLessThanSomething() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList where dateDernierDebit less than or equals to DEFAULT_DATE_DERNIER_DEBIT
        defaultCompteShouldNotBeFound("dateDernierDebit.lessThan=" + DEFAULT_DATE_DERNIER_DEBIT);

        // Get all the compteList where dateDernierDebit less than or equals to UPDATED_DATE_DERNIER_DEBIT
        defaultCompteShouldBeFound("dateDernierDebit.lessThan=" + UPDATED_DATE_DERNIER_DEBIT);
    }


    @Test
    @Transactional
    public void getAllComptesByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        Client client = ClientResourceIntTest.createEntity(em);
        em.persist(client);
        em.flush();
        compte.setClient(client);
        compteRepository.saveAndFlush(compte);
        Long clientId = client.getId();

        // Get all the compteList where client equals to clientId
        defaultCompteShouldBeFound("clientId.equals=" + clientId);

        // Get all the compteList where client equals to clientId + 1
        defaultCompteShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }


    @Test
    @Transactional
    public void getAllComptesByChapitreIsEqualToSomething() throws Exception {
        // Initialize the database
        Chapitre chapitre = ChapitreResourceIntTest.createEntity(em);
        em.persist(chapitre);
        em.flush();
        compte.setChapitre(chapitre);
        compteRepository.saveAndFlush(compte);
        Long chapitreId = chapitre.getId();

        // Get all the compteList where chapitre equals to chapitreId
        defaultCompteShouldBeFound("chapitreId.equals=" + chapitreId);

        // Get all the compteList where chapitre equals to chapitreId + 1
        defaultCompteShouldNotBeFound("chapitreId.equals=" + (chapitreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCompteShouldBeFound(String filter) throws Exception {
        restCompteMockMvc.perform(get("/api/comptes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compte.getId().intValue())))
            .andExpect(jsonPath("$.[*].intituleCompte").value(hasItem(DEFAULT_INTITULE_COMPTE.toString())))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].solde").value(hasItem(DEFAULT_SOLDE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateDernierCredit").value(hasItem(DEFAULT_DATE_DERNIER_CREDIT.toString())))
            .andExpect(jsonPath("$.[*].dateDernierDebit").value(hasItem(DEFAULT_DATE_DERNIER_DEBIT.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCompteShouldNotBeFound(String filter) throws Exception {
        restCompteMockMvc.perform(get("/api/comptes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCompte() throws Exception {
        // Get the compte
        restCompteMockMvc.perform(get("/api/comptes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompte() throws Exception {
        // Initialize the database
        compteService.save(compte);

        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Update the compte
        Compte updatedCompte = compteRepository.findOne(compte.getId());
        // Disconnect from session so that the updates on updatedCompte are not directly saved in db
        em.detach(updatedCompte);
        updatedCompte
            .intituleCompte(UPDATED_INTITULE_COMPTE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .solde(UPDATED_SOLDE)
            .dateDernierCredit(UPDATED_DATE_DERNIER_CREDIT)
            .dateDernierDebit(UPDATED_DATE_DERNIER_DEBIT);

        restCompteMockMvc.perform(put("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompte)))
            .andExpect(status().isOk());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getIntituleCompte()).isEqualTo(UPDATED_INTITULE_COMPTE);
        assertThat(testCompte.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testCompte.getSolde()).isEqualTo(UPDATED_SOLDE);
        assertThat(testCompte.getDateDernierCredit()).isEqualTo(UPDATED_DATE_DERNIER_CREDIT);
        assertThat(testCompte.getDateDernierDebit()).isEqualTo(UPDATED_DATE_DERNIER_DEBIT);
    }

    @Test
    @Transactional
    public void updateNonExistingCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Create the Compte

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompteMockMvc.perform(put("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isCreated());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompte() throws Exception {
        // Initialize the database
        compteService.save(compte);

        int databaseSizeBeforeDelete = compteRepository.findAll().size();

        // Get the compte
        restCompteMockMvc.perform(delete("/api/comptes/{id}", compte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Compte.class);
        Compte compte1 = new Compte();
        compte1.setId(1L);
        Compte compte2 = new Compte();
        compte2.setId(compte1.getId());
        assertThat(compte1).isEqualTo(compte2);
        compte2.setId(2L);
        assertThat(compte1).isNotEqualTo(compte2);
        compte1.setId(null);
        assertThat(compte1).isNotEqualTo(compte2);
    }
}
