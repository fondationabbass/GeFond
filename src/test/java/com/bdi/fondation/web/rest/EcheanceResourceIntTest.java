package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Echeance;
import com.bdi.fondation.repository.EcheanceRepository;
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
        final EcheanceResource echeanceResource = new EcheanceResource(echeanceRepository);
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
    public void getNonExistingEcheance() throws Exception {
        // Get the echeance
        restEcheanceMockMvc.perform(get("/api/echeances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEcheance() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);
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
        echeanceRepository.saveAndFlush(echeance);
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
