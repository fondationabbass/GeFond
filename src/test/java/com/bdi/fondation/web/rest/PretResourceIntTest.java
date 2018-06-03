package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.domain.Client;
import com.bdi.fondation.repository.PretRepository;
import com.bdi.fondation.service.PretService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.PretCriteria;
import com.bdi.fondation.service.PretQueryService;

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
 * Test class for the PretResource REST controller.
 *
 * @see PretResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class PretResourceIntTest {

    private static final String DEFAULT_TYP_PRET = "AAAAAAAAAA";
    private static final String UPDATED_TYP_PRET = "BBBBBBBBBB";

    private static final Double DEFAULT_MONT_AACCORD = 1D;
    private static final Double UPDATED_MONT_AACCORD = 2D;

    private static final Double DEFAULT_MONT_DEBLOQ = 1D;
    private static final Double UPDATED_MONT_DEBLOQ = 2D;

    private static final Integer DEFAULT_NBR_ECHEANCE = 1;
    private static final Integer UPDATED_NBR_ECHEANCE = 2;

    private static final String DEFAULT_PERIODICITE = "AAAAAAAAAA";
    private static final String UPDATED_PERIODICITE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_MISE_PLACE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MISE_PLACE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_PREMIERE_ECHEANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PREMIERE_ECHEANCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DERNIERE_ECHEANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DERNIERE_ECHEANCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DERNIER_DEBLOQ = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DERNIER_DEBLOQ = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final Double DEFAULT_ENCOURS = 1D;
    private static final Double UPDATED_ENCOURS = 2D;

    private static final String DEFAULT_USER_INITIAL = "AAAAAAAAAA";
    private static final String UPDATED_USER_INITIAL = "BBBBBBBBBB";

    private static final String DEFAULT_USER_DECIDEUR = "AAAAAAAAAA";
    private static final String UPDATED_USER_DECIDEUR = "BBBBBBBBBB";

    private static final String DEFAULT_USER_DEBLOQ = "AAAAAAAAAA";
    private static final String UPDATED_USER_DEBLOQ = "BBBBBBBBBB";

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private PretService pretService;

    @Autowired
    private PretQueryService pretQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPretMockMvc;

    private Pret pret;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PretResource pretResource = new PretResource(pretService, pretQueryService);
        this.restPretMockMvc = MockMvcBuilders.standaloneSetup(pretResource)
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
    public static Pret createEntity(EntityManager em) {
        Pret pret = new Pret()
            .typPret(DEFAULT_TYP_PRET)
            .montAaccord(DEFAULT_MONT_AACCORD)
            .montDebloq(DEFAULT_MONT_DEBLOQ)
            .nbrEcheance(DEFAULT_NBR_ECHEANCE)
            .periodicite(DEFAULT_PERIODICITE)
            .dateMisePlace(DEFAULT_DATE_MISE_PLACE)
            .datePremiereEcheance(DEFAULT_DATE_PREMIERE_ECHEANCE)
            .dateDerniereEcheance(DEFAULT_DATE_DERNIERE_ECHEANCE)
            .dateDernierDebloq(DEFAULT_DATE_DERNIER_DEBLOQ)
            .etat(DEFAULT_ETAT)
            .encours(DEFAULT_ENCOURS)
            .userInitial(DEFAULT_USER_INITIAL)
            .userDecideur(DEFAULT_USER_DECIDEUR)
            .userDebloq(DEFAULT_USER_DEBLOQ);
        return pret;
    }

    @Before
    public void initTest() {
        pret = createEntity(em);
    }

    @Test
    @Transactional
    public void createPret() throws Exception {
        int databaseSizeBeforeCreate = pretRepository.findAll().size();

        // Create the Pret
        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isCreated());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeCreate + 1);
        Pret testPret = pretList.get(pretList.size() - 1);
        assertThat(testPret.getTypPret()).isEqualTo(DEFAULT_TYP_PRET);
        assertThat(testPret.getMontAaccord()).isEqualTo(DEFAULT_MONT_AACCORD);
        assertThat(testPret.getMontDebloq()).isEqualTo(DEFAULT_MONT_DEBLOQ);
        assertThat(testPret.getNbrEcheance()).isEqualTo(DEFAULT_NBR_ECHEANCE);
        assertThat(testPret.getPeriodicite()).isEqualTo(DEFAULT_PERIODICITE);
        assertThat(testPret.getDateMisePlace()).isEqualTo(DEFAULT_DATE_MISE_PLACE);
        assertThat(testPret.getDatePremiereEcheance()).isEqualTo(DEFAULT_DATE_PREMIERE_ECHEANCE);
        assertThat(testPret.getDateDerniereEcheance()).isEqualTo(DEFAULT_DATE_DERNIERE_ECHEANCE);
        assertThat(testPret.getDateDernierDebloq()).isEqualTo(DEFAULT_DATE_DERNIER_DEBLOQ);
        assertThat(testPret.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testPret.getEncours()).isEqualTo(DEFAULT_ENCOURS);
        assertThat(testPret.getUserInitial()).isEqualTo(DEFAULT_USER_INITIAL);
        assertThat(testPret.getUserDecideur()).isEqualTo(DEFAULT_USER_DECIDEUR);
        assertThat(testPret.getUserDebloq()).isEqualTo(DEFAULT_USER_DEBLOQ);
    }

    @Test
    @Transactional
    public void createPretWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pretRepository.findAll().size();

        // Create the Pret with an existing ID
        pret.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrets() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList
        restPretMockMvc.perform(get("/api/prets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pret.getId().intValue())))
            .andExpect(jsonPath("$.[*].typPret").value(hasItem(DEFAULT_TYP_PRET.toString())))
            .andExpect(jsonPath("$.[*].montAaccord").value(hasItem(DEFAULT_MONT_AACCORD.doubleValue())))
            .andExpect(jsonPath("$.[*].montDebloq").value(hasItem(DEFAULT_MONT_DEBLOQ.doubleValue())))
            .andExpect(jsonPath("$.[*].nbrEcheance").value(hasItem(DEFAULT_NBR_ECHEANCE)))
            .andExpect(jsonPath("$.[*].periodicite").value(hasItem(DEFAULT_PERIODICITE.toString())))
            .andExpect(jsonPath("$.[*].dateMisePlace").value(hasItem(DEFAULT_DATE_MISE_PLACE.toString())))
            .andExpect(jsonPath("$.[*].datePremiereEcheance").value(hasItem(DEFAULT_DATE_PREMIERE_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].dateDerniereEcheance").value(hasItem(DEFAULT_DATE_DERNIERE_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].dateDernierDebloq").value(hasItem(DEFAULT_DATE_DERNIER_DEBLOQ.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].encours").value(hasItem(DEFAULT_ENCOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].userInitial").value(hasItem(DEFAULT_USER_INITIAL.toString())))
            .andExpect(jsonPath("$.[*].userDecideur").value(hasItem(DEFAULT_USER_DECIDEUR.toString())))
            .andExpect(jsonPath("$.[*].userDebloq").value(hasItem(DEFAULT_USER_DEBLOQ.toString())));
    }

    @Test
    @Transactional
    public void getPret() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get the pret
        restPretMockMvc.perform(get("/api/prets/{id}", pret.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pret.getId().intValue()))
            .andExpect(jsonPath("$.typPret").value(DEFAULT_TYP_PRET.toString()))
            .andExpect(jsonPath("$.montAaccord").value(DEFAULT_MONT_AACCORD.doubleValue()))
            .andExpect(jsonPath("$.montDebloq").value(DEFAULT_MONT_DEBLOQ.doubleValue()))
            .andExpect(jsonPath("$.nbrEcheance").value(DEFAULT_NBR_ECHEANCE))
            .andExpect(jsonPath("$.periodicite").value(DEFAULT_PERIODICITE.toString()))
            .andExpect(jsonPath("$.dateMisePlace").value(DEFAULT_DATE_MISE_PLACE.toString()))
            .andExpect(jsonPath("$.datePremiereEcheance").value(DEFAULT_DATE_PREMIERE_ECHEANCE.toString()))
            .andExpect(jsonPath("$.dateDerniereEcheance").value(DEFAULT_DATE_DERNIERE_ECHEANCE.toString()))
            .andExpect(jsonPath("$.dateDernierDebloq").value(DEFAULT_DATE_DERNIER_DEBLOQ.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.encours").value(DEFAULT_ENCOURS.doubleValue()))
            .andExpect(jsonPath("$.userInitial").value(DEFAULT_USER_INITIAL.toString()))
            .andExpect(jsonPath("$.userDecideur").value(DEFAULT_USER_DECIDEUR.toString()))
            .andExpect(jsonPath("$.userDebloq").value(DEFAULT_USER_DEBLOQ.toString()));
    }

    @Test
    @Transactional
    public void getAllPretsByTypPretIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where typPret equals to DEFAULT_TYP_PRET
        defaultPretShouldBeFound("typPret.equals=" + DEFAULT_TYP_PRET);

        // Get all the pretList where typPret equals to UPDATED_TYP_PRET
        defaultPretShouldNotBeFound("typPret.equals=" + UPDATED_TYP_PRET);
    }

    @Test
    @Transactional
    public void getAllPretsByTypPretIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where typPret in DEFAULT_TYP_PRET or UPDATED_TYP_PRET
        defaultPretShouldBeFound("typPret.in=" + DEFAULT_TYP_PRET + "," + UPDATED_TYP_PRET);

        // Get all the pretList where typPret equals to UPDATED_TYP_PRET
        defaultPretShouldNotBeFound("typPret.in=" + UPDATED_TYP_PRET);
    }

    @Test
    @Transactional
    public void getAllPretsByTypPretIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where typPret is not null
        defaultPretShouldBeFound("typPret.specified=true");

        // Get all the pretList where typPret is null
        defaultPretShouldNotBeFound("typPret.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByMontAaccordIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where montAaccord equals to DEFAULT_MONT_AACCORD
        defaultPretShouldBeFound("montAaccord.equals=" + DEFAULT_MONT_AACCORD);

        // Get all the pretList where montAaccord equals to UPDATED_MONT_AACCORD
        defaultPretShouldNotBeFound("montAaccord.equals=" + UPDATED_MONT_AACCORD);
    }

    @Test
    @Transactional
    public void getAllPretsByMontAaccordIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where montAaccord in DEFAULT_MONT_AACCORD or UPDATED_MONT_AACCORD
        defaultPretShouldBeFound("montAaccord.in=" + DEFAULT_MONT_AACCORD + "," + UPDATED_MONT_AACCORD);

        // Get all the pretList where montAaccord equals to UPDATED_MONT_AACCORD
        defaultPretShouldNotBeFound("montAaccord.in=" + UPDATED_MONT_AACCORD);
    }

    @Test
    @Transactional
    public void getAllPretsByMontAaccordIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where montAaccord is not null
        defaultPretShouldBeFound("montAaccord.specified=true");

        // Get all the pretList where montAaccord is null
        defaultPretShouldNotBeFound("montAaccord.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByMontDebloqIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where montDebloq equals to DEFAULT_MONT_DEBLOQ
        defaultPretShouldBeFound("montDebloq.equals=" + DEFAULT_MONT_DEBLOQ);

        // Get all the pretList where montDebloq equals to UPDATED_MONT_DEBLOQ
        defaultPretShouldNotBeFound("montDebloq.equals=" + UPDATED_MONT_DEBLOQ);
    }

    @Test
    @Transactional
    public void getAllPretsByMontDebloqIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where montDebloq in DEFAULT_MONT_DEBLOQ or UPDATED_MONT_DEBLOQ
        defaultPretShouldBeFound("montDebloq.in=" + DEFAULT_MONT_DEBLOQ + "," + UPDATED_MONT_DEBLOQ);

        // Get all the pretList where montDebloq equals to UPDATED_MONT_DEBLOQ
        defaultPretShouldNotBeFound("montDebloq.in=" + UPDATED_MONT_DEBLOQ);
    }

    @Test
    @Transactional
    public void getAllPretsByMontDebloqIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where montDebloq is not null
        defaultPretShouldBeFound("montDebloq.specified=true");

        // Get all the pretList where montDebloq is null
        defaultPretShouldNotBeFound("montDebloq.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByNbrEcheanceIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where nbrEcheance equals to DEFAULT_NBR_ECHEANCE
        defaultPretShouldBeFound("nbrEcheance.equals=" + DEFAULT_NBR_ECHEANCE);

        // Get all the pretList where nbrEcheance equals to UPDATED_NBR_ECHEANCE
        defaultPretShouldNotBeFound("nbrEcheance.equals=" + UPDATED_NBR_ECHEANCE);
    }

    @Test
    @Transactional
    public void getAllPretsByNbrEcheanceIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where nbrEcheance in DEFAULT_NBR_ECHEANCE or UPDATED_NBR_ECHEANCE
        defaultPretShouldBeFound("nbrEcheance.in=" + DEFAULT_NBR_ECHEANCE + "," + UPDATED_NBR_ECHEANCE);

        // Get all the pretList where nbrEcheance equals to UPDATED_NBR_ECHEANCE
        defaultPretShouldNotBeFound("nbrEcheance.in=" + UPDATED_NBR_ECHEANCE);
    }

    @Test
    @Transactional
    public void getAllPretsByNbrEcheanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where nbrEcheance is not null
        defaultPretShouldBeFound("nbrEcheance.specified=true");

        // Get all the pretList where nbrEcheance is null
        defaultPretShouldNotBeFound("nbrEcheance.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByNbrEcheanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where nbrEcheance greater than or equals to DEFAULT_NBR_ECHEANCE
        defaultPretShouldBeFound("nbrEcheance.greaterOrEqualThan=" + DEFAULT_NBR_ECHEANCE);

        // Get all the pretList where nbrEcheance greater than or equals to UPDATED_NBR_ECHEANCE
        defaultPretShouldNotBeFound("nbrEcheance.greaterOrEqualThan=" + UPDATED_NBR_ECHEANCE);
    }

    @Test
    @Transactional
    public void getAllPretsByNbrEcheanceIsLessThanSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where nbrEcheance less than or equals to DEFAULT_NBR_ECHEANCE
        defaultPretShouldNotBeFound("nbrEcheance.lessThan=" + DEFAULT_NBR_ECHEANCE);

        // Get all the pretList where nbrEcheance less than or equals to UPDATED_NBR_ECHEANCE
        defaultPretShouldBeFound("nbrEcheance.lessThan=" + UPDATED_NBR_ECHEANCE);
    }


    @Test
    @Transactional
    public void getAllPretsByPeriodiciteIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where periodicite equals to DEFAULT_PERIODICITE
        defaultPretShouldBeFound("periodicite.equals=" + DEFAULT_PERIODICITE);

        // Get all the pretList where periodicite equals to UPDATED_PERIODICITE
        defaultPretShouldNotBeFound("periodicite.equals=" + UPDATED_PERIODICITE);
    }

    @Test
    @Transactional
    public void getAllPretsByPeriodiciteIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where periodicite in DEFAULT_PERIODICITE or UPDATED_PERIODICITE
        defaultPretShouldBeFound("periodicite.in=" + DEFAULT_PERIODICITE + "," + UPDATED_PERIODICITE);

        // Get all the pretList where periodicite equals to UPDATED_PERIODICITE
        defaultPretShouldNotBeFound("periodicite.in=" + UPDATED_PERIODICITE);
    }

    @Test
    @Transactional
    public void getAllPretsByPeriodiciteIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where periodicite is not null
        defaultPretShouldBeFound("periodicite.specified=true");

        // Get all the pretList where periodicite is null
        defaultPretShouldNotBeFound("periodicite.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByDateMisePlaceIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateMisePlace equals to DEFAULT_DATE_MISE_PLACE
        defaultPretShouldBeFound("dateMisePlace.equals=" + DEFAULT_DATE_MISE_PLACE);

        // Get all the pretList where dateMisePlace equals to UPDATED_DATE_MISE_PLACE
        defaultPretShouldNotBeFound("dateMisePlace.equals=" + UPDATED_DATE_MISE_PLACE);
    }

    @Test
    @Transactional
    public void getAllPretsByDateMisePlaceIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateMisePlace in DEFAULT_DATE_MISE_PLACE or UPDATED_DATE_MISE_PLACE
        defaultPretShouldBeFound("dateMisePlace.in=" + DEFAULT_DATE_MISE_PLACE + "," + UPDATED_DATE_MISE_PLACE);

        // Get all the pretList where dateMisePlace equals to UPDATED_DATE_MISE_PLACE
        defaultPretShouldNotBeFound("dateMisePlace.in=" + UPDATED_DATE_MISE_PLACE);
    }

    @Test
    @Transactional
    public void getAllPretsByDateMisePlaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateMisePlace is not null
        defaultPretShouldBeFound("dateMisePlace.specified=true");

        // Get all the pretList where dateMisePlace is null
        defaultPretShouldNotBeFound("dateMisePlace.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByDateMisePlaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateMisePlace greater than or equals to DEFAULT_DATE_MISE_PLACE
        defaultPretShouldBeFound("dateMisePlace.greaterOrEqualThan=" + DEFAULT_DATE_MISE_PLACE);

        // Get all the pretList where dateMisePlace greater than or equals to UPDATED_DATE_MISE_PLACE
        defaultPretShouldNotBeFound("dateMisePlace.greaterOrEqualThan=" + UPDATED_DATE_MISE_PLACE);
    }

    @Test
    @Transactional
    public void getAllPretsByDateMisePlaceIsLessThanSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateMisePlace less than or equals to DEFAULT_DATE_MISE_PLACE
        defaultPretShouldNotBeFound("dateMisePlace.lessThan=" + DEFAULT_DATE_MISE_PLACE);

        // Get all the pretList where dateMisePlace less than or equals to UPDATED_DATE_MISE_PLACE
        defaultPretShouldBeFound("dateMisePlace.lessThan=" + UPDATED_DATE_MISE_PLACE);
    }


    @Test
    @Transactional
    public void getAllPretsByDatePremiereEcheanceIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where datePremiereEcheance equals to DEFAULT_DATE_PREMIERE_ECHEANCE
        defaultPretShouldBeFound("datePremiereEcheance.equals=" + DEFAULT_DATE_PREMIERE_ECHEANCE);

        // Get all the pretList where datePremiereEcheance equals to UPDATED_DATE_PREMIERE_ECHEANCE
        defaultPretShouldNotBeFound("datePremiereEcheance.equals=" + UPDATED_DATE_PREMIERE_ECHEANCE);
    }

    @Test
    @Transactional
    public void getAllPretsByDatePremiereEcheanceIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where datePremiereEcheance in DEFAULT_DATE_PREMIERE_ECHEANCE or UPDATED_DATE_PREMIERE_ECHEANCE
        defaultPretShouldBeFound("datePremiereEcheance.in=" + DEFAULT_DATE_PREMIERE_ECHEANCE + "," + UPDATED_DATE_PREMIERE_ECHEANCE);

        // Get all the pretList where datePremiereEcheance equals to UPDATED_DATE_PREMIERE_ECHEANCE
        defaultPretShouldNotBeFound("datePremiereEcheance.in=" + UPDATED_DATE_PREMIERE_ECHEANCE);
    }

    @Test
    @Transactional
    public void getAllPretsByDatePremiereEcheanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where datePremiereEcheance is not null
        defaultPretShouldBeFound("datePremiereEcheance.specified=true");

        // Get all the pretList where datePremiereEcheance is null
        defaultPretShouldNotBeFound("datePremiereEcheance.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByDatePremiereEcheanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where datePremiereEcheance greater than or equals to DEFAULT_DATE_PREMIERE_ECHEANCE
        defaultPretShouldBeFound("datePremiereEcheance.greaterOrEqualThan=" + DEFAULT_DATE_PREMIERE_ECHEANCE);

        // Get all the pretList where datePremiereEcheance greater than or equals to UPDATED_DATE_PREMIERE_ECHEANCE
        defaultPretShouldNotBeFound("datePremiereEcheance.greaterOrEqualThan=" + UPDATED_DATE_PREMIERE_ECHEANCE);
    }

    @Test
    @Transactional
    public void getAllPretsByDatePremiereEcheanceIsLessThanSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where datePremiereEcheance less than or equals to DEFAULT_DATE_PREMIERE_ECHEANCE
        defaultPretShouldNotBeFound("datePremiereEcheance.lessThan=" + DEFAULT_DATE_PREMIERE_ECHEANCE);

        // Get all the pretList where datePremiereEcheance less than or equals to UPDATED_DATE_PREMIERE_ECHEANCE
        defaultPretShouldBeFound("datePremiereEcheance.lessThan=" + UPDATED_DATE_PREMIERE_ECHEANCE);
    }


    @Test
    @Transactional
    public void getAllPretsByDateDerniereEcheanceIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateDerniereEcheance equals to DEFAULT_DATE_DERNIERE_ECHEANCE
        defaultPretShouldBeFound("dateDerniereEcheance.equals=" + DEFAULT_DATE_DERNIERE_ECHEANCE);

        // Get all the pretList where dateDerniereEcheance equals to UPDATED_DATE_DERNIERE_ECHEANCE
        defaultPretShouldNotBeFound("dateDerniereEcheance.equals=" + UPDATED_DATE_DERNIERE_ECHEANCE);
    }

    @Test
    @Transactional
    public void getAllPretsByDateDerniereEcheanceIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateDerniereEcheance in DEFAULT_DATE_DERNIERE_ECHEANCE or UPDATED_DATE_DERNIERE_ECHEANCE
        defaultPretShouldBeFound("dateDerniereEcheance.in=" + DEFAULT_DATE_DERNIERE_ECHEANCE + "," + UPDATED_DATE_DERNIERE_ECHEANCE);

        // Get all the pretList where dateDerniereEcheance equals to UPDATED_DATE_DERNIERE_ECHEANCE
        defaultPretShouldNotBeFound("dateDerniereEcheance.in=" + UPDATED_DATE_DERNIERE_ECHEANCE);
    }

    @Test
    @Transactional
    public void getAllPretsByDateDerniereEcheanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateDerniereEcheance is not null
        defaultPretShouldBeFound("dateDerniereEcheance.specified=true");

        // Get all the pretList where dateDerniereEcheance is null
        defaultPretShouldNotBeFound("dateDerniereEcheance.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByDateDerniereEcheanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateDerniereEcheance greater than or equals to DEFAULT_DATE_DERNIERE_ECHEANCE
        defaultPretShouldBeFound("dateDerniereEcheance.greaterOrEqualThan=" + DEFAULT_DATE_DERNIERE_ECHEANCE);

        // Get all the pretList where dateDerniereEcheance greater than or equals to UPDATED_DATE_DERNIERE_ECHEANCE
        defaultPretShouldNotBeFound("dateDerniereEcheance.greaterOrEqualThan=" + UPDATED_DATE_DERNIERE_ECHEANCE);
    }

    @Test
    @Transactional
    public void getAllPretsByDateDerniereEcheanceIsLessThanSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateDerniereEcheance less than or equals to DEFAULT_DATE_DERNIERE_ECHEANCE
        defaultPretShouldNotBeFound("dateDerniereEcheance.lessThan=" + DEFAULT_DATE_DERNIERE_ECHEANCE);

        // Get all the pretList where dateDerniereEcheance less than or equals to UPDATED_DATE_DERNIERE_ECHEANCE
        defaultPretShouldBeFound("dateDerniereEcheance.lessThan=" + UPDATED_DATE_DERNIERE_ECHEANCE);
    }


    @Test
    @Transactional
    public void getAllPretsByDateDernierDebloqIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateDernierDebloq equals to DEFAULT_DATE_DERNIER_DEBLOQ
        defaultPretShouldBeFound("dateDernierDebloq.equals=" + DEFAULT_DATE_DERNIER_DEBLOQ);

        // Get all the pretList where dateDernierDebloq equals to UPDATED_DATE_DERNIER_DEBLOQ
        defaultPretShouldNotBeFound("dateDernierDebloq.equals=" + UPDATED_DATE_DERNIER_DEBLOQ);
    }

    @Test
    @Transactional
    public void getAllPretsByDateDernierDebloqIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateDernierDebloq in DEFAULT_DATE_DERNIER_DEBLOQ or UPDATED_DATE_DERNIER_DEBLOQ
        defaultPretShouldBeFound("dateDernierDebloq.in=" + DEFAULT_DATE_DERNIER_DEBLOQ + "," + UPDATED_DATE_DERNIER_DEBLOQ);

        // Get all the pretList where dateDernierDebloq equals to UPDATED_DATE_DERNIER_DEBLOQ
        defaultPretShouldNotBeFound("dateDernierDebloq.in=" + UPDATED_DATE_DERNIER_DEBLOQ);
    }

    @Test
    @Transactional
    public void getAllPretsByDateDernierDebloqIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateDernierDebloq is not null
        defaultPretShouldBeFound("dateDernierDebloq.specified=true");

        // Get all the pretList where dateDernierDebloq is null
        defaultPretShouldNotBeFound("dateDernierDebloq.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByDateDernierDebloqIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateDernierDebloq greater than or equals to DEFAULT_DATE_DERNIER_DEBLOQ
        defaultPretShouldBeFound("dateDernierDebloq.greaterOrEqualThan=" + DEFAULT_DATE_DERNIER_DEBLOQ);

        // Get all the pretList where dateDernierDebloq greater than or equals to UPDATED_DATE_DERNIER_DEBLOQ
        defaultPretShouldNotBeFound("dateDernierDebloq.greaterOrEqualThan=" + UPDATED_DATE_DERNIER_DEBLOQ);
    }

    @Test
    @Transactional
    public void getAllPretsByDateDernierDebloqIsLessThanSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where dateDernierDebloq less than or equals to DEFAULT_DATE_DERNIER_DEBLOQ
        defaultPretShouldNotBeFound("dateDernierDebloq.lessThan=" + DEFAULT_DATE_DERNIER_DEBLOQ);

        // Get all the pretList where dateDernierDebloq less than or equals to UPDATED_DATE_DERNIER_DEBLOQ
        defaultPretShouldBeFound("dateDernierDebloq.lessThan=" + UPDATED_DATE_DERNIER_DEBLOQ);
    }


    @Test
    @Transactional
    public void getAllPretsByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where etat equals to DEFAULT_ETAT
        defaultPretShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the pretList where etat equals to UPDATED_ETAT
        defaultPretShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllPretsByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultPretShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the pretList where etat equals to UPDATED_ETAT
        defaultPretShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllPretsByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where etat is not null
        defaultPretShouldBeFound("etat.specified=true");

        // Get all the pretList where etat is null
        defaultPretShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByEncoursIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where encours equals to DEFAULT_ENCOURS
        defaultPretShouldBeFound("encours.equals=" + DEFAULT_ENCOURS);

        // Get all the pretList where encours equals to UPDATED_ENCOURS
        defaultPretShouldNotBeFound("encours.equals=" + UPDATED_ENCOURS);
    }

    @Test
    @Transactional
    public void getAllPretsByEncoursIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where encours in DEFAULT_ENCOURS or UPDATED_ENCOURS
        defaultPretShouldBeFound("encours.in=" + DEFAULT_ENCOURS + "," + UPDATED_ENCOURS);

        // Get all the pretList where encours equals to UPDATED_ENCOURS
        defaultPretShouldNotBeFound("encours.in=" + UPDATED_ENCOURS);
    }

    @Test
    @Transactional
    public void getAllPretsByEncoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where encours is not null
        defaultPretShouldBeFound("encours.specified=true");

        // Get all the pretList where encours is null
        defaultPretShouldNotBeFound("encours.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByUserInitialIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where userInitial equals to DEFAULT_USER_INITIAL
        defaultPretShouldBeFound("userInitial.equals=" + DEFAULT_USER_INITIAL);

        // Get all the pretList where userInitial equals to UPDATED_USER_INITIAL
        defaultPretShouldNotBeFound("userInitial.equals=" + UPDATED_USER_INITIAL);
    }

    @Test
    @Transactional
    public void getAllPretsByUserInitialIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where userInitial in DEFAULT_USER_INITIAL or UPDATED_USER_INITIAL
        defaultPretShouldBeFound("userInitial.in=" + DEFAULT_USER_INITIAL + "," + UPDATED_USER_INITIAL);

        // Get all the pretList where userInitial equals to UPDATED_USER_INITIAL
        defaultPretShouldNotBeFound("userInitial.in=" + UPDATED_USER_INITIAL);
    }

    @Test
    @Transactional
    public void getAllPretsByUserInitialIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where userInitial is not null
        defaultPretShouldBeFound("userInitial.specified=true");

        // Get all the pretList where userInitial is null
        defaultPretShouldNotBeFound("userInitial.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByUserDecideurIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where userDecideur equals to DEFAULT_USER_DECIDEUR
        defaultPretShouldBeFound("userDecideur.equals=" + DEFAULT_USER_DECIDEUR);

        // Get all the pretList where userDecideur equals to UPDATED_USER_DECIDEUR
        defaultPretShouldNotBeFound("userDecideur.equals=" + UPDATED_USER_DECIDEUR);
    }

    @Test
    @Transactional
    public void getAllPretsByUserDecideurIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where userDecideur in DEFAULT_USER_DECIDEUR or UPDATED_USER_DECIDEUR
        defaultPretShouldBeFound("userDecideur.in=" + DEFAULT_USER_DECIDEUR + "," + UPDATED_USER_DECIDEUR);

        // Get all the pretList where userDecideur equals to UPDATED_USER_DECIDEUR
        defaultPretShouldNotBeFound("userDecideur.in=" + UPDATED_USER_DECIDEUR);
    }

    @Test
    @Transactional
    public void getAllPretsByUserDecideurIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where userDecideur is not null
        defaultPretShouldBeFound("userDecideur.specified=true");

        // Get all the pretList where userDecideur is null
        defaultPretShouldNotBeFound("userDecideur.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByUserDebloqIsEqualToSomething() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where userDebloq equals to DEFAULT_USER_DEBLOQ
        defaultPretShouldBeFound("userDebloq.equals=" + DEFAULT_USER_DEBLOQ);

        // Get all the pretList where userDebloq equals to UPDATED_USER_DEBLOQ
        defaultPretShouldNotBeFound("userDebloq.equals=" + UPDATED_USER_DEBLOQ);
    }

    @Test
    @Transactional
    public void getAllPretsByUserDebloqIsInShouldWork() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where userDebloq in DEFAULT_USER_DEBLOQ or UPDATED_USER_DEBLOQ
        defaultPretShouldBeFound("userDebloq.in=" + DEFAULT_USER_DEBLOQ + "," + UPDATED_USER_DEBLOQ);

        // Get all the pretList where userDebloq equals to UPDATED_USER_DEBLOQ
        defaultPretShouldNotBeFound("userDebloq.in=" + UPDATED_USER_DEBLOQ);
    }

    @Test
    @Transactional
    public void getAllPretsByUserDebloqIsNullOrNotNull() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList where userDebloq is not null
        defaultPretShouldBeFound("userDebloq.specified=true");

        // Get all the pretList where userDebloq is null
        defaultPretShouldNotBeFound("userDebloq.specified=false");
    }

    @Test
    @Transactional
    public void getAllPretsByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        Client client = ClientResourceIntTest.createEntity(em);
        em.persist(client);
        em.flush();
        pret.setClient(client);
        pretRepository.saveAndFlush(pret);
        Long clientId = client.getId();

        // Get all the pretList where client equals to clientId
        defaultPretShouldBeFound("clientId.equals=" + clientId);

        // Get all the pretList where client equals to clientId + 1
        defaultPretShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPretShouldBeFound(String filter) throws Exception {
        restPretMockMvc.perform(get("/api/prets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pret.getId().intValue())))
            .andExpect(jsonPath("$.[*].typPret").value(hasItem(DEFAULT_TYP_PRET.toString())))
            .andExpect(jsonPath("$.[*].montAaccord").value(hasItem(DEFAULT_MONT_AACCORD.doubleValue())))
            .andExpect(jsonPath("$.[*].montDebloq").value(hasItem(DEFAULT_MONT_DEBLOQ.doubleValue())))
            .andExpect(jsonPath("$.[*].nbrEcheance").value(hasItem(DEFAULT_NBR_ECHEANCE)))
            .andExpect(jsonPath("$.[*].periodicite").value(hasItem(DEFAULT_PERIODICITE.toString())))
            .andExpect(jsonPath("$.[*].dateMisePlace").value(hasItem(DEFAULT_DATE_MISE_PLACE.toString())))
            .andExpect(jsonPath("$.[*].datePremiereEcheance").value(hasItem(DEFAULT_DATE_PREMIERE_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].dateDerniereEcheance").value(hasItem(DEFAULT_DATE_DERNIERE_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].dateDernierDebloq").value(hasItem(DEFAULT_DATE_DERNIER_DEBLOQ.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].encours").value(hasItem(DEFAULT_ENCOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].userInitial").value(hasItem(DEFAULT_USER_INITIAL.toString())))
            .andExpect(jsonPath("$.[*].userDecideur").value(hasItem(DEFAULT_USER_DECIDEUR.toString())))
            .andExpect(jsonPath("$.[*].userDebloq").value(hasItem(DEFAULT_USER_DEBLOQ.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPretShouldNotBeFound(String filter) throws Exception {
        restPretMockMvc.perform(get("/api/prets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPret() throws Exception {
        // Get the pret
        restPretMockMvc.perform(get("/api/prets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePret() throws Exception {
        // Initialize the database
        pretService.save(pret);

        int databaseSizeBeforeUpdate = pretRepository.findAll().size();

        // Update the pret
        Pret updatedPret = pretRepository.findOne(pret.getId());
        // Disconnect from session so that the updates on updatedPret are not directly saved in db
        em.detach(updatedPret);
        updatedPret
            .typPret(UPDATED_TYP_PRET)
            .montAaccord(UPDATED_MONT_AACCORD)
            .montDebloq(UPDATED_MONT_DEBLOQ)
            .nbrEcheance(UPDATED_NBR_ECHEANCE)
            .periodicite(UPDATED_PERIODICITE)
            .dateMisePlace(UPDATED_DATE_MISE_PLACE)
            .datePremiereEcheance(UPDATED_DATE_PREMIERE_ECHEANCE)
            .dateDerniereEcheance(UPDATED_DATE_DERNIERE_ECHEANCE)
            .dateDernierDebloq(UPDATED_DATE_DERNIER_DEBLOQ)
            .etat(UPDATED_ETAT)
            .encours(UPDATED_ENCOURS)
            .userInitial(UPDATED_USER_INITIAL)
            .userDecideur(UPDATED_USER_DECIDEUR)
            .userDebloq(UPDATED_USER_DEBLOQ);

        restPretMockMvc.perform(put("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPret)))
            .andExpect(status().isOk());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeUpdate);
        Pret testPret = pretList.get(pretList.size() - 1);
        assertThat(testPret.getTypPret()).isEqualTo(UPDATED_TYP_PRET);
        assertThat(testPret.getMontAaccord()).isEqualTo(UPDATED_MONT_AACCORD);
        assertThat(testPret.getMontDebloq()).isEqualTo(UPDATED_MONT_DEBLOQ);
        assertThat(testPret.getNbrEcheance()).isEqualTo(UPDATED_NBR_ECHEANCE);
        assertThat(testPret.getPeriodicite()).isEqualTo(UPDATED_PERIODICITE);
        assertThat(testPret.getDateMisePlace()).isEqualTo(UPDATED_DATE_MISE_PLACE);
        assertThat(testPret.getDatePremiereEcheance()).isEqualTo(UPDATED_DATE_PREMIERE_ECHEANCE);
        assertThat(testPret.getDateDerniereEcheance()).isEqualTo(UPDATED_DATE_DERNIERE_ECHEANCE);
        assertThat(testPret.getDateDernierDebloq()).isEqualTo(UPDATED_DATE_DERNIER_DEBLOQ);
        assertThat(testPret.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testPret.getEncours()).isEqualTo(UPDATED_ENCOURS);
        assertThat(testPret.getUserInitial()).isEqualTo(UPDATED_USER_INITIAL);
        assertThat(testPret.getUserDecideur()).isEqualTo(UPDATED_USER_DECIDEUR);
        assertThat(testPret.getUserDebloq()).isEqualTo(UPDATED_USER_DEBLOQ);
    }

    @Test
    @Transactional
    public void updateNonExistingPret() throws Exception {
        int databaseSizeBeforeUpdate = pretRepository.findAll().size();

        // Create the Pret

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPretMockMvc.perform(put("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isCreated());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePret() throws Exception {
        // Initialize the database
        pretService.save(pret);

        int databaseSizeBeforeDelete = pretRepository.findAll().size();

        // Get the pret
        restPretMockMvc.perform(delete("/api/prets/{id}", pret.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pret.class);
        Pret pret1 = new Pret();
        pret1.setId(1L);
        Pret pret2 = new Pret();
        pret2.setId(pret1.getId());
        assertThat(pret1).isEqualTo(pret2);
        pret2.setId(2L);
        assertThat(pret1).isNotEqualTo(pret2);
        pret1.setId(null);
        assertThat(pret1).isNotEqualTo(pret2);
    }
}
