package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Projet;
import com.bdi.fondation.domain.Candidature;
import com.bdi.fondation.repository.ProjetRepository;
import com.bdi.fondation.service.ProjetService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.ProjetCriteria;
import com.bdi.fondation.service.ProjetQueryService;

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
 * Test class for the ProjetResource REST controller.
 *
 * @see ProjetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class ProjetResourceIntTest {

    private static final String DEFAULT_INTITULE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE = "BBBBBBBBBB";

    private static final Double DEFAULT_MONT_ESTIME = 1D;
    private static final Double UPDATED_MONT_ESTIME = 2D;

    private static final Double DEFAULT_MONT_APP = 1D;
    private static final Double UPDATED_MONT_APP = 2D;

    private static final String DEFAULT_DOMAINE = "AAAAAAAAAA";
    private static final String UPDATED_DOMAINE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private ProjetService projetService;

    @Autowired
    private ProjetQueryService projetQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjetMockMvc;

    private Projet projet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjetResource projetResource = new ProjetResource(projetService, projetQueryService);
        this.restProjetMockMvc = MockMvcBuilders.standaloneSetup(projetResource)
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
    public static Projet createEntity(EntityManager em) {
        Projet projet = new Projet()
            .intitule(DEFAULT_INTITULE)
            .montEstime(DEFAULT_MONT_ESTIME)
            .montApp(DEFAULT_MONT_APP)
            .domaine(DEFAULT_DOMAINE)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .dateCreation(DEFAULT_DATE_CREATION)
            .etat(DEFAULT_ETAT)
            .lieu(DEFAULT_LIEU);
        return projet;
    }

    @Before
    public void initTest() {
        projet = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjet() throws Exception {
        int databaseSizeBeforeCreate = projetRepository.findAll().size();

        // Create the Projet
        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isCreated());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate + 1);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getIntitule()).isEqualTo(DEFAULT_INTITULE);
        assertThat(testProjet.getMontEstime()).isEqualTo(DEFAULT_MONT_ESTIME);
        assertThat(testProjet.getMontApp()).isEqualTo(DEFAULT_MONT_APP);
        assertThat(testProjet.getDomaine()).isEqualTo(DEFAULT_DOMAINE);
        assertThat(testProjet.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProjet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjet.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testProjet.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testProjet.getLieu()).isEqualTo(DEFAULT_LIEU);
    }

    @Test
    @Transactional
    public void createProjetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projetRepository.findAll().size();

        // Create the Projet with an existing ID
        projet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIntituleIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setIntitule(null);

        // Create the Projet, which fails.

        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontEstimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setMontEstime(null);

        // Create the Projet, which fails.

        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontAppIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setMontApp(null);

        // Create the Projet, which fails.

        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomaineIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setDomaine(null);

        // Create the Projet, which fails.

        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setType(null);

        // Create the Projet, which fails.

        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setDescription(null);

        // Create the Projet, which fails.

        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setEtat(null);

        // Create the Projet, which fails.

        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLieuIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setLieu(null);

        // Create the Projet, which fails.

        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjets() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList
        restProjetMockMvc.perform(get("/api/projets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projet.getId().intValue())))
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE.toString())))
            .andExpect(jsonPath("$.[*].montEstime").value(hasItem(DEFAULT_MONT_ESTIME.doubleValue())))
            .andExpect(jsonPath("$.[*].montApp").value(hasItem(DEFAULT_MONT_APP.doubleValue())))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())));
    }

    @Test
    @Transactional
    public void getProjet() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get the projet
        restProjetMockMvc.perform(get("/api/projets/{id}", projet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projet.getId().intValue()))
            .andExpect(jsonPath("$.intitule").value(DEFAULT_INTITULE.toString()))
            .andExpect(jsonPath("$.montEstime").value(DEFAULT_MONT_ESTIME.doubleValue()))
            .andExpect(jsonPath("$.montApp").value(DEFAULT_MONT_APP.doubleValue()))
            .andExpect(jsonPath("$.domaine").value(DEFAULT_DOMAINE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU.toString()));
    }

    @Test
    @Transactional
    public void getAllProjetsByIntituleIsEqualToSomething() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where intitule equals to DEFAULT_INTITULE
        defaultProjetShouldBeFound("intitule.equals=" + DEFAULT_INTITULE);

        // Get all the projetList where intitule equals to UPDATED_INTITULE
        defaultProjetShouldNotBeFound("intitule.equals=" + UPDATED_INTITULE);
    }

    @Test
    @Transactional
    public void getAllProjetsByIntituleIsInShouldWork() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where intitule in DEFAULT_INTITULE or UPDATED_INTITULE
        defaultProjetShouldBeFound("intitule.in=" + DEFAULT_INTITULE + "," + UPDATED_INTITULE);

        // Get all the projetList where intitule equals to UPDATED_INTITULE
        defaultProjetShouldNotBeFound("intitule.in=" + UPDATED_INTITULE);
    }

    @Test
    @Transactional
    public void getAllProjetsByIntituleIsNullOrNotNull() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where intitule is not null
        defaultProjetShouldBeFound("intitule.specified=true");

        // Get all the projetList where intitule is null
        defaultProjetShouldNotBeFound("intitule.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjetsByMontEstimeIsEqualToSomething() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where montEstime equals to DEFAULT_MONT_ESTIME
        defaultProjetShouldBeFound("montEstime.equals=" + DEFAULT_MONT_ESTIME);

        // Get all the projetList where montEstime equals to UPDATED_MONT_ESTIME
        defaultProjetShouldNotBeFound("montEstime.equals=" + UPDATED_MONT_ESTIME);
    }

    @Test
    @Transactional
    public void getAllProjetsByMontEstimeIsInShouldWork() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where montEstime in DEFAULT_MONT_ESTIME or UPDATED_MONT_ESTIME
        defaultProjetShouldBeFound("montEstime.in=" + DEFAULT_MONT_ESTIME + "," + UPDATED_MONT_ESTIME);

        // Get all the projetList where montEstime equals to UPDATED_MONT_ESTIME
        defaultProjetShouldNotBeFound("montEstime.in=" + UPDATED_MONT_ESTIME);
    }

    @Test
    @Transactional
    public void getAllProjetsByMontEstimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where montEstime is not null
        defaultProjetShouldBeFound("montEstime.specified=true");

        // Get all the projetList where montEstime is null
        defaultProjetShouldNotBeFound("montEstime.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjetsByMontAppIsEqualToSomething() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where montApp equals to DEFAULT_MONT_APP
        defaultProjetShouldBeFound("montApp.equals=" + DEFAULT_MONT_APP);

        // Get all the projetList where montApp equals to UPDATED_MONT_APP
        defaultProjetShouldNotBeFound("montApp.equals=" + UPDATED_MONT_APP);
    }

    @Test
    @Transactional
    public void getAllProjetsByMontAppIsInShouldWork() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where montApp in DEFAULT_MONT_APP or UPDATED_MONT_APP
        defaultProjetShouldBeFound("montApp.in=" + DEFAULT_MONT_APP + "," + UPDATED_MONT_APP);

        // Get all the projetList where montApp equals to UPDATED_MONT_APP
        defaultProjetShouldNotBeFound("montApp.in=" + UPDATED_MONT_APP);
    }

    @Test
    @Transactional
    public void getAllProjetsByMontAppIsNullOrNotNull() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where montApp is not null
        defaultProjetShouldBeFound("montApp.specified=true");

        // Get all the projetList where montApp is null
        defaultProjetShouldNotBeFound("montApp.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjetsByDomaineIsEqualToSomething() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where domaine equals to DEFAULT_DOMAINE
        defaultProjetShouldBeFound("domaine.equals=" + DEFAULT_DOMAINE);

        // Get all the projetList where domaine equals to UPDATED_DOMAINE
        defaultProjetShouldNotBeFound("domaine.equals=" + UPDATED_DOMAINE);
    }

    @Test
    @Transactional
    public void getAllProjetsByDomaineIsInShouldWork() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where domaine in DEFAULT_DOMAINE or UPDATED_DOMAINE
        defaultProjetShouldBeFound("domaine.in=" + DEFAULT_DOMAINE + "," + UPDATED_DOMAINE);

        // Get all the projetList where domaine equals to UPDATED_DOMAINE
        defaultProjetShouldNotBeFound("domaine.in=" + UPDATED_DOMAINE);
    }

    @Test
    @Transactional
    public void getAllProjetsByDomaineIsNullOrNotNull() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where domaine is not null
        defaultProjetShouldBeFound("domaine.specified=true");

        // Get all the projetList where domaine is null
        defaultProjetShouldNotBeFound("domaine.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjetsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where type equals to DEFAULT_TYPE
        defaultProjetShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the projetList where type equals to UPDATED_TYPE
        defaultProjetShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllProjetsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProjetShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the projetList where type equals to UPDATED_TYPE
        defaultProjetShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllProjetsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where type is not null
        defaultProjetShouldBeFound("type.specified=true");

        // Get all the projetList where type is null
        defaultProjetShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjetsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where description equals to DEFAULT_DESCRIPTION
        defaultProjetShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projetList where description equals to UPDATED_DESCRIPTION
        defaultProjetShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjetsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjetShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projetList where description equals to UPDATED_DESCRIPTION
        defaultProjetShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjetsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where description is not null
        defaultProjetShouldBeFound("description.specified=true");

        // Get all the projetList where description is null
        defaultProjetShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjetsByDateCreationIsEqualToSomething() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where dateCreation equals to DEFAULT_DATE_CREATION
        defaultProjetShouldBeFound("dateCreation.equals=" + DEFAULT_DATE_CREATION);

        // Get all the projetList where dateCreation equals to UPDATED_DATE_CREATION
        defaultProjetShouldNotBeFound("dateCreation.equals=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllProjetsByDateCreationIsInShouldWork() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where dateCreation in DEFAULT_DATE_CREATION or UPDATED_DATE_CREATION
        defaultProjetShouldBeFound("dateCreation.in=" + DEFAULT_DATE_CREATION + "," + UPDATED_DATE_CREATION);

        // Get all the projetList where dateCreation equals to UPDATED_DATE_CREATION
        defaultProjetShouldNotBeFound("dateCreation.in=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllProjetsByDateCreationIsNullOrNotNull() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where dateCreation is not null
        defaultProjetShouldBeFound("dateCreation.specified=true");

        // Get all the projetList where dateCreation is null
        defaultProjetShouldNotBeFound("dateCreation.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjetsByDateCreationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where dateCreation greater than or equals to DEFAULT_DATE_CREATION
        defaultProjetShouldBeFound("dateCreation.greaterOrEqualThan=" + DEFAULT_DATE_CREATION);

        // Get all the projetList where dateCreation greater than or equals to UPDATED_DATE_CREATION
        defaultProjetShouldNotBeFound("dateCreation.greaterOrEqualThan=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllProjetsByDateCreationIsLessThanSomething() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where dateCreation less than or equals to DEFAULT_DATE_CREATION
        defaultProjetShouldNotBeFound("dateCreation.lessThan=" + DEFAULT_DATE_CREATION);

        // Get all the projetList where dateCreation less than or equals to UPDATED_DATE_CREATION
        defaultProjetShouldBeFound("dateCreation.lessThan=" + UPDATED_DATE_CREATION);
    }


    @Test
    @Transactional
    public void getAllProjetsByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where etat equals to DEFAULT_ETAT
        defaultProjetShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the projetList where etat equals to UPDATED_ETAT
        defaultProjetShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllProjetsByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultProjetShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the projetList where etat equals to UPDATED_ETAT
        defaultProjetShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllProjetsByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where etat is not null
        defaultProjetShouldBeFound("etat.specified=true");

        // Get all the projetList where etat is null
        defaultProjetShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjetsByLieuIsEqualToSomething() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where lieu equals to DEFAULT_LIEU
        defaultProjetShouldBeFound("lieu.equals=" + DEFAULT_LIEU);

        // Get all the projetList where lieu equals to UPDATED_LIEU
        defaultProjetShouldNotBeFound("lieu.equals=" + UPDATED_LIEU);
    }

    @Test
    @Transactional
    public void getAllProjetsByLieuIsInShouldWork() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where lieu in DEFAULT_LIEU or UPDATED_LIEU
        defaultProjetShouldBeFound("lieu.in=" + DEFAULT_LIEU + "," + UPDATED_LIEU);

        // Get all the projetList where lieu equals to UPDATED_LIEU
        defaultProjetShouldNotBeFound("lieu.in=" + UPDATED_LIEU);
    }

    @Test
    @Transactional
    public void getAllProjetsByLieuIsNullOrNotNull() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList where lieu is not null
        defaultProjetShouldBeFound("lieu.specified=true");

        // Get all the projetList where lieu is null
        defaultProjetShouldNotBeFound("lieu.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjetsByCandidatureIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidature candidature = CandidatureResourceIntTest.createEntity(em);
        em.persist(candidature);
        em.flush();
        projet.setCandidature(candidature);
        projetRepository.saveAndFlush(projet);
        Long candidatureId = candidature.getId();

        // Get all the projetList where candidature equals to candidatureId
        defaultProjetShouldBeFound("candidatureId.equals=" + candidatureId);

        // Get all the projetList where candidature equals to candidatureId + 1
        defaultProjetShouldNotBeFound("candidatureId.equals=" + (candidatureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProjetShouldBeFound(String filter) throws Exception {
        restProjetMockMvc.perform(get("/api/projets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projet.getId().intValue())))
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE.toString())))
            .andExpect(jsonPath("$.[*].montEstime").value(hasItem(DEFAULT_MONT_ESTIME.doubleValue())))
            .andExpect(jsonPath("$.[*].montApp").value(hasItem(DEFAULT_MONT_APP.doubleValue())))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProjetShouldNotBeFound(String filter) throws Exception {
        restProjetMockMvc.perform(get("/api/projets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingProjet() throws Exception {
        // Get the projet
        restProjetMockMvc.perform(get("/api/projets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjet() throws Exception {
        // Initialize the database
        projetService.save(projet);

        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet
        Projet updatedProjet = projetRepository.findOne(projet.getId());
        // Disconnect from session so that the updates on updatedProjet are not directly saved in db
        em.detach(updatedProjet);
        updatedProjet
            .intitule(UPDATED_INTITULE)
            .montEstime(UPDATED_MONT_ESTIME)
            .montApp(UPDATED_MONT_APP)
            .domaine(UPDATED_DOMAINE)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .dateCreation(UPDATED_DATE_CREATION)
            .etat(UPDATED_ETAT)
            .lieu(UPDATED_LIEU);

        restProjetMockMvc.perform(put("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjet)))
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getIntitule()).isEqualTo(UPDATED_INTITULE);
        assertThat(testProjet.getMontEstime()).isEqualTo(UPDATED_MONT_ESTIME);
        assertThat(testProjet.getMontApp()).isEqualTo(UPDATED_MONT_APP);
        assertThat(testProjet.getDomaine()).isEqualTo(UPDATED_DOMAINE);
        assertThat(testProjet.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProjet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjet.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testProjet.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testProjet.getLieu()).isEqualTo(UPDATED_LIEU);
    }

    @Test
    @Transactional
    public void updateNonExistingProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Create the Projet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjetMockMvc.perform(put("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isCreated());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjet() throws Exception {
        // Initialize the database
        projetService.save(projet);

        int databaseSizeBeforeDelete = projetRepository.findAll().size();

        // Get the projet
        restProjetMockMvc.perform(delete("/api/projets/{id}", projet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projet.class);
        Projet projet1 = new Projet();
        projet1.setId(1L);
        Projet projet2 = new Projet();
        projet2.setId(projet1.getId());
        assertThat(projet1).isEqualTo(projet2);
        projet2.setId(2L);
        assertThat(projet1).isNotEqualTo(projet2);
        projet1.setId(null);
        assertThat(projet1).isNotEqualTo(projet2);
    }
}
