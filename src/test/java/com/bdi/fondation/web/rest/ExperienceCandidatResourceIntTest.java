package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.ExperienceCandidat;
import com.bdi.fondation.repository.ExperienceCandidatRepository;
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
        final ExperienceCandidatResource experienceCandidatResource = new ExperienceCandidatResource(experienceCandidatRepository);
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
    public void getNonExistingExperienceCandidat() throws Exception {
        // Get the experienceCandidat
        restExperienceCandidatMockMvc.perform(get("/api/experience-candidats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperienceCandidat() throws Exception {
        // Initialize the database
        experienceCandidatRepository.saveAndFlush(experienceCandidat);
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
        experienceCandidatRepository.saveAndFlush(experienceCandidat);
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
