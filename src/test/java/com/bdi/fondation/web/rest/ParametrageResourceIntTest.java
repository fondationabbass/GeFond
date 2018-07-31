package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Parametrage;
import com.bdi.fondation.repository.ParametrageRepository;
import com.bdi.fondation.service.ParametrageService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.ParametrageCriteria;
import com.bdi.fondation.service.ParametrageQueryService;

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
    private ParametrageService parametrageService;

    @Autowired
    private ParametrageQueryService parametrageQueryService;

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
        final ParametrageResource parametrageResource = new ParametrageResource(parametrageService, parametrageQueryService);
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
    public void getAllParametragesByCodeTypeParamIsEqualToSomething() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where codeTypeParam equals to DEFAULT_CODE_TYPE_PARAM
        defaultParametrageShouldBeFound("codeTypeParam.equals=" + DEFAULT_CODE_TYPE_PARAM);

        // Get all the parametrageList where codeTypeParam equals to UPDATED_CODE_TYPE_PARAM
        defaultParametrageShouldNotBeFound("codeTypeParam.equals=" + UPDATED_CODE_TYPE_PARAM);
    }

    @Test
    @Transactional
    public void getAllParametragesByCodeTypeParamIsInShouldWork() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where codeTypeParam in DEFAULT_CODE_TYPE_PARAM or UPDATED_CODE_TYPE_PARAM
        defaultParametrageShouldBeFound("codeTypeParam.in=" + DEFAULT_CODE_TYPE_PARAM + "," + UPDATED_CODE_TYPE_PARAM);

        // Get all the parametrageList where codeTypeParam equals to UPDATED_CODE_TYPE_PARAM
        defaultParametrageShouldNotBeFound("codeTypeParam.in=" + UPDATED_CODE_TYPE_PARAM);
    }

    @Test
    @Transactional
    public void getAllParametragesByCodeTypeParamIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where codeTypeParam is not null
        defaultParametrageShouldBeFound("codeTypeParam.specified=true");

        // Get all the parametrageList where codeTypeParam is null
        defaultParametrageShouldNotBeFound("codeTypeParam.specified=false");
    }

    @Test
    @Transactional
    public void getAllParametragesByCodeParamIsEqualToSomething() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where codeParam equals to DEFAULT_CODE_PARAM
        defaultParametrageShouldBeFound("codeParam.equals=" + DEFAULT_CODE_PARAM);

        // Get all the parametrageList where codeParam equals to UPDATED_CODE_PARAM
        defaultParametrageShouldNotBeFound("codeParam.equals=" + UPDATED_CODE_PARAM);
    }

    @Test
    @Transactional
    public void getAllParametragesByCodeParamIsInShouldWork() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where codeParam in DEFAULT_CODE_PARAM or UPDATED_CODE_PARAM
        defaultParametrageShouldBeFound("codeParam.in=" + DEFAULT_CODE_PARAM + "," + UPDATED_CODE_PARAM);

        // Get all the parametrageList where codeParam equals to UPDATED_CODE_PARAM
        defaultParametrageShouldNotBeFound("codeParam.in=" + UPDATED_CODE_PARAM);
    }

    @Test
    @Transactional
    public void getAllParametragesByCodeParamIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where codeParam is not null
        defaultParametrageShouldBeFound("codeParam.specified=true");

        // Get all the parametrageList where codeParam is null
        defaultParametrageShouldNotBeFound("codeParam.specified=false");
    }

    @Test
    @Transactional
    public void getAllParametragesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where libelle equals to DEFAULT_LIBELLE
        defaultParametrageShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the parametrageList where libelle equals to UPDATED_LIBELLE
        defaultParametrageShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllParametragesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultParametrageShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the parametrageList where libelle equals to UPDATED_LIBELLE
        defaultParametrageShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllParametragesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where libelle is not null
        defaultParametrageShouldBeFound("libelle.specified=true");

        // Get all the parametrageList where libelle is null
        defaultParametrageShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllParametragesByLib1IsEqualToSomething() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where lib1 equals to DEFAULT_LIB_1
        defaultParametrageShouldBeFound("lib1.equals=" + DEFAULT_LIB_1);

        // Get all the parametrageList where lib1 equals to UPDATED_LIB_1
        defaultParametrageShouldNotBeFound("lib1.equals=" + UPDATED_LIB_1);
    }

    @Test
    @Transactional
    public void getAllParametragesByLib1IsInShouldWork() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where lib1 in DEFAULT_LIB_1 or UPDATED_LIB_1
        defaultParametrageShouldBeFound("lib1.in=" + DEFAULT_LIB_1 + "," + UPDATED_LIB_1);

        // Get all the parametrageList where lib1 equals to UPDATED_LIB_1
        defaultParametrageShouldNotBeFound("lib1.in=" + UPDATED_LIB_1);
    }

    @Test
    @Transactional
    public void getAllParametragesByLib1IsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where lib1 is not null
        defaultParametrageShouldBeFound("lib1.specified=true");

        // Get all the parametrageList where lib1 is null
        defaultParametrageShouldNotBeFound("lib1.specified=false");
    }

    @Test
    @Transactional
    public void getAllParametragesByLib2IsEqualToSomething() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where lib2 equals to DEFAULT_LIB_2
        defaultParametrageShouldBeFound("lib2.equals=" + DEFAULT_LIB_2);

        // Get all the parametrageList where lib2 equals to UPDATED_LIB_2
        defaultParametrageShouldNotBeFound("lib2.equals=" + UPDATED_LIB_2);
    }

    @Test
    @Transactional
    public void getAllParametragesByLib2IsInShouldWork() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where lib2 in DEFAULT_LIB_2 or UPDATED_LIB_2
        defaultParametrageShouldBeFound("lib2.in=" + DEFAULT_LIB_2 + "," + UPDATED_LIB_2);

        // Get all the parametrageList where lib2 equals to UPDATED_LIB_2
        defaultParametrageShouldNotBeFound("lib2.in=" + UPDATED_LIB_2);
    }

    @Test
    @Transactional
    public void getAllParametragesByLib2IsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where lib2 is not null
        defaultParametrageShouldBeFound("lib2.specified=true");

        // Get all the parametrageList where lib2 is null
        defaultParametrageShouldNotBeFound("lib2.specified=false");
    }

    @Test
    @Transactional
    public void getAllParametragesByLib3IsEqualToSomething() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where lib3 equals to DEFAULT_LIB_3
        defaultParametrageShouldBeFound("lib3.equals=" + DEFAULT_LIB_3);

        // Get all the parametrageList where lib3 equals to UPDATED_LIB_3
        defaultParametrageShouldNotBeFound("lib3.equals=" + UPDATED_LIB_3);
    }

    @Test
    @Transactional
    public void getAllParametragesByLib3IsInShouldWork() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where lib3 in DEFAULT_LIB_3 or UPDATED_LIB_3
        defaultParametrageShouldBeFound("lib3.in=" + DEFAULT_LIB_3 + "," + UPDATED_LIB_3);

        // Get all the parametrageList where lib3 equals to UPDATED_LIB_3
        defaultParametrageShouldNotBeFound("lib3.in=" + UPDATED_LIB_3);
    }

    @Test
    @Transactional
    public void getAllParametragesByLib3IsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where lib3 is not null
        defaultParametrageShouldBeFound("lib3.specified=true");

        // Get all the parametrageList where lib3 is null
        defaultParametrageShouldNotBeFound("lib3.specified=false");
    }

    @Test
    @Transactional
    public void getAllParametragesByMnt1IsEqualToSomething() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where mnt1 equals to DEFAULT_MNT_1
        defaultParametrageShouldBeFound("mnt1.equals=" + DEFAULT_MNT_1);

        // Get all the parametrageList where mnt1 equals to UPDATED_MNT_1
        defaultParametrageShouldNotBeFound("mnt1.equals=" + UPDATED_MNT_1);
    }

    @Test
    @Transactional
    public void getAllParametragesByMnt1IsInShouldWork() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where mnt1 in DEFAULT_MNT_1 or UPDATED_MNT_1
        defaultParametrageShouldBeFound("mnt1.in=" + DEFAULT_MNT_1 + "," + UPDATED_MNT_1);

        // Get all the parametrageList where mnt1 equals to UPDATED_MNT_1
        defaultParametrageShouldNotBeFound("mnt1.in=" + UPDATED_MNT_1);
    }

    @Test
    @Transactional
    public void getAllParametragesByMnt1IsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where mnt1 is not null
        defaultParametrageShouldBeFound("mnt1.specified=true");

        // Get all the parametrageList where mnt1 is null
        defaultParametrageShouldNotBeFound("mnt1.specified=false");
    }

    @Test
    @Transactional
    public void getAllParametragesByMnt2IsEqualToSomething() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where mnt2 equals to DEFAULT_MNT_2
        defaultParametrageShouldBeFound("mnt2.equals=" + DEFAULT_MNT_2);

        // Get all the parametrageList where mnt2 equals to UPDATED_MNT_2
        defaultParametrageShouldNotBeFound("mnt2.equals=" + UPDATED_MNT_2);
    }

    @Test
    @Transactional
    public void getAllParametragesByMnt2IsInShouldWork() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where mnt2 in DEFAULT_MNT_2 or UPDATED_MNT_2
        defaultParametrageShouldBeFound("mnt2.in=" + DEFAULT_MNT_2 + "," + UPDATED_MNT_2);

        // Get all the parametrageList where mnt2 equals to UPDATED_MNT_2
        defaultParametrageShouldNotBeFound("mnt2.in=" + UPDATED_MNT_2);
    }

    @Test
    @Transactional
    public void getAllParametragesByMnt2IsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where mnt2 is not null
        defaultParametrageShouldBeFound("mnt2.specified=true");

        // Get all the parametrageList where mnt2 is null
        defaultParametrageShouldNotBeFound("mnt2.specified=false");
    }

    @Test
    @Transactional
    public void getAllParametragesByMnt3IsEqualToSomething() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where mnt3 equals to DEFAULT_MNT_3
        defaultParametrageShouldBeFound("mnt3.equals=" + DEFAULT_MNT_3);

        // Get all the parametrageList where mnt3 equals to UPDATED_MNT_3
        defaultParametrageShouldNotBeFound("mnt3.equals=" + UPDATED_MNT_3);
    }

    @Test
    @Transactional
    public void getAllParametragesByMnt3IsInShouldWork() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where mnt3 in DEFAULT_MNT_3 or UPDATED_MNT_3
        defaultParametrageShouldBeFound("mnt3.in=" + DEFAULT_MNT_3 + "," + UPDATED_MNT_3);

        // Get all the parametrageList where mnt3 equals to UPDATED_MNT_3
        defaultParametrageShouldNotBeFound("mnt3.in=" + UPDATED_MNT_3);
    }

    @Test
    @Transactional
    public void getAllParametragesByMnt3IsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrageRepository.saveAndFlush(parametrage);

        // Get all the parametrageList where mnt3 is not null
        defaultParametrageShouldBeFound("mnt3.specified=true");

        // Get all the parametrageList where mnt3 is null
        defaultParametrageShouldNotBeFound("mnt3.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParametrageShouldBeFound(String filter) throws Exception {
        restParametrageMockMvc.perform(get("/api/parametrages?sort=id,desc&" + filter))
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

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParametrageShouldNotBeFound(String filter) throws Exception {
        restParametrageMockMvc.perform(get("/api/parametrages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
        parametrageService.save(parametrage);

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
        parametrageService.save(parametrage);

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
