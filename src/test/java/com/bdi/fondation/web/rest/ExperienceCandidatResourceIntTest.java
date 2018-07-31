package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.ExperienceCandidat;
import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.repository.ExperienceCandidatRepository;
import com.bdi.fondation.service.ExperienceCandidatService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.ExperienceCandidatCriteria;
import com.bdi.fondation.service.ExperienceCandidatQueryService;

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
 * Test class for the ExperienceCandidatResource REST controller.
 *
 * @see ExperienceCandidatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class ExperienceCandidatResourceIntTest {

    private static final String DEFAULT_TYPE_INFO = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAB = "AAAAAAAAAA";
    private static final String UPDATED_ETAB = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESS_ETAB = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS_ETAB = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEB = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ExperienceCandidatRepository experienceCandidatRepository;

    @Autowired
    private ExperienceCandidatService experienceCandidatService;

    @Autowired
    private ExperienceCandidatQueryService experienceCandidatQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExperienceCandidatMockMvc;

    private ExperienceCandidat experienceCandidat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperienceCandidatResource experienceCandidatResource = new ExperienceCandidatResource(experienceCandidatService, experienceCandidatQueryService);
        this.restExperienceCandidatMockMvc = MockMvcBuilders.standaloneSetup(experienceCandidatResource)
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
    public static ExperienceCandidat createEntity(EntityManager em) {
        ExperienceCandidat experienceCandidat = new ExperienceCandidat()
            .typeInfo(DEFAULT_TYPE_INFO)
            .titre(DEFAULT_TITRE)
            .etab(DEFAULT_ETAB)
            .adressEtab(DEFAULT_ADRESS_ETAB)
            .dateDeb(DEFAULT_DATE_DEB)
            .dateFin(DEFAULT_DATE_FIN);
        return experienceCandidat;
    }

    @Before
    public void initTest() {
        experienceCandidat = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperienceCandidat() throws Exception {
        int databaseSizeBeforeCreate = experienceCandidatRepository.findAll().size();

        // Create the ExperienceCandidat
        restExperienceCandidatMockMvc.perform(post("/api/experience-candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceCandidat)))
            .andExpect(status().isCreated());

        // Validate the ExperienceCandidat in the database
        List<ExperienceCandidat> experienceCandidatList = experienceCandidatRepository.findAll();
        assertThat(experienceCandidatList).hasSize(databaseSizeBeforeCreate + 1);
        ExperienceCandidat testExperienceCandidat = experienceCandidatList.get(experienceCandidatList.size() - 1);
        assertThat(testExperienceCandidat.getTypeInfo()).isEqualTo(DEFAULT_TYPE_INFO);
        assertThat(testExperienceCandidat.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testExperienceCandidat.getEtab()).isEqualTo(DEFAULT_ETAB);
        assertThat(testExperienceCandidat.getAdressEtab()).isEqualTo(DEFAULT_ADRESS_ETAB);
        assertThat(testExperienceCandidat.getDateDeb()).isEqualTo(DEFAULT_DATE_DEB);
        assertThat(testExperienceCandidat.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    public void createExperienceCandidatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experienceCandidatRepository.findAll().size();

        // Create the ExperienceCandidat with an existing ID
        experienceCandidat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienceCandidatMockMvc.perform(post("/api/experience-candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceCandidat)))
            .andExpect(status().isBadRequest());

        // Validate the ExperienceCandidat in the database
        List<ExperienceCandidat> experienceCandidatList = experienceCandidatRepository.findAll();
        assertThat(experienceCandidatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceCandidatRepository.findAll().size();
        // set the field null
        experienceCandidat.setTypeInfo(null);

        // Create the ExperienceCandidat, which fails.

        restExperienceCandidatMockMvc.perform(post("/api/experience-candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceCandidat)))
            .andExpect(status().isBadRequest());

        List<ExperienceCandidat> experienceCandidatList = experienceCandidatRepository.findAll();
        assertThat(experienceCandidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitreIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceCandidatRepository.findAll().size();
        // set the field null
        experienceCandidat.setTitre(null);

        // Create the ExperienceCandidat, which fails.

        restExperienceCandidatMockMvc.perform(post("/api/experience-candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceCandidat)))
            .andExpect(status().isBadRequest());

        List<ExperienceCandidat> experienceCandidatList = experienceCandidatRepository.findAll();
        assertThat(experienceCandidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtabIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceCandidatRepository.findAll().size();
        // set the field null
        experienceCandidat.setEtab(null);

        // Create the ExperienceCandidat, which fails.

        restExperienceCandidatMockMvc.perform(post("/api/experience-candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceCandidat)))
            .andExpect(status().isBadRequest());

        List<ExperienceCandidat> experienceCandidatList = experienceCandidatRepository.findAll();
        assertThat(experienceCandidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceCandidatRepository.findAll().size();
        // set the field null
        experienceCandidat.setDateDeb(null);

        // Create the ExperienceCandidat, which fails.

        restExperienceCandidatMockMvc.perform(post("/api/experience-candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceCandidat)))
            .andExpect(status().isBadRequest());

        List<ExperienceCandidat> experienceCandidatList = experienceCandidatRepository.findAll();
        assertThat(experienceCandidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceCandidatRepository.findAll().size();
        // set the field null
        experienceCandidat.setDateFin(null);

        // Create the ExperienceCandidat, which fails.

        restExperienceCandidatMockMvc.perform(post("/api/experience-candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceCandidat)))
            .andExpect(status().isBadRequest());

        List<ExperienceCandidat> experienceCandidatList = experienceCandidatRepository.findAll();
        assertThat(experienceCandidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidats() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList
        restExperienceCandidatMockMvc.perform(get("/api/experience-candidats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experienceCandidat.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeInfo").value(hasItem(DEFAULT_TYPE_INFO.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].etab").value(hasItem(DEFAULT_ETAB.toString())))
            .andExpect(jsonPath("$.[*].adressEtab").value(hasItem(DEFAULT_ADRESS_ETAB.toString())))
            .andExpect(jsonPath("$.[*].dateDeb").value(hasItem(DEFAULT_DATE_DEB.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }

    @Test
    @Transactional
    public void getExperienceCandidat() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get the experienceCandidat
        restExperienceCandidatMockMvc.perform(get("/api/experience-candidats/{id}", experienceCandidat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experienceCandidat.getId().intValue()))
            .andExpect(jsonPath("$.typeInfo").value(DEFAULT_TYPE_INFO.toString()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.etab").value(DEFAULT_ETAB.toString()))
            .andExpect(jsonPath("$.adressEtab").value(DEFAULT_ADRESS_ETAB.toString()))
            .andExpect(jsonPath("$.dateDeb").value(DEFAULT_DATE_DEB.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByTypeInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where typeInfo equals to DEFAULT_TYPE_INFO
        defaultExperienceCandidatShouldBeFound("typeInfo.equals=" + DEFAULT_TYPE_INFO);

        // Get all the experienceCandidatList where typeInfo equals to UPDATED_TYPE_INFO
        defaultExperienceCandidatShouldNotBeFound("typeInfo.equals=" + UPDATED_TYPE_INFO);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByTypeInfoIsInShouldWork() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where typeInfo in DEFAULT_TYPE_INFO or UPDATED_TYPE_INFO
        defaultExperienceCandidatShouldBeFound("typeInfo.in=" + DEFAULT_TYPE_INFO + "," + UPDATED_TYPE_INFO);

        // Get all the experienceCandidatList where typeInfo equals to UPDATED_TYPE_INFO
        defaultExperienceCandidatShouldNotBeFound("typeInfo.in=" + UPDATED_TYPE_INFO);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByTypeInfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where typeInfo is not null
        defaultExperienceCandidatShouldBeFound("typeInfo.specified=true");

        // Get all the experienceCandidatList where typeInfo is null
        defaultExperienceCandidatShouldNotBeFound("typeInfo.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByTitreIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where titre equals to DEFAULT_TITRE
        defaultExperienceCandidatShouldBeFound("titre.equals=" + DEFAULT_TITRE);

        // Get all the experienceCandidatList where titre equals to UPDATED_TITRE
        defaultExperienceCandidatShouldNotBeFound("titre.equals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByTitreIsInShouldWork() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where titre in DEFAULT_TITRE or UPDATED_TITRE
        defaultExperienceCandidatShouldBeFound("titre.in=" + DEFAULT_TITRE + "," + UPDATED_TITRE);

        // Get all the experienceCandidatList where titre equals to UPDATED_TITRE
        defaultExperienceCandidatShouldNotBeFound("titre.in=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByTitreIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where titre is not null
        defaultExperienceCandidatShouldBeFound("titre.specified=true");

        // Get all the experienceCandidatList where titre is null
        defaultExperienceCandidatShouldNotBeFound("titre.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByEtabIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where etab equals to DEFAULT_ETAB
        defaultExperienceCandidatShouldBeFound("etab.equals=" + DEFAULT_ETAB);

        // Get all the experienceCandidatList where etab equals to UPDATED_ETAB
        defaultExperienceCandidatShouldNotBeFound("etab.equals=" + UPDATED_ETAB);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByEtabIsInShouldWork() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where etab in DEFAULT_ETAB or UPDATED_ETAB
        defaultExperienceCandidatShouldBeFound("etab.in=" + DEFAULT_ETAB + "," + UPDATED_ETAB);

        // Get all the experienceCandidatList where etab equals to UPDATED_ETAB
        defaultExperienceCandidatShouldNotBeFound("etab.in=" + UPDATED_ETAB);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByEtabIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where etab is not null
        defaultExperienceCandidatShouldBeFound("etab.specified=true");

        // Get all the experienceCandidatList where etab is null
        defaultExperienceCandidatShouldNotBeFound("etab.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByAdressEtabIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where adressEtab equals to DEFAULT_ADRESS_ETAB
        defaultExperienceCandidatShouldBeFound("adressEtab.equals=" + DEFAULT_ADRESS_ETAB);

        // Get all the experienceCandidatList where adressEtab equals to UPDATED_ADRESS_ETAB
        defaultExperienceCandidatShouldNotBeFound("adressEtab.equals=" + UPDATED_ADRESS_ETAB);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByAdressEtabIsInShouldWork() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where adressEtab in DEFAULT_ADRESS_ETAB or UPDATED_ADRESS_ETAB
        defaultExperienceCandidatShouldBeFound("adressEtab.in=" + DEFAULT_ADRESS_ETAB + "," + UPDATED_ADRESS_ETAB);

        // Get all the experienceCandidatList where adressEtab equals to UPDATED_ADRESS_ETAB
        defaultExperienceCandidatShouldNotBeFound("adressEtab.in=" + UPDATED_ADRESS_ETAB);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByAdressEtabIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where adressEtab is not null
        defaultExperienceCandidatShouldBeFound("adressEtab.specified=true");

        // Get all the experienceCandidatList where adressEtab is null
        defaultExperienceCandidatShouldNotBeFound("adressEtab.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByDateDebIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where dateDeb equals to DEFAULT_DATE_DEB
        defaultExperienceCandidatShouldBeFound("dateDeb.equals=" + DEFAULT_DATE_DEB);

        // Get all the experienceCandidatList where dateDeb equals to UPDATED_DATE_DEB
        defaultExperienceCandidatShouldNotBeFound("dateDeb.equals=" + UPDATED_DATE_DEB);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByDateDebIsInShouldWork() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where dateDeb in DEFAULT_DATE_DEB or UPDATED_DATE_DEB
        defaultExperienceCandidatShouldBeFound("dateDeb.in=" + DEFAULT_DATE_DEB + "," + UPDATED_DATE_DEB);

        // Get all the experienceCandidatList where dateDeb equals to UPDATED_DATE_DEB
        defaultExperienceCandidatShouldNotBeFound("dateDeb.in=" + UPDATED_DATE_DEB);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByDateDebIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where dateDeb is not null
        defaultExperienceCandidatShouldBeFound("dateDeb.specified=true");

        // Get all the experienceCandidatList where dateDeb is null
        defaultExperienceCandidatShouldNotBeFound("dateDeb.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByDateDebIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where dateDeb greater than or equals to DEFAULT_DATE_DEB
        defaultExperienceCandidatShouldBeFound("dateDeb.greaterOrEqualThan=" + DEFAULT_DATE_DEB);

        // Get all the experienceCandidatList where dateDeb greater than or equals to UPDATED_DATE_DEB
        defaultExperienceCandidatShouldNotBeFound("dateDeb.greaterOrEqualThan=" + UPDATED_DATE_DEB);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByDateDebIsLessThanSomething() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where dateDeb less than or equals to DEFAULT_DATE_DEB
        defaultExperienceCandidatShouldNotBeFound("dateDeb.lessThan=" + DEFAULT_DATE_DEB);

        // Get all the experienceCandidatList where dateDeb less than or equals to UPDATED_DATE_DEB
        defaultExperienceCandidatShouldBeFound("dateDeb.lessThan=" + UPDATED_DATE_DEB);
    }


    @Test
    @Transactional
    public void getAllExperienceCandidatsByDateFinIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where dateFin equals to DEFAULT_DATE_FIN
        defaultExperienceCandidatShouldBeFound("dateFin.equals=" + DEFAULT_DATE_FIN);

        // Get all the experienceCandidatList where dateFin equals to UPDATED_DATE_FIN
        defaultExperienceCandidatShouldNotBeFound("dateFin.equals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByDateFinIsInShouldWork() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where dateFin in DEFAULT_DATE_FIN or UPDATED_DATE_FIN
        defaultExperienceCandidatShouldBeFound("dateFin.in=" + DEFAULT_DATE_FIN + "," + UPDATED_DATE_FIN);

        // Get all the experienceCandidatList where dateFin equals to UPDATED_DATE_FIN
        defaultExperienceCandidatShouldNotBeFound("dateFin.in=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByDateFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where dateFin is not null
        defaultExperienceCandidatShouldBeFound("dateFin.specified=true");

        // Get all the experienceCandidatList where dateFin is null
        defaultExperienceCandidatShouldNotBeFound("dateFin.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByDateFinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where dateFin greater than or equals to DEFAULT_DATE_FIN
        defaultExperienceCandidatShouldBeFound("dateFin.greaterOrEqualThan=" + DEFAULT_DATE_FIN);

        // Get all the experienceCandidatList where dateFin greater than or equals to UPDATED_DATE_FIN
        defaultExperienceCandidatShouldNotBeFound("dateFin.greaterOrEqualThan=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllExperienceCandidatsByDateFinIsLessThanSomething() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);

        // Get all the experienceCandidatList where dateFin less than or equals to DEFAULT_DATE_FIN
        defaultExperienceCandidatShouldNotBeFound("dateFin.lessThan=" + DEFAULT_DATE_FIN);

        // Get all the experienceCandidatList where dateFin less than or equals to UPDATED_DATE_FIN
        defaultExperienceCandidatShouldBeFound("dateFin.lessThan=" + UPDATED_DATE_FIN);
    }


    @Test
    @Transactional
    public void getAllExperienceCandidatsByCandidatIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidat candidat = CandidatResourceIntTest.createEntity(em);
        em.persist(candidat);
        em.flush();
        experienceCandidat.setCandidat(candidat);
        experienceCandidatRepository.saveAndFlush(experienceCandidat);
        Long candidatId = candidat.getId();

        // Get all the experienceCandidatList where candidat equals to candidatId
        defaultExperienceCandidatShouldBeFound("candidatId.equals=" + candidatId);

        // Get all the experienceCandidatList where candidat equals to candidatId + 1
        defaultExperienceCandidatShouldNotBeFound("candidatId.equals=" + (candidatId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultExperienceCandidatShouldBeFound(String filter) throws Exception {
        restExperienceCandidatMockMvc.perform(get("/api/experience-candidats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experienceCandidat.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeInfo").value(hasItem(DEFAULT_TYPE_INFO.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].etab").value(hasItem(DEFAULT_ETAB.toString())))
            .andExpect(jsonPath("$.[*].adressEtab").value(hasItem(DEFAULT_ADRESS_ETAB.toString())))
            .andExpect(jsonPath("$.[*].dateDeb").value(hasItem(DEFAULT_DATE_DEB.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultExperienceCandidatShouldNotBeFound(String filter) throws Exception {
        restExperienceCandidatMockMvc.perform(get("/api/experience-candidats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingExperienceCandidat() throws Exception {
        // Get the experienceCandidat
        restExperienceCandidatMockMvc.perform(get("/api/experience-candidats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperienceCandidat() throws Exception {
        // Initialize the database
        experienceCandidatService.save(experienceCandidat);

        int databaseSizeBeforeUpdate = experienceCandidatRepository.findAll().size();

        // Update the experienceCandidat
        ExperienceCandidat updatedExperienceCandidat = experienceCandidatRepository.findOne(experienceCandidat.getId());
        // Disconnect from session so that the updates on updatedExperienceCandidat are not directly saved in db
        em.detach(updatedExperienceCandidat);
        updatedExperienceCandidat
            .typeInfo(UPDATED_TYPE_INFO)
            .titre(UPDATED_TITRE)
            .etab(UPDATED_ETAB)
            .adressEtab(UPDATED_ADRESS_ETAB)
            .dateDeb(UPDATED_DATE_DEB)
            .dateFin(UPDATED_DATE_FIN);

        restExperienceCandidatMockMvc.perform(put("/api/experience-candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExperienceCandidat)))
            .andExpect(status().isOk());

        // Validate the ExperienceCandidat in the database
        List<ExperienceCandidat> experienceCandidatList = experienceCandidatRepository.findAll();
        assertThat(experienceCandidatList).hasSize(databaseSizeBeforeUpdate);
        ExperienceCandidat testExperienceCandidat = experienceCandidatList.get(experienceCandidatList.size() - 1);
        assertThat(testExperienceCandidat.getTypeInfo()).isEqualTo(UPDATED_TYPE_INFO);
        assertThat(testExperienceCandidat.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testExperienceCandidat.getEtab()).isEqualTo(UPDATED_ETAB);
        assertThat(testExperienceCandidat.getAdressEtab()).isEqualTo(UPDATED_ADRESS_ETAB);
        assertThat(testExperienceCandidat.getDateDeb()).isEqualTo(UPDATED_DATE_DEB);
        assertThat(testExperienceCandidat.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingExperienceCandidat() throws Exception {
        int databaseSizeBeforeUpdate = experienceCandidatRepository.findAll().size();

        // Create the ExperienceCandidat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExperienceCandidatMockMvc.perform(put("/api/experience-candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceCandidat)))
            .andExpect(status().isCreated());

        // Validate the ExperienceCandidat in the database
        List<ExperienceCandidat> experienceCandidatList = experienceCandidatRepository.findAll();
        assertThat(experienceCandidatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExperienceCandidat() throws Exception {
        // Initialize the database
        experienceCandidatService.save(experienceCandidat);

        int databaseSizeBeforeDelete = experienceCandidatRepository.findAll().size();

        // Get the experienceCandidat
        restExperienceCandidatMockMvc.perform(delete("/api/experience-candidats/{id}", experienceCandidat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExperienceCandidat> experienceCandidatList = experienceCandidatRepository.findAll();
        assertThat(experienceCandidatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExperienceCandidat.class);
        ExperienceCandidat experienceCandidat1 = new ExperienceCandidat();
        experienceCandidat1.setId(1L);
        ExperienceCandidat experienceCandidat2 = new ExperienceCandidat();
        experienceCandidat2.setId(experienceCandidat1.getId());
        assertThat(experienceCandidat1).isEqualTo(experienceCandidat2);
        experienceCandidat2.setId(2L);
        assertThat(experienceCandidat1).isNotEqualTo(experienceCandidat2);
        experienceCandidat1.setId(null);
        assertThat(experienceCandidat1).isNotEqualTo(experienceCandidat2);
    }
}
