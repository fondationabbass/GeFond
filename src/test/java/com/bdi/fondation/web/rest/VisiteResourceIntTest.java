package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Visite;
import com.bdi.fondation.repository.VisiteRepository;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;

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
 * Test class for the VisiteResource REST controller.
 *
 * @see VisiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class VisiteResourceIntTest {

    private static final String DEFAULT_LIEU_VISITE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_VISITE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_VISITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VISITE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PERS_RENCONTRE = 1;
    private static final Integer UPDATED_PERS_RENCONTRE = 2;

    private static final String DEFAULT_CADRE_VISITE = "AAAAAAAAAA";
    private static final String UPDATED_CADRE_VISITE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_ETAT_LIEU = "BBBBBBBBBB";

    private static final String DEFAULT_VISITEUR = "AAAAAAAAAA";
    private static final String UPDATED_VISITEUR = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_RECOMENDATION = "AAAAAAAAAA";
    private static final String UPDATED_RECOMENDATION = "BBBBBBBBBB";

    private static final String DEFAULT_RAPPORT = "AAAAAAAAAA";
    private static final String UPDATED_RAPPORT = "BBBBBBBBBB";

    @Autowired
    private VisiteRepository visiteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisiteMockMvc;

    private Visite visite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VisiteResource visiteResource = new VisiteResource(visiteRepository);
        this.restVisiteMockMvc = MockMvcBuilders.standaloneSetup(visiteResource)
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
    public static Visite createEntity(EntityManager em) {
        Visite visite = new Visite()
            .lieuVisite(DEFAULT_LIEU_VISITE)
            .dateVisite(DEFAULT_DATE_VISITE)
            .persRencontre(DEFAULT_PERS_RENCONTRE)
            .cadreVisite(DEFAULT_CADRE_VISITE)
            .etatLieu(DEFAULT_ETAT_LIEU)
            .visiteur(DEFAULT_VISITEUR)
            .etat(DEFAULT_ETAT)
            .recomendation(DEFAULT_RECOMENDATION)
            .rapport(DEFAULT_RAPPORT);
        return visite;
    }

    @Before
    public void initTest() {
        visite = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisite() throws Exception {
        int databaseSizeBeforeCreate = visiteRepository.findAll().size();

        // Create the Visite
        restVisiteMockMvc.perform(post("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visite)))
            .andExpect(status().isCreated());

        // Validate the Visite in the database
        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeCreate + 1);
        Visite testVisite = visiteList.get(visiteList.size() - 1);
        assertThat(testVisite.getLieuVisite()).isEqualTo(DEFAULT_LIEU_VISITE);
        assertThat(testVisite.getDateVisite()).isEqualTo(DEFAULT_DATE_VISITE);
        assertThat(testVisite.getPersRencontre()).isEqualTo(DEFAULT_PERS_RENCONTRE);
        assertThat(testVisite.getCadreVisite()).isEqualTo(DEFAULT_CADRE_VISITE);
        assertThat(testVisite.getEtatLieu()).isEqualTo(DEFAULT_ETAT_LIEU);
        assertThat(testVisite.getVisiteur()).isEqualTo(DEFAULT_VISITEUR);
        assertThat(testVisite.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testVisite.getRecomendation()).isEqualTo(DEFAULT_RECOMENDATION);
        assertThat(testVisite.getRapport()).isEqualTo(DEFAULT_RAPPORT);
    }

    @Test
    @Transactional
    public void createVisiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visiteRepository.findAll().size();

        // Create the Visite with an existing ID
        visite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisiteMockMvc.perform(post("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visite)))
            .andExpect(status().isBadRequest());

        // Validate the Visite in the database
        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLieuVisiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = visiteRepository.findAll().size();
        // set the field null
        visite.setLieuVisite(null);

        // Create the Visite, which fails.

        restVisiteMockMvc.perform(post("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visite)))
            .andExpect(status().isBadRequest());

        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateVisiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = visiteRepository.findAll().size();
        // set the field null
        visite.setDateVisite(null);

        // Create the Visite, which fails.

        restVisiteMockMvc.perform(post("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visite)))
            .andExpect(status().isBadRequest());

        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPersRencontreIsRequired() throws Exception {
        int databaseSizeBeforeTest = visiteRepository.findAll().size();
        // set the field null
        visite.setPersRencontre(null);

        // Create the Visite, which fails.

        restVisiteMockMvc.perform(post("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visite)))
            .andExpect(status().isBadRequest());

        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCadreVisiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = visiteRepository.findAll().size();
        // set the field null
        visite.setCadreVisite(null);

        // Create the Visite, which fails.

        restVisiteMockMvc.perform(post("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visite)))
            .andExpect(status().isBadRequest());

        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRapportIsRequired() throws Exception {
        int databaseSizeBeforeTest = visiteRepository.findAll().size();
        // set the field null
        visite.setRapport(null);

        // Create the Visite, which fails.

        restVisiteMockMvc.perform(post("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visite)))
            .andExpect(status().isBadRequest());

        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVisites() throws Exception {
        // Initialize the database
        visiteRepository.saveAndFlush(visite);

        // Get all the visiteList
        restVisiteMockMvc.perform(get("/api/visites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visite.getId().intValue())))
            .andExpect(jsonPath("$.[*].lieuVisite").value(hasItem(DEFAULT_LIEU_VISITE.toString())))
            .andExpect(jsonPath("$.[*].dateVisite").value(hasItem(DEFAULT_DATE_VISITE.toString())))
            .andExpect(jsonPath("$.[*].persRencontre").value(hasItem(DEFAULT_PERS_RENCONTRE)))
            .andExpect(jsonPath("$.[*].cadreVisite").value(hasItem(DEFAULT_CADRE_VISITE.toString())))
            .andExpect(jsonPath("$.[*].etatLieu").value(hasItem(DEFAULT_ETAT_LIEU.toString())))
            .andExpect(jsonPath("$.[*].visiteur").value(hasItem(DEFAULT_VISITEUR.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].recomendation").value(hasItem(DEFAULT_RECOMENDATION.toString())))
            .andExpect(jsonPath("$.[*].rapport").value(hasItem(DEFAULT_RAPPORT.toString())));
    }

    @Test
    @Transactional
    public void getVisite() throws Exception {
        // Initialize the database
        visiteRepository.saveAndFlush(visite);

        // Get the visite
        restVisiteMockMvc.perform(get("/api/visites/{id}", visite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visite.getId().intValue()))
            .andExpect(jsonPath("$.lieuVisite").value(DEFAULT_LIEU_VISITE.toString()))
            .andExpect(jsonPath("$.dateVisite").value(DEFAULT_DATE_VISITE.toString()))
            .andExpect(jsonPath("$.persRencontre").value(DEFAULT_PERS_RENCONTRE))
            .andExpect(jsonPath("$.cadreVisite").value(DEFAULT_CADRE_VISITE.toString()))
            .andExpect(jsonPath("$.etatLieu").value(DEFAULT_ETAT_LIEU.toString()))
            .andExpect(jsonPath("$.visiteur").value(DEFAULT_VISITEUR.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.recomendation").value(DEFAULT_RECOMENDATION.toString()))
            .andExpect(jsonPath("$.rapport").value(DEFAULT_RAPPORT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVisite() throws Exception {
        // Get the visite
        restVisiteMockMvc.perform(get("/api/visites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisite() throws Exception {
        // Initialize the database
        visiteRepository.saveAndFlush(visite);
        int databaseSizeBeforeUpdate = visiteRepository.findAll().size();

        // Update the visite
        Visite updatedVisite = visiteRepository.findOne(visite.getId());
        // Disconnect from session so that the updates on updatedVisite are not directly saved in db
        em.detach(updatedVisite);
        updatedVisite
            .lieuVisite(UPDATED_LIEU_VISITE)
            .dateVisite(UPDATED_DATE_VISITE)
            .persRencontre(UPDATED_PERS_RENCONTRE)
            .cadreVisite(UPDATED_CADRE_VISITE)
            .etatLieu(UPDATED_ETAT_LIEU)
            .visiteur(UPDATED_VISITEUR)
            .etat(UPDATED_ETAT)
            .recomendation(UPDATED_RECOMENDATION)
            .rapport(UPDATED_RAPPORT);

        restVisiteMockMvc.perform(put("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisite)))
            .andExpect(status().isOk());

        // Validate the Visite in the database
        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeUpdate);
        Visite testVisite = visiteList.get(visiteList.size() - 1);
        assertThat(testVisite.getLieuVisite()).isEqualTo(UPDATED_LIEU_VISITE);
        assertThat(testVisite.getDateVisite()).isEqualTo(UPDATED_DATE_VISITE);
        assertThat(testVisite.getPersRencontre()).isEqualTo(UPDATED_PERS_RENCONTRE);
        assertThat(testVisite.getCadreVisite()).isEqualTo(UPDATED_CADRE_VISITE);
        assertThat(testVisite.getEtatLieu()).isEqualTo(UPDATED_ETAT_LIEU);
        assertThat(testVisite.getVisiteur()).isEqualTo(UPDATED_VISITEUR);
        assertThat(testVisite.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testVisite.getRecomendation()).isEqualTo(UPDATED_RECOMENDATION);
        assertThat(testVisite.getRapport()).isEqualTo(UPDATED_RAPPORT);
    }

    @Test
    @Transactional
    public void updateNonExistingVisite() throws Exception {
        int databaseSizeBeforeUpdate = visiteRepository.findAll().size();

        // Create the Visite

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVisiteMockMvc.perform(put("/api/visites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visite)))
            .andExpect(status().isCreated());

        // Validate the Visite in the database
        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVisite() throws Exception {
        // Initialize the database
        visiteRepository.saveAndFlush(visite);
        int databaseSizeBeforeDelete = visiteRepository.findAll().size();

        // Get the visite
        restVisiteMockMvc.perform(delete("/api/visites/{id}", visite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Visite> visiteList = visiteRepository.findAll();
        assertThat(visiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visite.class);
        Visite visite1 = new Visite();
        visite1.setId(1L);
        Visite visite2 = new Visite();
        visite2.setId(visite1.getId());
        assertThat(visite1).isEqualTo(visite2);
        visite2.setId(2L);
        assertThat(visite1).isNotEqualTo(visite2);
        visite1.setId(null);
        assertThat(visite1).isNotEqualTo(visite2);
    }
}
