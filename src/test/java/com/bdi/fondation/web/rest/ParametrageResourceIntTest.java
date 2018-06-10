package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Parametrage;
import com.bdi.fondation.repository.ParametrageRepository;
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
import java.util.List;

import static com.bdi.fondation.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ParametrageResource REST controller.
 *
 * @see ParametrageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class ParametrageResourceIntTest {

    private static final String DEFAULT_CODE_TYPE_PARAM = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_PARAM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_PARAM = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PARAM = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_1 = "AAAAAAAAAA";
    private static final String UPDATED_LIB_1 = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_2 = "AAAAAAAAAA";
    private static final String UPDATED_LIB_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_3 = "AAAAAAAAAA";
    private static final String UPDATED_LIB_3 = "BBBBBBBBBB";

    private static final Double DEFAULT_MNT_1 = 1D;
    private static final Double UPDATED_MNT_1 = 2D;

    private static final Double DEFAULT_MNT_2 = 1D;
    private static final Double UPDATED_MNT_2 = 2D;

    private static final Double DEFAULT_MNT_3 = 1D;
    private static final Double UPDATED_MNT_3 = 2D;

    @Autowired
    private ParametrageRepository parametrageRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParametrageMockMvc;

    private Parametrage parametrage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParametrageResource parametrageResource = new ParametrageResource(parametrageRepository);
        this.restParametrageMockMvc = MockMvcBuilders.standaloneSetup(parametrageResource)
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
    public static Parametrage createEntity(EntityManager em) {
        Parametrage parametrage = new Parametrage()
            .codeTypeParam(DEFAULT_CODE_TYPE_PARAM)
            .codeParam(DEFAULT_CODE_PARAM)
            .libelle(DEFAULT_LIBELLE)
            .lib1(DEFAULT_LIB_1)
            .lib2(DEFAULT_LIB_2)
            .lib3(DEFAULT_LIB_3)
            .mnt1(DEFAULT_MNT_1)
            .mnt2(DEFAULT_MNT_2)
            .mnt3(DEFAULT_MNT_3);
        return parametrage;
    }

    @Before
    public void initTest() {
        parametrage = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametrage() throws Exception {
        int databaseSizeBeforeCreate = parametrageRepository.findAll().size();

        // Create the Parametrage
        restParametrageMockMvc.perform(post("/api/parametrages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametrage)))
            .andExpect(status().isCreated());

        // Validate the Parametrage in the database
        List<Parametrage> parametrageList = parametrageRepository.findAll();
        assertThat(parametrageList).hasSize(databaseSizeBeforeCreate + 1);
        Parametrage testParametrage = parametrageList.get(parametrageList.size() - 1);
        assertThat(testParametrage.getCodeTypeParam()).isEqualTo(DEFAULT_CODE_TYPE_PARAM);
        assertThat(testParametrage.getCodeParam()).isEqualTo(DEFAULT_CODE_PARAM);
        assertThat(testParametrage.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testParametrage.getLib1()).isEqualTo(DEFAULT_LIB_1);
        assertThat(testParametrage.getLib2()).isEqualTo(DEFAULT_LIB_2);
        assertThat(testParametrage.getLib3()).isEqualTo(DEFAULT_LIB_3);
        assertThat(testParametrage.getMnt1()).isEqualTo(DEFAULT_MNT_1);
        assertThat(testParametrage.getMnt2()).isEqualTo(DEFAULT_MNT_2);
        assertThat(testParametrage.getMnt3()).isEqualTo(DEFAULT_MNT_3);
    }

    @Test
    @Transactional
    public void createParametrageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametrageRepository.findAll().size();

        // Create the Parametrage with an existing ID
        parametrage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametrageMockMvc.perform(post("/api/parametrages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametrage)))
            .andExpect(status().isBadRequest());

        // Validate the Parametrage in the database
        List<Parametrage> parametrageList = parametrageRepository.findAll();
        assertThat(parametrageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParametrages() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList
        restParametrageMockMvc.perform(get("/api/parametrages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametrage.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeParam").value(hasItem(DEFAULT_CODE_TYPE_PARAM.toString())))
            .andExpect(jsonPath("$.[*].codeParam").value(hasItem(DEFAULT_CODE_PARAM.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].lib1").value(hasItem(DEFAULT_LIB_1.toString())))
            .andExpect(jsonPath("$.[*].lib2").value(hasItem(DEFAULT_LIB_2.toString())))
            .andExpect(jsonPath("$.[*].lib3").value(hasItem(DEFAULT_LIB_3.toString())))
            .andExpect(jsonPath("$.[*].mnt1").value(hasItem(DEFAULT_MNT_1.doubleValue())))
            .andExpect(jsonPath("$.[*].mnt2").value(hasItem(DEFAULT_MNT_2.doubleValue())))
            .andExpect(jsonPath("$.[*].mnt3").value(hasItem(DEFAULT_MNT_3.doubleValue())));
    }

    @Test
    @Transactional
    public void getParametrage() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get the parametrage
        restParametrageMockMvc.perform(get("/api/parametrages/{id}", parametrage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parametrage.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeParam").value(DEFAULT_CODE_TYPE_PARAM.toString()))
            .andExpect(jsonPath("$.codeParam").value(DEFAULT_CODE_PARAM.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.lib1").value(DEFAULT_LIB_1.toString()))
            .andExpect(jsonPath("$.lib2").value(DEFAULT_LIB_2.toString()))
            .andExpect(jsonPath("$.lib3").value(DEFAULT_LIB_3.toString()))
            .andExpect(jsonPath("$.mnt1").value(DEFAULT_MNT_1.doubleValue()))
            .andExpect(jsonPath("$.mnt2").value(DEFAULT_MNT_2.doubleValue()))
            .andExpect(jsonPath("$.mnt3").value(DEFAULT_MNT_3.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParametrage() throws Exception {
        // Get the parametrage
        restParametrageMockMvc.perform(get("/api/parametrages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametrage() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);
        int databaseSizeBeforeUpdate = parametrageRepository.findAll().size();

        // Update the parametrage
        Parametrage updatedParametrage = parametrageRepository.findOne(parametrage.getId());
        // Disconnect from session so that the updates on updatedParametrage are not directly saved in db
        em.detach(updatedParametrage);
        updatedParametrage
            .codeTypeParam(UPDATED_CODE_TYPE_PARAM)
            .codeParam(UPDATED_CODE_PARAM)
            .libelle(UPDATED_LIBELLE)
            .lib1(UPDATED_LIB_1)
            .lib2(UPDATED_LIB_2)
            .lib3(UPDATED_LIB_3)
            .mnt1(UPDATED_MNT_1)
            .mnt2(UPDATED_MNT_2)
            .mnt3(UPDATED_MNT_3);

        restParametrageMockMvc.perform(put("/api/parametrages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParametrage)))
            .andExpect(status().isOk());

        // Validate the Parametrage in the database
        List<Parametrage> parametrageList = parametrageRepository.findAll();
        assertThat(parametrageList).hasSize(databaseSizeBeforeUpdate);
        Parametrage testParametrage = parametrageList.get(parametrageList.size() - 1);
        assertThat(testParametrage.getCodeTypeParam()).isEqualTo(UPDATED_CODE_TYPE_PARAM);
        assertThat(testParametrage.getCodeParam()).isEqualTo(UPDATED_CODE_PARAM);
        assertThat(testParametrage.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testParametrage.getLib1()).isEqualTo(UPDATED_LIB_1);
        assertThat(testParametrage.getLib2()).isEqualTo(UPDATED_LIB_2);
        assertThat(testParametrage.getLib3()).isEqualTo(UPDATED_LIB_3);
        assertThat(testParametrage.getMnt1()).isEqualTo(UPDATED_MNT_1);
        assertThat(testParametrage.getMnt2()).isEqualTo(UPDATED_MNT_2);
        assertThat(testParametrage.getMnt3()).isEqualTo(UPDATED_MNT_3);
    }

    @Test
    @Transactional
    public void updateNonExistingParametrage() throws Exception {
        int databaseSizeBeforeUpdate = parametrageRepository.findAll().size();

        // Create the Parametrage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParametrageMockMvc.perform(put("/api/parametrages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametrage)))
            .andExpect(status().isCreated());

        // Validate the Parametrage in the database
        List<Parametrage> parametrageList = parametrageRepository.findAll();
        assertThat(parametrageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParametrage() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);
        int databaseSizeBeforeDelete = parametrageRepository.findAll().size();

        // Get the parametrage
        restParametrageMockMvc.perform(delete("/api/parametrages/{id}", parametrage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parametrage> parametrageList = parametrageRepository.findAll();
        assertThat(parametrageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parametrage.class);
        Parametrage parametrage1 = new Parametrage();
        parametrage1.setId(1L);
        Parametrage parametrage2 = new Parametrage();
        parametrage2.setId(parametrage1.getId());
        assertThat(parametrage1).isEqualTo(parametrage2);
        parametrage2.setId(2L);
        assertThat(parametrage1).isNotEqualTo(parametrage2);
        parametrage1.setId(null);
        assertThat(parametrage1).isNotEqualTo(parametrage2);
    }
}
