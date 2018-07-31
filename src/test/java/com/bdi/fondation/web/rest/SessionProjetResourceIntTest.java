package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.SessionProjet;
import com.bdi.fondation.repository.SessionProjetRepository;
import com.bdi.fondation.service.SessionProjetService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.SessionProjetCriteria;
import com.bdi.fondation.service.SessionProjetQueryService;

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
 * Test class for the SessionProjetResource REST controller.
 *
 * @see SessionProjetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class SessionProjetResourceIntTest {

    private static final LocalDate DEFAULT_DATE_OUVERT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OUVERT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FERMETURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FERMETURE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PLAFOND_FINANCE = 1D;
    private static final Double UPDATED_PLAFOND_FINANCE = 2D;

    private static final Integer DEFAULT_NOMBRE_CLIENT = 1;
    private static final Integer UPDATED_NOMBRE_CLIENT = 2;

    private static final Double DEFAULT_PLAFOND_CLIENT = 1D;
    private static final Double UPDATED_PLAFOND_CLIENT = 2D;

    private static final LocalDate DEFAULT_DATE_CREAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREAT = LocalDate.now(ZoneId.systemDefault());
    
    private static final String DEFAULT_DATE_MAJ = "AAAAAAAAAA";
    private static final String UPDATED_DATE_MAJ = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private SessionProjetRepository sessionProjetRepository;

    @Autowired
    private SessionProjetService sessionProjetService;

    @Autowired
    private SessionProjetQueryService sessionProjetQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSessionProjetMockMvc;

    private SessionProjet sessionProjet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SessionProjetResource sessionProjetResource = new SessionProjetResource(sessionProjetService, sessionProjetQueryService);
        this.restSessionProjetMockMvc = MockMvcBuilders.standaloneSetup(sessionProjetResource)
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
    public static SessionProjet createEntity(EntityManager em) {
        SessionProjet sessionProjet = new SessionProjet()
            .dateOuvert(DEFAULT_DATE_OUVERT)
            .dateFermeture(DEFAULT_DATE_FERMETURE)
            .plafondFinance(DEFAULT_PLAFOND_FINANCE)
            .nombreClient(DEFAULT_NOMBRE_CLIENT)
            .plafondClient(DEFAULT_PLAFOND_CLIENT)
            .dateCreat(DEFAULT_DATE_CREAT)
            .dateMaj(DEFAULT_DATE_MAJ)
            .etat(DEFAULT_ETAT);
        return sessionProjet;
    }

    @Before
    public void initTest() {
        sessionProjet = createEntity(em);
    }

    @Test
    @Transactional
    public void createSessionProjet() throws Exception {
        int databaseSizeBeforeCreate = sessionProjetRepository.findAll().size();

        // Create the SessionProjet
        restSessionProjetMockMvc.perform(post("/api/session-projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionProjet)))
            .andExpect(status().isCreated());

        // Validate the SessionProjet in the database
        List<SessionProjet> sessionProjetList = sessionProjetRepository.findAll();
        assertThat(sessionProjetList).hasSize(databaseSizeBeforeCreate + 1);
        SessionProjet testSessionProjet = sessionProjetList.get(sessionProjetList.size() - 1);
        assertThat(testSessionProjet.getDateOuvert()).isEqualTo(DEFAULT_DATE_OUVERT);
        assertThat(testSessionProjet.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
        assertThat(testSessionProjet.getPlafondFinance()).isEqualTo(DEFAULT_PLAFOND_FINANCE);
        assertThat(testSessionProjet.getNombreClient()).isEqualTo(DEFAULT_NOMBRE_CLIENT);
        assertThat(testSessionProjet.getPlafondClient()).isEqualTo(DEFAULT_PLAFOND_CLIENT);
        assertThat(testSessionProjet.getDateCreat()).isEqualTo(DEFAULT_DATE_CREAT);
        assertThat(testSessionProjet.getDateMaj()).isEqualTo(DEFAULT_DATE_MAJ);
        assertThat(testSessionProjet.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    public void createSessionProjetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sessionProjetRepository.findAll().size();

        // Create the SessionProjet with an existing ID
        sessionProjet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionProjetMockMvc.perform(post("/api/session-projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionProjet)))
            .andExpect(status().isBadRequest());

        // Validate the SessionProjet in the database
        List<SessionProjet> sessionProjetList = sessionProjetRepository.findAll();
        assertThat(sessionProjetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateOuvertIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionProjetRepository.findAll().size();
        // set the field null
        sessionProjet.setDateOuvert(null);

        // Create the SessionProjet, which fails.

        restSessionProjetMockMvc.perform(post("/api/session-projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionProjet)))
            .andExpect(status().isBadRequest());

        List<SessionProjet> sessionProjetList = sessionProjetRepository.findAll();
        assertThat(sessionProjetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFermetureIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionProjetRepository.findAll().size();
        // set the field null
        sessionProjet.setDateFermeture(null);

        // Create the SessionProjet, which fails.

        restSessionProjetMockMvc.perform(post("/api/session-projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionProjet)))
            .andExpect(status().isBadRequest());

        List<SessionProjet> sessionProjetList = sessionProjetRepository.findAll();
        assertThat(sessionProjetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlafondFinanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionProjetRepository.findAll().size();
        // set the field null
        sessionProjet.setPlafondFinance(null);

        // Create the SessionProjet, which fails.

        restSessionProjetMockMvc.perform(post("/api/session-projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionProjet)))
            .andExpect(status().isBadRequest());

        List<SessionProjet> sessionProjetList = sessionProjetRepository.findAll();
        assertThat(sessionProjetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionProjetRepository.findAll().size();
        // set the field null
        sessionProjet.setNombreClient(null);

        // Create the SessionProjet, which fails.

        restSessionProjetMockMvc.perform(post("/api/session-projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionProjet)))
            .andExpect(status().isBadRequest());

        List<SessionProjet> sessionProjetList = sessionProjetRepository.findAll();
        assertThat(sessionProjetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlafondClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionProjetRepository.findAll().size();
        // set the field null
        sessionProjet.setPlafondClient(null);

        // Create the SessionProjet, which fails.

        restSessionProjetMockMvc.perform(post("/api/session-projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionProjet)))
            .andExpect(status().isBadRequest());

        List<SessionProjet> sessionProjetList = sessionProjetRepository.findAll();
        assertThat(sessionProjetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSessionProjets() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList
        restSessionProjetMockMvc.perform(get("/api/session-projets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sessionProjet.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOuvert").value(hasItem(DEFAULT_DATE_OUVERT.toString())))
            .andExpect(jsonPath("$.[*].dateFermeture").value(hasItem(DEFAULT_DATE_FERMETURE.toString())))
            .andExpect(jsonPath("$.[*].plafondFinance").value(hasItem(DEFAULT_PLAFOND_FINANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].nombreClient").value(hasItem(DEFAULT_NOMBRE_CLIENT)))
            .andExpect(jsonPath("$.[*].plafondClient").value(hasItem(DEFAULT_PLAFOND_CLIENT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreat").value(hasItem(DEFAULT_DATE_CREAT.toString())))
            .andExpect(jsonPath("$.[*].dateMaj").value(hasItem(DEFAULT_DATE_MAJ.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    @Transactional
    public void getSessionProjet() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get the sessionProjet
        restSessionProjetMockMvc.perform(get("/api/session-projets/{id}", sessionProjet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sessionProjet.getId().intValue()))
            .andExpect(jsonPath("$.dateOuvert").value(DEFAULT_DATE_OUVERT.toString()))
            .andExpect(jsonPath("$.dateFermeture").value(DEFAULT_DATE_FERMETURE.toString()))
            .andExpect(jsonPath("$.plafondFinance").value(DEFAULT_PLAFOND_FINANCE.doubleValue()))
            .andExpect(jsonPath("$.nombreClient").value(DEFAULT_NOMBRE_CLIENT))
            .andExpect(jsonPath("$.plafondClient").value(DEFAULT_PLAFOND_CLIENT.doubleValue()))
            .andExpect(jsonPath("$.dateCreat").value(DEFAULT_DATE_CREAT.toString()))
            .andExpect(jsonPath("$.dateMaj").value(DEFAULT_DATE_MAJ.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateOuvertIsEqualToSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateOuvert equals to DEFAULT_DATE_OUVERT
        defaultSessionProjetShouldBeFound("dateOuvert.equals=" + DEFAULT_DATE_OUVERT);

        // Get all the sessionProjetList where dateOuvert equals to UPDATED_DATE_OUVERT
        defaultSessionProjetShouldNotBeFound("dateOuvert.equals=" + UPDATED_DATE_OUVERT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateOuvertIsInShouldWork() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateOuvert in DEFAULT_DATE_OUVERT or UPDATED_DATE_OUVERT
        defaultSessionProjetShouldBeFound("dateOuvert.in=" + DEFAULT_DATE_OUVERT + "," + UPDATED_DATE_OUVERT);

        // Get all the sessionProjetList where dateOuvert equals to UPDATED_DATE_OUVERT
        defaultSessionProjetShouldNotBeFound("dateOuvert.in=" + UPDATED_DATE_OUVERT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateOuvertIsNullOrNotNull() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateOuvert is not null
        defaultSessionProjetShouldBeFound("dateOuvert.specified=true");

        // Get all the sessionProjetList where dateOuvert is null
        defaultSessionProjetShouldNotBeFound("dateOuvert.specified=false");
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateOuvertIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateOuvert greater than or equals to DEFAULT_DATE_OUVERT
        defaultSessionProjetShouldBeFound("dateOuvert.greaterOrEqualThan=" + DEFAULT_DATE_OUVERT);

        // Get all the sessionProjetList where dateOuvert greater than or equals to UPDATED_DATE_OUVERT
        defaultSessionProjetShouldNotBeFound("dateOuvert.greaterOrEqualThan=" + UPDATED_DATE_OUVERT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateOuvertIsLessThanSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateOuvert less than or equals to DEFAULT_DATE_OUVERT
        defaultSessionProjetShouldNotBeFound("dateOuvert.lessThan=" + DEFAULT_DATE_OUVERT);

        // Get all the sessionProjetList where dateOuvert less than or equals to UPDATED_DATE_OUVERT
        defaultSessionProjetShouldBeFound("dateOuvert.lessThan=" + UPDATED_DATE_OUVERT);
    }


    @Test
    @Transactional
    public void getAllSessionProjetsByDateFermetureIsEqualToSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateFermeture equals to DEFAULT_DATE_FERMETURE
        defaultSessionProjetShouldBeFound("dateFermeture.equals=" + DEFAULT_DATE_FERMETURE);

        // Get all the sessionProjetList where dateFermeture equals to UPDATED_DATE_FERMETURE
        defaultSessionProjetShouldNotBeFound("dateFermeture.equals=" + UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateFermetureIsInShouldWork() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateFermeture in DEFAULT_DATE_FERMETURE or UPDATED_DATE_FERMETURE
        defaultSessionProjetShouldBeFound("dateFermeture.in=" + DEFAULT_DATE_FERMETURE + "," + UPDATED_DATE_FERMETURE);

        // Get all the sessionProjetList where dateFermeture equals to UPDATED_DATE_FERMETURE
        defaultSessionProjetShouldNotBeFound("dateFermeture.in=" + UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateFermetureIsNullOrNotNull() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateFermeture is not null
        defaultSessionProjetShouldBeFound("dateFermeture.specified=true");

        // Get all the sessionProjetList where dateFermeture is null
        defaultSessionProjetShouldNotBeFound("dateFermeture.specified=false");
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateFermetureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateFermeture greater than or equals to DEFAULT_DATE_FERMETURE
        defaultSessionProjetShouldBeFound("dateFermeture.greaterOrEqualThan=" + DEFAULT_DATE_FERMETURE);

        // Get all the sessionProjetList where dateFermeture greater than or equals to UPDATED_DATE_FERMETURE
        defaultSessionProjetShouldNotBeFound("dateFermeture.greaterOrEqualThan=" + UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateFermetureIsLessThanSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateFermeture less than or equals to DEFAULT_DATE_FERMETURE
        defaultSessionProjetShouldNotBeFound("dateFermeture.lessThan=" + DEFAULT_DATE_FERMETURE);

        // Get all the sessionProjetList where dateFermeture less than or equals to UPDATED_DATE_FERMETURE
        defaultSessionProjetShouldBeFound("dateFermeture.lessThan=" + UPDATED_DATE_FERMETURE);
    }


    @Test
    @Transactional
    public void getAllSessionProjetsByPlafondFinanceIsEqualToSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where plafondFinance equals to DEFAULT_PLAFOND_FINANCE
        defaultSessionProjetShouldBeFound("plafondFinance.equals=" + DEFAULT_PLAFOND_FINANCE);

        // Get all the sessionProjetList where plafondFinance equals to UPDATED_PLAFOND_FINANCE
        defaultSessionProjetShouldNotBeFound("plafondFinance.equals=" + UPDATED_PLAFOND_FINANCE);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByPlafondFinanceIsInShouldWork() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where plafondFinance in DEFAULT_PLAFOND_FINANCE or UPDATED_PLAFOND_FINANCE
        defaultSessionProjetShouldBeFound("plafondFinance.in=" + DEFAULT_PLAFOND_FINANCE + "," + UPDATED_PLAFOND_FINANCE);

        // Get all the sessionProjetList where plafondFinance equals to UPDATED_PLAFOND_FINANCE
        defaultSessionProjetShouldNotBeFound("plafondFinance.in=" + UPDATED_PLAFOND_FINANCE);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByPlafondFinanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where plafondFinance is not null
        defaultSessionProjetShouldBeFound("plafondFinance.specified=true");

        // Get all the sessionProjetList where plafondFinance is null
        defaultSessionProjetShouldNotBeFound("plafondFinance.specified=false");
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByNombreClientIsEqualToSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where nombreClient equals to DEFAULT_NOMBRE_CLIENT
        defaultSessionProjetShouldBeFound("nombreClient.equals=" + DEFAULT_NOMBRE_CLIENT);

        // Get all the sessionProjetList where nombreClient equals to UPDATED_NOMBRE_CLIENT
        defaultSessionProjetShouldNotBeFound("nombreClient.equals=" + UPDATED_NOMBRE_CLIENT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByNombreClientIsInShouldWork() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where nombreClient in DEFAULT_NOMBRE_CLIENT or UPDATED_NOMBRE_CLIENT
        defaultSessionProjetShouldBeFound("nombreClient.in=" + DEFAULT_NOMBRE_CLIENT + "," + UPDATED_NOMBRE_CLIENT);

        // Get all the sessionProjetList where nombreClient equals to UPDATED_NOMBRE_CLIENT
        defaultSessionProjetShouldNotBeFound("nombreClient.in=" + UPDATED_NOMBRE_CLIENT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByNombreClientIsNullOrNotNull() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where nombreClient is not null
        defaultSessionProjetShouldBeFound("nombreClient.specified=true");

        // Get all the sessionProjetList where nombreClient is null
        defaultSessionProjetShouldNotBeFound("nombreClient.specified=false");
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByNombreClientIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where nombreClient greater than or equals to DEFAULT_NOMBRE_CLIENT
        defaultSessionProjetShouldBeFound("nombreClient.greaterOrEqualThan=" + DEFAULT_NOMBRE_CLIENT);

        // Get all the sessionProjetList where nombreClient greater than or equals to UPDATED_NOMBRE_CLIENT
        defaultSessionProjetShouldNotBeFound("nombreClient.greaterOrEqualThan=" + UPDATED_NOMBRE_CLIENT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByNombreClientIsLessThanSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where nombreClient less than or equals to DEFAULT_NOMBRE_CLIENT
        defaultSessionProjetShouldNotBeFound("nombreClient.lessThan=" + DEFAULT_NOMBRE_CLIENT);

        // Get all the sessionProjetList where nombreClient less than or equals to UPDATED_NOMBRE_CLIENT
        defaultSessionProjetShouldBeFound("nombreClient.lessThan=" + UPDATED_NOMBRE_CLIENT);
    }


    @Test
    @Transactional
    public void getAllSessionProjetsByPlafondClientIsEqualToSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where plafondClient equals to DEFAULT_PLAFOND_CLIENT
        defaultSessionProjetShouldBeFound("plafondClient.equals=" + DEFAULT_PLAFOND_CLIENT);

        // Get all the sessionProjetList where plafondClient equals to UPDATED_PLAFOND_CLIENT
        defaultSessionProjetShouldNotBeFound("plafondClient.equals=" + UPDATED_PLAFOND_CLIENT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByPlafondClientIsInShouldWork() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where plafondClient in DEFAULT_PLAFOND_CLIENT or UPDATED_PLAFOND_CLIENT
        defaultSessionProjetShouldBeFound("plafondClient.in=" + DEFAULT_PLAFOND_CLIENT + "," + UPDATED_PLAFOND_CLIENT);

        // Get all the sessionProjetList where plafondClient equals to UPDATED_PLAFOND_CLIENT
        defaultSessionProjetShouldNotBeFound("plafondClient.in=" + UPDATED_PLAFOND_CLIENT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByPlafondClientIsNullOrNotNull() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where plafondClient is not null
        defaultSessionProjetShouldBeFound("plafondClient.specified=true");

        // Get all the sessionProjetList where plafondClient is null
        defaultSessionProjetShouldNotBeFound("plafondClient.specified=false");
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateCreatIsEqualToSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateCreat equals to DEFAULT_DATE_CREAT
        defaultSessionProjetShouldBeFound("dateCreat.equals=" + DEFAULT_DATE_CREAT);

        // Get all the sessionProjetList where dateCreat equals to UPDATED_DATE_CREAT
        defaultSessionProjetShouldNotBeFound("dateCreat.equals=" + UPDATED_DATE_CREAT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateCreatIsInShouldWork() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateCreat in DEFAULT_DATE_CREAT or UPDATED_DATE_CREAT
        defaultSessionProjetShouldBeFound("dateCreat.in=" + DEFAULT_DATE_CREAT + "," + UPDATED_DATE_CREAT);

        // Get all the sessionProjetList where dateCreat equals to UPDATED_DATE_CREAT
        defaultSessionProjetShouldNotBeFound("dateCreat.in=" + UPDATED_DATE_CREAT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateCreatIsNullOrNotNull() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateCreat is not null
        defaultSessionProjetShouldBeFound("dateCreat.specified=true");

        // Get all the sessionProjetList where dateCreat is null
        defaultSessionProjetShouldNotBeFound("dateCreat.specified=false");
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateMajIsEqualToSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateMaj equals to DEFAULT_DATE_MAJ
        defaultSessionProjetShouldBeFound("dateMaj.equals=" + DEFAULT_DATE_MAJ);

        // Get all the sessionProjetList where dateMaj equals to UPDATED_DATE_MAJ
        defaultSessionProjetShouldNotBeFound("dateMaj.equals=" + UPDATED_DATE_MAJ);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateMajIsInShouldWork() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateMaj in DEFAULT_DATE_MAJ or UPDATED_DATE_MAJ
        defaultSessionProjetShouldBeFound("dateMaj.in=" + DEFAULT_DATE_MAJ + "," + UPDATED_DATE_MAJ);

        // Get all the sessionProjetList where dateMaj equals to UPDATED_DATE_MAJ
        defaultSessionProjetShouldNotBeFound("dateMaj.in=" + UPDATED_DATE_MAJ);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByDateMajIsNullOrNotNull() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where dateMaj is not null
        defaultSessionProjetShouldBeFound("dateMaj.specified=true");

        // Get all the sessionProjetList where dateMaj is null
        defaultSessionProjetShouldNotBeFound("dateMaj.specified=false");
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where etat equals to DEFAULT_ETAT
        defaultSessionProjetShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the sessionProjetList where etat equals to UPDATED_ETAT
        defaultSessionProjetShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultSessionProjetShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the sessionProjetList where etat equals to UPDATED_ETAT
        defaultSessionProjetShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllSessionProjetsByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        sessionProjetRepository.saveAndFlush(sessionProjet);

        // Get all the sessionProjetList where etat is not null
        defaultSessionProjetShouldBeFound("etat.specified=true");

        // Get all the sessionProjetList where etat is null
        defaultSessionProjetShouldNotBeFound("etat.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSessionProjetShouldBeFound(String filter) throws Exception {
        restSessionProjetMockMvc.perform(get("/api/session-projets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sessionProjet.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOuvert").value(hasItem(DEFAULT_DATE_OUVERT.toString())))
            .andExpect(jsonPath("$.[*].dateFermeture").value(hasItem(DEFAULT_DATE_FERMETURE.toString())))
            .andExpect(jsonPath("$.[*].plafondFinance").value(hasItem(DEFAULT_PLAFOND_FINANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].nombreClient").value(hasItem(DEFAULT_NOMBRE_CLIENT)))
            .andExpect(jsonPath("$.[*].plafondClient").value(hasItem(DEFAULT_PLAFOND_CLIENT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreat").value(hasItem(DEFAULT_DATE_CREAT.toString())))
            .andExpect(jsonPath("$.[*].dateMaj").value(hasItem(DEFAULT_DATE_MAJ.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSessionProjetShouldNotBeFound(String filter) throws Exception {
        restSessionProjetMockMvc.perform(get("/api/session-projets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingSessionProjet() throws Exception {
        // Get the sessionProjet
        restSessionProjetMockMvc.perform(get("/api/session-projets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSessionProjet() throws Exception {
        // Initialize the database
        sessionProjetService.save(sessionProjet);

        int databaseSizeBeforeUpdate = sessionProjetRepository.findAll().size();

        // Update the sessionProjet
        SessionProjet updatedSessionProjet = sessionProjetRepository.findOne(sessionProjet.getId());
        // Disconnect from session so that the updates on updatedSessionProjet are not directly saved in db
        em.detach(updatedSessionProjet);
        updatedSessionProjet
            .dateOuvert(UPDATED_DATE_OUVERT)
            .dateFermeture(UPDATED_DATE_FERMETURE)
            .plafondFinance(UPDATED_PLAFOND_FINANCE)
            .nombreClient(UPDATED_NOMBRE_CLIENT)
            .plafondClient(UPDATED_PLAFOND_CLIENT)
            .dateCreat(UPDATED_DATE_CREAT)
            .dateMaj(UPDATED_DATE_MAJ)
            .etat(UPDATED_ETAT);

        restSessionProjetMockMvc.perform(put("/api/session-projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSessionProjet)))
            .andExpect(status().isOk());

        // Validate the SessionProjet in the database
        List<SessionProjet> sessionProjetList = sessionProjetRepository.findAll();
        assertThat(sessionProjetList).hasSize(databaseSizeBeforeUpdate);
        SessionProjet testSessionProjet = sessionProjetList.get(sessionProjetList.size() - 1);
        assertThat(testSessionProjet.getDateOuvert()).isEqualTo(UPDATED_DATE_OUVERT);
        assertThat(testSessionProjet.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
        assertThat(testSessionProjet.getPlafondFinance()).isEqualTo(UPDATED_PLAFOND_FINANCE);
        assertThat(testSessionProjet.getNombreClient()).isEqualTo(UPDATED_NOMBRE_CLIENT);
        assertThat(testSessionProjet.getPlafondClient()).isEqualTo(UPDATED_PLAFOND_CLIENT);
        assertThat(testSessionProjet.getDateCreat()).isEqualTo(UPDATED_DATE_CREAT);
        assertThat(testSessionProjet.getDateMaj()).isEqualTo(UPDATED_DATE_MAJ);
        assertThat(testSessionProjet.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void updateNonExistingSessionProjet() throws Exception {
        int databaseSizeBeforeUpdate = sessionProjetRepository.findAll().size();

        // Create the SessionProjet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSessionProjetMockMvc.perform(put("/api/session-projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionProjet)))
            .andExpect(status().isCreated());

        // Validate the SessionProjet in the database
        List<SessionProjet> sessionProjetList = sessionProjetRepository.findAll();
        assertThat(sessionProjetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSessionProjet() throws Exception {
        // Initialize the database
        sessionProjetService.save(sessionProjet);

        int databaseSizeBeforeDelete = sessionProjetRepository.findAll().size();

        // Get the sessionProjet
        restSessionProjetMockMvc.perform(delete("/api/session-projets/{id}", sessionProjet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SessionProjet> sessionProjetList = sessionProjetRepository.findAll();
        assertThat(sessionProjetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionProjet.class);
        SessionProjet sessionProjet1 = new SessionProjet();
        sessionProjet1.setId(1L);
        SessionProjet sessionProjet2 = new SessionProjet();
        sessionProjet2.setId(sessionProjet1.getId());
        assertThat(sessionProjet1).isEqualTo(sessionProjet2);
        sessionProjet2.setId(2L);
        assertThat(sessionProjet1).isNotEqualTo(sessionProjet2);
        sessionProjet1.setId(null);
        assertThat(sessionProjet1).isNotEqualTo(sessionProjet2);
    }
}
