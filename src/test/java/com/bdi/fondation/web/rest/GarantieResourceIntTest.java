package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Garantie;
import com.bdi.fondation.repository.GarantieRepository;
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
        final GarantieResource garantieResource = new GarantieResource(garantieRepository);
        this.restGarantieMockMvc = MockMvcBuilders.standaloneSetup(garantieResource)
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
            .montantEvalue(DEFAULT_MONTANT_EVALUE)
            .montantAfect(DEFAULT_MONTANT_AFECT)
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
        assertThat(testGarantie.getMontantEvalue()).isEqualTo(DEFAULT_MONTANT_EVALUE);
        assertThat(testGarantie.getMontantAfect()).isEqualTo(DEFAULT_MONTANT_AFECT);
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
    public void checkMontantEvalueIsRequired() throws Exception {
        int databaseSizeBeforeTest = garantieRepository.findAll().size();
        // set the field null
        garantie.setMontantEvalue(null);

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
    public void checkMontantAfectIsRequired() throws Exception {
        int databaseSizeBeforeTest = garantieRepository.findAll().size();
        // set the field null
        garantie.setMontantAfect(null);

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
    public void getNonExistingGarantie() throws Exception {
        // Get the garantie
        restGarantieMockMvc.perform(get("/api/garanties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGarantie() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);
        int databaseSizeBeforeUpdate = garantieRepository.findAll().size();

        // Update the garantie
        Garantie updatedGarantie = garantieRepository.findOne(garantie.getId());
        // Disconnect from session so that the updates on updatedGarantie are not directly saved in db
        em.detach(updatedGarantie);
        updatedGarantie
            .typeGar(UPDATED_TYPE_GAR)
            .montantEvalue(UPDATED_MONTANT_EVALUE)
            .montantAfect(UPDATED_MONTANT_AFECT)
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
        assertThat(testGarantie.getMontantEvalue()).isEqualTo(UPDATED_MONTANT_EVALUE);
        assertThat(testGarantie.getMontantAfect()).isEqualTo(UPDATED_MONTANT_AFECT);
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
        garantieRepository.saveAndFlush(garantie);
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
