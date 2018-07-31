package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Chapitre;
import com.bdi.fondation.repository.ChapitreRepository;
import com.bdi.fondation.service.ChapitreService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.ChapitreCriteria;
import com.bdi.fondation.service.ChapitreQueryService;

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
 * Test class for the ChapitreResource REST controller.
 *
 * @see ChapitreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class ChapitreResourceIntTest {

    private static final String DEFAULT_LIB_CHAPITRE = "AAAAAAAAAA";
    private static final String UPDATED_LIB_CHAPITRE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE_COMPTE = "BBBBBBBBBB";

    @Autowired
    private ChapitreRepository chapitreRepository;

    @Autowired
    private ChapitreService chapitreService;

    @Autowired
    private ChapitreQueryService chapitreQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChapitreMockMvc;

    private Chapitre chapitre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChapitreResource chapitreResource = new ChapitreResource(chapitreService, chapitreQueryService);
        this.restChapitreMockMvc = MockMvcBuilders.standaloneSetup(chapitreResource)
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
    public static Chapitre createEntity(EntityManager em) {
        Chapitre chapitre = new Chapitre()
            .libChapitre(DEFAULT_LIB_CHAPITRE)
            .categorieCompte(DEFAULT_CATEGORIE_COMPTE);
        return chapitre;
    }

    @Before
    public void initTest() {
        chapitre = createEntity(em);
    }

    @Test
    @Transactional
    public void createChapitre() throws Exception {
        int databaseSizeBeforeCreate = chapitreRepository.findAll().size();

        // Create the Chapitre
        restChapitreMockMvc.perform(post("/api/chapitres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chapitre)))
            .andExpect(status().isCreated());

        // Validate the Chapitre in the database
        List<Chapitre> chapitreList = chapitreRepository.findAll();
        assertThat(chapitreList).hasSize(databaseSizeBeforeCreate + 1);
        Chapitre testChapitre = chapitreList.get(chapitreList.size() - 1);
        assertThat(testChapitre.getLibChapitre()).isEqualTo(DEFAULT_LIB_CHAPITRE);
        assertThat(testChapitre.getCategorieCompte()).isEqualTo(DEFAULT_CATEGORIE_COMPTE);
    }

    @Test
    @Transactional
    public void createChapitreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chapitreRepository.findAll().size();

        // Create the Chapitre with an existing ID
        chapitre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChapitreMockMvc.perform(post("/api/chapitres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chapitre)))
            .andExpect(status().isBadRequest());

        // Validate the Chapitre in the database
        List<Chapitre> chapitreList = chapitreRepository.findAll();
        assertThat(chapitreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChapitres() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);

        // Get all the chapitreList
        restChapitreMockMvc.perform(get("/api/chapitres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chapitre.getId().intValue())))
            .andExpect(jsonPath("$.[*].libChapitre").value(hasItem(DEFAULT_LIB_CHAPITRE.toString())))
            .andExpect(jsonPath("$.[*].categorieCompte").value(hasItem(DEFAULT_CATEGORIE_COMPTE.toString())));
    }

    @Test
    @Transactional
    public void getChapitre() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);

        // Get the chapitre
        restChapitreMockMvc.perform(get("/api/chapitres/{id}", chapitre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chapitre.getId().intValue()))
            .andExpect(jsonPath("$.libChapitre").value(DEFAULT_LIB_CHAPITRE.toString()))
            .andExpect(jsonPath("$.categorieCompte").value(DEFAULT_CATEGORIE_COMPTE.toString()));
    }

    @Test
    @Transactional
    public void getAllChapitresByLibChapitreIsEqualToSomething() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);

        // Get all the chapitreList where libChapitre equals to DEFAULT_LIB_CHAPITRE
        defaultChapitreShouldBeFound("libChapitre.equals=" + DEFAULT_LIB_CHAPITRE);

        // Get all the chapitreList where libChapitre equals to UPDATED_LIB_CHAPITRE
        defaultChapitreShouldNotBeFound("libChapitre.equals=" + UPDATED_LIB_CHAPITRE);
    }

    @Test
    @Transactional
    public void getAllChapitresByLibChapitreIsInShouldWork() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);

        // Get all the chapitreList where libChapitre in DEFAULT_LIB_CHAPITRE or UPDATED_LIB_CHAPITRE
        defaultChapitreShouldBeFound("libChapitre.in=" + DEFAULT_LIB_CHAPITRE + "," + UPDATED_LIB_CHAPITRE);

        // Get all the chapitreList where libChapitre equals to UPDATED_LIB_CHAPITRE
        defaultChapitreShouldNotBeFound("libChapitre.in=" + UPDATED_LIB_CHAPITRE);
    }

    @Test
    @Transactional
    public void getAllChapitresByLibChapitreIsNullOrNotNull() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);

        // Get all the chapitreList where libChapitre is not null
        defaultChapitreShouldBeFound("libChapitre.specified=true");

        // Get all the chapitreList where libChapitre is null
        defaultChapitreShouldNotBeFound("libChapitre.specified=false");
    }

    @Test
    @Transactional
    public void getAllChapitresByCategorieCompteIsEqualToSomething() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);

        // Get all the chapitreList where categorieCompte equals to DEFAULT_CATEGORIE_COMPTE
        defaultChapitreShouldBeFound("categorieCompte.equals=" + DEFAULT_CATEGORIE_COMPTE);

        // Get all the chapitreList where categorieCompte equals to UPDATED_CATEGORIE_COMPTE
        defaultChapitreShouldNotBeFound("categorieCompte.equals=" + UPDATED_CATEGORIE_COMPTE);
    }

    @Test
    @Transactional
    public void getAllChapitresByCategorieCompteIsInShouldWork() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);

        // Get all the chapitreList where categorieCompte in DEFAULT_CATEGORIE_COMPTE or UPDATED_CATEGORIE_COMPTE
        defaultChapitreShouldBeFound("categorieCompte.in=" + DEFAULT_CATEGORIE_COMPTE + "," + UPDATED_CATEGORIE_COMPTE);

        // Get all the chapitreList where categorieCompte equals to UPDATED_CATEGORIE_COMPTE
        defaultChapitreShouldNotBeFound("categorieCompte.in=" + UPDATED_CATEGORIE_COMPTE);
    }

    @Test
    @Transactional
    public void getAllChapitresByCategorieCompteIsNullOrNotNull() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);

        // Get all the chapitreList where categorieCompte is not null
        defaultChapitreShouldBeFound("categorieCompte.specified=true");

        // Get all the chapitreList where categorieCompte is null
        defaultChapitreShouldNotBeFound("categorieCompte.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultChapitreShouldBeFound(String filter) throws Exception {
        restChapitreMockMvc.perform(get("/api/chapitres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chapitre.getId().intValue())))
            .andExpect(jsonPath("$.[*].libChapitre").value(hasItem(DEFAULT_LIB_CHAPITRE.toString())))
            .andExpect(jsonPath("$.[*].categorieCompte").value(hasItem(DEFAULT_CATEGORIE_COMPTE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultChapitreShouldNotBeFound(String filter) throws Exception {
        restChapitreMockMvc.perform(get("/api/chapitres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingChapitre() throws Exception {
        // Get the chapitre
        restChapitreMockMvc.perform(get("/api/chapitres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChapitre() throws Exception {
        // Initialize the database
        chapitreService.save(chapitre);

        int databaseSizeBeforeUpdate = chapitreRepository.findAll().size();

        // Update the chapitre
        Chapitre updatedChapitre = chapitreRepository.findOne(chapitre.getId());
        // Disconnect from session so that the updates on updatedChapitre are not directly saved in db
        em.detach(updatedChapitre);
        updatedChapitre
            .libChapitre(UPDATED_LIB_CHAPITRE)
            .categorieCompte(UPDATED_CATEGORIE_COMPTE);

        restChapitreMockMvc.perform(put("/api/chapitres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChapitre)))
            .andExpect(status().isOk());

        // Validate the Chapitre in the database
        List<Chapitre> chapitreList = chapitreRepository.findAll();
        assertThat(chapitreList).hasSize(databaseSizeBeforeUpdate);
        Chapitre testChapitre = chapitreList.get(chapitreList.size() - 1);
        assertThat(testChapitre.getLibChapitre()).isEqualTo(UPDATED_LIB_CHAPITRE);
        assertThat(testChapitre.getCategorieCompte()).isEqualTo(UPDATED_CATEGORIE_COMPTE);
    }

    @Test
    @Transactional
    public void updateNonExistingChapitre() throws Exception {
        int databaseSizeBeforeUpdate = chapitreRepository.findAll().size();

        // Create the Chapitre

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChapitreMockMvc.perform(put("/api/chapitres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chapitre)))
            .andExpect(status().isCreated());

        // Validate the Chapitre in the database
        List<Chapitre> chapitreList = chapitreRepository.findAll();
        assertThat(chapitreList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChapitre() throws Exception {
        // Initialize the database
        chapitreService.save(chapitre);

        int databaseSizeBeforeDelete = chapitreRepository.findAll().size();

        // Get the chapitre
        restChapitreMockMvc.perform(delete("/api/chapitres/{id}", chapitre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Chapitre> chapitreList = chapitreRepository.findAll();
        assertThat(chapitreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chapitre.class);
        Chapitre chapitre1 = new Chapitre();
        chapitre1.setId(1L);
        Chapitre chapitre2 = new Chapitre();
        chapitre2.setId(chapitre1.getId());
        assertThat(chapitre1).isEqualTo(chapitre2);
        chapitre2.setId(2L);
        assertThat(chapitre1).isNotEqualTo(chapitre2);
        chapitre1.setId(null);
        assertThat(chapitre1).isNotEqualTo(chapitre2);
    }
}
