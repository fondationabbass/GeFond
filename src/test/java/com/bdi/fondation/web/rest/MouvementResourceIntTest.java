package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Mouvement;
import com.bdi.fondation.repository.MouvementRepository;
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
 * Test class for the MouvementResource REST controller.
 *
 * @see MouvementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class MouvementResourceIntTest {

    private static final LocalDate DEFAULT_DATE_MVT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MVT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIB = "AAAAAAAAAA";
    private static final String UPDATED_LIB = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final String DEFAULT_SENS = "AAAAAAAAAA";
    private static final String UPDATED_SENS = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMouvementMockMvc;

    private Mouvement mouvement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MouvementResource mouvementResource = new MouvementResource(mouvementRepository);
        this.restMouvementMockMvc = MockMvcBuilders.standaloneSetup(mouvementResource)
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
    public static Mouvement createEntity(EntityManager em) {
        Mouvement mouvement = new Mouvement()
            .dateMvt(DEFAULT_DATE_MVT)
            .lib(DEFAULT_LIB)
            .montant(DEFAULT_MONTANT)
            .sens(DEFAULT_SENS)
            .etat(DEFAULT_ETAT);
        return mouvement;
    }

    @Before
    public void initTest() {
        mouvement = createEntity(em);
    }

    @Test
    @Transactional
    public void createMouvement() throws Exception {
        int databaseSizeBeforeCreate = mouvementRepository.findAll().size();

        // Create the Mouvement
        restMouvementMockMvc.perform(post("/api/mouvements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mouvement)))
            .andExpect(status().isCreated());

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeCreate + 1);
        Mouvement testMouvement = mouvementList.get(mouvementList.size() - 1);
        assertThat(testMouvement.getDateMvt()).isEqualTo(DEFAULT_DATE_MVT);
        assertThat(testMouvement.getLib()).isEqualTo(DEFAULT_LIB);
        assertThat(testMouvement.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testMouvement.getSens()).isEqualTo(DEFAULT_SENS);
        assertThat(testMouvement.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    public void createMouvementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mouvementRepository.findAll().size();

        // Create the Mouvement with an existing ID
        mouvement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMouvementMockMvc.perform(post("/api/mouvements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mouvement)))
            .andExpect(status().isBadRequest());

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibIsRequired() throws Exception {
        int databaseSizeBeforeTest = mouvementRepository.findAll().size();
        // set the field null
        mouvement.setLib(null);

        // Create the Mouvement, which fails.

        restMouvementMockMvc.perform(post("/api/mouvements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mouvement)))
            .andExpect(status().isBadRequest());

        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = mouvementRepository.findAll().size();
        // set the field null
        mouvement.setMontant(null);

        // Create the Mouvement, which fails.

        restMouvementMockMvc.perform(post("/api/mouvements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mouvement)))
            .andExpect(status().isBadRequest());

        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSensIsRequired() throws Exception {
        int databaseSizeBeforeTest = mouvementRepository.findAll().size();
        // set the field null
        mouvement.setSens(null);

        // Create the Mouvement, which fails.

        restMouvementMockMvc.perform(post("/api/mouvements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mouvement)))
            .andExpect(status().isBadRequest());

        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMouvements() throws Exception {
        // Initialize the database
        mouvementRepository.saveAndFlush(mouvement);

        // Get all the mouvementList
        restMouvementMockMvc.perform(get("/api/mouvements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateMvt").value(hasItem(DEFAULT_DATE_MVT.toString())))
            .andExpect(jsonPath("$.[*].lib").value(hasItem(DEFAULT_LIB.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].sens").value(hasItem(DEFAULT_SENS.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    @Transactional
    public void getMouvement() throws Exception {
        // Initialize the database
        mouvementRepository.saveAndFlush(mouvement);

        // Get the mouvement
        restMouvementMockMvc.perform(get("/api/mouvements/{id}", mouvement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mouvement.getId().intValue()))
            .andExpect(jsonPath("$.dateMvt").value(DEFAULT_DATE_MVT.toString()))
            .andExpect(jsonPath("$.lib").value(DEFAULT_LIB.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.sens").value(DEFAULT_SENS.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMouvement() throws Exception {
        // Get the mouvement
        restMouvementMockMvc.perform(get("/api/mouvements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMouvement() throws Exception {
        // Initialize the database
        mouvementRepository.saveAndFlush(mouvement);
        int databaseSizeBeforeUpdate = mouvementRepository.findAll().size();

        // Update the mouvement
        Mouvement updatedMouvement = mouvementRepository.findOne(mouvement.getId());
        // Disconnect from session so that the updates on updatedMouvement are not directly saved in db
        em.detach(updatedMouvement);
        updatedMouvement
            .dateMvt(UPDATED_DATE_MVT)
            .lib(UPDATED_LIB)
            .montant(UPDATED_MONTANT)
            .sens(UPDATED_SENS)
            .etat(UPDATED_ETAT);

        restMouvementMockMvc.perform(put("/api/mouvements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMouvement)))
            .andExpect(status().isOk());

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
        Mouvement testMouvement = mouvementList.get(mouvementList.size() - 1);
        assertThat(testMouvement.getDateMvt()).isEqualTo(UPDATED_DATE_MVT);
        assertThat(testMouvement.getLib()).isEqualTo(UPDATED_LIB);
        assertThat(testMouvement.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testMouvement.getSens()).isEqualTo(UPDATED_SENS);
        assertThat(testMouvement.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void updateNonExistingMouvement() throws Exception {
        int databaseSizeBeforeUpdate = mouvementRepository.findAll().size();

        // Create the Mouvement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMouvementMockMvc.perform(put("/api/mouvements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mouvement)))
            .andExpect(status().isCreated());

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMouvement() throws Exception {
        // Initialize the database
        mouvementRepository.saveAndFlush(mouvement);
        int databaseSizeBeforeDelete = mouvementRepository.findAll().size();

        // Get the mouvement
        restMouvementMockMvc.perform(delete("/api/mouvements/{id}", mouvement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mouvement.class);
        Mouvement mouvement1 = new Mouvement();
        mouvement1.setId(1L);
        Mouvement mouvement2 = new Mouvement();
        mouvement2.setId(mouvement1.getId());
        assertThat(mouvement1).isEqualTo(mouvement2);
        mouvement2.setId(2L);
        assertThat(mouvement1).isNotEqualTo(mouvement2);
        mouvement1.setId(null);
        assertThat(mouvement1).isNotEqualTo(mouvement2);
    }
}
