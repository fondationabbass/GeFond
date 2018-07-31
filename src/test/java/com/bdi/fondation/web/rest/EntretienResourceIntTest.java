package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Entretien;
import com.bdi.fondation.domain.Candidature;
import com.bdi.fondation.repository.EntretienRepository;
import com.bdi.fondation.service.EntretienService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.EntretienCriteria;
import com.bdi.fondation.service.EntretienQueryService;

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
 * Test class for the EntretienResource REST controller.
 *
 * @see EntretienResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class EntretienResourceIntTest {

    private static final String DEFAULT_CADRE = "AAAAAAAAAA";
    private static final String UPDATED_CADRE = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTAT = "AAAAAAAAAA";
    private static final String UPDATED_RESULTAT = "BBBBBBBBBB";

    private static final String DEFAULT_INTERLOCUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_INTERLOCUTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ENTRETIEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENTRETIEN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EntretienRepository entretienRepository;

    @Autowired
    private EntretienService entretienService;

    @Autowired
    private EntretienQueryService entretienQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntretienMockMvc;

    private Entretien entretien;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntretienResource entretienResource = new EntretienResource(entretienService, entretienQueryService);
        this.restEntretienMockMvc = MockMvcBuilders.standaloneSetup(entretienResource)
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
    public static Entretien createEntity(EntityManager em) {
        Entretien entretien = new Entretien()
            .cadre(DEFAULT_CADRE)
            .resultat(DEFAULT_RESULTAT)
            .interlocuteur(DEFAULT_INTERLOCUTEUR)
            .etat(DEFAULT_ETAT)
            .dateEntretien(DEFAULT_DATE_ENTRETIEN);
        return entretien;
    }

    @Before
    public void initTest() {
        entretien = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntretien() throws Exception {
        int databaseSizeBeforeCreate = entretienRepository.findAll().size();

        // Create the Entretien
        restEntretienMockMvc.perform(post("/api/entretiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entretien)))
            .andExpect(status().isCreated());

        // Validate the Entretien in the database
        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeCreate + 1);
        Entretien testEntretien = entretienList.get(entretienList.size() - 1);
        assertThat(testEntretien.getCadre()).isEqualTo(DEFAULT_CADRE);
        assertThat(testEntretien.getResultat()).isEqualTo(DEFAULT_RESULTAT);
        assertThat(testEntretien.getInterlocuteur()).isEqualTo(DEFAULT_INTERLOCUTEUR);
        assertThat(testEntretien.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testEntretien.getDateEntretien()).isEqualTo(DEFAULT_DATE_ENTRETIEN);
    }

    @Test
    @Transactional
    public void createEntretienWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entretienRepository.findAll().size();

        // Create the Entretien with an existing ID
        entretien.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntretienMockMvc.perform(post("/api/entretiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entretien)))
            .andExpect(status().isBadRequest());

        // Validate the Entretien in the database
        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCadreIsRequired() throws Exception {
        int databaseSizeBeforeTest = entretienRepository.findAll().size();
        // set the field null
        entretien.setCadre(null);

        // Create the Entretien, which fails.

        restEntretienMockMvc.perform(post("/api/entretiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entretien)))
            .andExpect(status().isBadRequest());

        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultatIsRequired() throws Exception {
        int databaseSizeBeforeTest = entretienRepository.findAll().size();
        // set the field null
        entretien.setResultat(null);

        // Create the Entretien, which fails.

        restEntretienMockMvc.perform(post("/api/entretiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entretien)))
            .andExpect(status().isBadRequest());

        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInterlocuteurIsRequired() throws Exception {
        int databaseSizeBeforeTest = entretienRepository.findAll().size();
        // set the field null
        entretien.setInterlocuteur(null);

        // Create the Entretien, which fails.

        restEntretienMockMvc.perform(post("/api/entretiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entretien)))
            .andExpect(status().isBadRequest());

        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = entretienRepository.findAll().size();
        // set the field null
        entretien.setEtat(null);

        // Create the Entretien, which fails.

        restEntretienMockMvc.perform(post("/api/entretiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entretien)))
            .andExpect(status().isBadRequest());

        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntretiens() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList
        restEntretienMockMvc.perform(get("/api/entretiens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entretien.getId().intValue())))
            .andExpect(jsonPath("$.[*].cadre").value(hasItem(DEFAULT_CADRE.toString())))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT.toString())))
            .andExpect(jsonPath("$.[*].interlocuteur").value(hasItem(DEFAULT_INTERLOCUTEUR.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].dateEntretien").value(hasItem(DEFAULT_DATE_ENTRETIEN.toString())));
    }

    @Test
    @Transactional
    public void getEntretien() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get the entretien
        restEntretienMockMvc.perform(get("/api/entretiens/{id}", entretien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entretien.getId().intValue()))
            .andExpect(jsonPath("$.cadre").value(DEFAULT_CADRE.toString()))
            .andExpect(jsonPath("$.resultat").value(DEFAULT_RESULTAT.toString()))
            .andExpect(jsonPath("$.interlocuteur").value(DEFAULT_INTERLOCUTEUR.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.dateEntretien").value(DEFAULT_DATE_ENTRETIEN.toString()));
    }

    @Test
    @Transactional
    public void getAllEntretiensByCadreIsEqualToSomething() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where cadre equals to DEFAULT_CADRE
        defaultEntretienShouldBeFound("cadre.equals=" + DEFAULT_CADRE);

        // Get all the entretienList where cadre equals to UPDATED_CADRE
        defaultEntretienShouldNotBeFound("cadre.equals=" + UPDATED_CADRE);
    }

    @Test
    @Transactional
    public void getAllEntretiensByCadreIsInShouldWork() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where cadre in DEFAULT_CADRE or UPDATED_CADRE
        defaultEntretienShouldBeFound("cadre.in=" + DEFAULT_CADRE + "," + UPDATED_CADRE);

        // Get all the entretienList where cadre equals to UPDATED_CADRE
        defaultEntretienShouldNotBeFound("cadre.in=" + UPDATED_CADRE);
    }

    @Test
    @Transactional
    public void getAllEntretiensByCadreIsNullOrNotNull() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where cadre is not null
        defaultEntretienShouldBeFound("cadre.specified=true");

        // Get all the entretienList where cadre is null
        defaultEntretienShouldNotBeFound("cadre.specified=false");
    }

    @Test
    @Transactional
    public void getAllEntretiensByResultatIsEqualToSomething() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where resultat equals to DEFAULT_RESULTAT
        defaultEntretienShouldBeFound("resultat.equals=" + DEFAULT_RESULTAT);

        // Get all the entretienList where resultat equals to UPDATED_RESULTAT
        defaultEntretienShouldNotBeFound("resultat.equals=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    public void getAllEntretiensByResultatIsInShouldWork() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where resultat in DEFAULT_RESULTAT or UPDATED_RESULTAT
        defaultEntretienShouldBeFound("resultat.in=" + DEFAULT_RESULTAT + "," + UPDATED_RESULTAT);

        // Get all the entretienList where resultat equals to UPDATED_RESULTAT
        defaultEntretienShouldNotBeFound("resultat.in=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    public void getAllEntretiensByResultatIsNullOrNotNull() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where resultat is not null
        defaultEntretienShouldBeFound("resultat.specified=true");

        // Get all the entretienList where resultat is null
        defaultEntretienShouldNotBeFound("resultat.specified=false");
    }

    @Test
    @Transactional
    public void getAllEntretiensByInterlocuteurIsEqualToSomething() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where interlocuteur equals to DEFAULT_INTERLOCUTEUR
        defaultEntretienShouldBeFound("interlocuteur.equals=" + DEFAULT_INTERLOCUTEUR);

        // Get all the entretienList where interlocuteur equals to UPDATED_INTERLOCUTEUR
        defaultEntretienShouldNotBeFound("interlocuteur.equals=" + UPDATED_INTERLOCUTEUR);
    }

    @Test
    @Transactional
    public void getAllEntretiensByInterlocuteurIsInShouldWork() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where interlocuteur in DEFAULT_INTERLOCUTEUR or UPDATED_INTERLOCUTEUR
        defaultEntretienShouldBeFound("interlocuteur.in=" + DEFAULT_INTERLOCUTEUR + "," + UPDATED_INTERLOCUTEUR);

        // Get all the entretienList where interlocuteur equals to UPDATED_INTERLOCUTEUR
        defaultEntretienShouldNotBeFound("interlocuteur.in=" + UPDATED_INTERLOCUTEUR);
    }

    @Test
    @Transactional
    public void getAllEntretiensByInterlocuteurIsNullOrNotNull() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where interlocuteur is not null
        defaultEntretienShouldBeFound("interlocuteur.specified=true");

        // Get all the entretienList where interlocuteur is null
        defaultEntretienShouldNotBeFound("interlocuteur.specified=false");
    }

    @Test
    @Transactional
    public void getAllEntretiensByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where etat equals to DEFAULT_ETAT
        defaultEntretienShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the entretienList where etat equals to UPDATED_ETAT
        defaultEntretienShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllEntretiensByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultEntretienShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the entretienList where etat equals to UPDATED_ETAT
        defaultEntretienShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllEntretiensByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where etat is not null
        defaultEntretienShouldBeFound("etat.specified=true");

        // Get all the entretienList where etat is null
        defaultEntretienShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllEntretiensByDateEntretienIsEqualToSomething() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where dateEntretien equals to DEFAULT_DATE_ENTRETIEN
        defaultEntretienShouldBeFound("dateEntretien.equals=" + DEFAULT_DATE_ENTRETIEN);

        // Get all the entretienList where dateEntretien equals to UPDATED_DATE_ENTRETIEN
        defaultEntretienShouldNotBeFound("dateEntretien.equals=" + UPDATED_DATE_ENTRETIEN);
    }

    @Test
    @Transactional
    public void getAllEntretiensByDateEntretienIsInShouldWork() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where dateEntretien in DEFAULT_DATE_ENTRETIEN or UPDATED_DATE_ENTRETIEN
        defaultEntretienShouldBeFound("dateEntretien.in=" + DEFAULT_DATE_ENTRETIEN + "," + UPDATED_DATE_ENTRETIEN);

        // Get all the entretienList where dateEntretien equals to UPDATED_DATE_ENTRETIEN
        defaultEntretienShouldNotBeFound("dateEntretien.in=" + UPDATED_DATE_ENTRETIEN);
    }

    @Test
    @Transactional
    public void getAllEntretiensByDateEntretienIsNullOrNotNull() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where dateEntretien is not null
        defaultEntretienShouldBeFound("dateEntretien.specified=true");

        // Get all the entretienList where dateEntretien is null
        defaultEntretienShouldNotBeFound("dateEntretien.specified=false");
    }

    @Test
    @Transactional
    public void getAllEntretiensByDateEntretienIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where dateEntretien greater than or equals to DEFAULT_DATE_ENTRETIEN
        defaultEntretienShouldBeFound("dateEntretien.greaterOrEqualThan=" + DEFAULT_DATE_ENTRETIEN);

        // Get all the entretienList where dateEntretien greater than or equals to UPDATED_DATE_ENTRETIEN
        defaultEntretienShouldNotBeFound("dateEntretien.greaterOrEqualThan=" + UPDATED_DATE_ENTRETIEN);
    }

    @Test
    @Transactional
    public void getAllEntretiensByDateEntretienIsLessThanSomething() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList where dateEntretien less than or equals to DEFAULT_DATE_ENTRETIEN
        defaultEntretienShouldNotBeFound("dateEntretien.lessThan=" + DEFAULT_DATE_ENTRETIEN);

        // Get all the entretienList where dateEntretien less than or equals to UPDATED_DATE_ENTRETIEN
        defaultEntretienShouldBeFound("dateEntretien.lessThan=" + UPDATED_DATE_ENTRETIEN);
    }


    @Test
    @Transactional
    public void getAllEntretiensByCandidatureIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidature candidature = CandidatureResourceIntTest.createEntity(em);
        em.persist(candidature);
        em.flush();
        entretien.setCandidature(candidature);
        entretienRepository.saveAndFlush(entretien);
        Long candidatureId = candidature.getId();

        // Get all the entretienList where candidature equals to candidatureId
        defaultEntretienShouldBeFound("candidatureId.equals=" + candidatureId);

        // Get all the entretienList where candidature equals to candidatureId + 1
        defaultEntretienShouldNotBeFound("candidatureId.equals=" + (candidatureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEntretienShouldBeFound(String filter) throws Exception {
        restEntretienMockMvc.perform(get("/api/entretiens?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entretien.getId().intValue())))
            .andExpect(jsonPath("$.[*].cadre").value(hasItem(DEFAULT_CADRE.toString())))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT.toString())))
            .andExpect(jsonPath("$.[*].interlocuteur").value(hasItem(DEFAULT_INTERLOCUTEUR.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].dateEntretien").value(hasItem(DEFAULT_DATE_ENTRETIEN.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEntretienShouldNotBeFound(String filter) throws Exception {
        restEntretienMockMvc.perform(get("/api/entretiens?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingEntretien() throws Exception {
        // Get the entretien
        restEntretienMockMvc.perform(get("/api/entretiens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntretien() throws Exception {
        // Initialize the database
        entretienService.save(entretien);

        int databaseSizeBeforeUpdate = entretienRepository.findAll().size();

        // Update the entretien
        Entretien updatedEntretien = entretienRepository.findOne(entretien.getId());
        // Disconnect from session so that the updates on updatedEntretien are not directly saved in db
        em.detach(updatedEntretien);
        updatedEntretien
            .cadre(UPDATED_CADRE)
            .resultat(UPDATED_RESULTAT)
            .interlocuteur(UPDATED_INTERLOCUTEUR)
            .etat(UPDATED_ETAT)
            .dateEntretien(UPDATED_DATE_ENTRETIEN);

        restEntretienMockMvc.perform(put("/api/entretiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntretien)))
            .andExpect(status().isOk());

        // Validate the Entretien in the database
        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeUpdate);
        Entretien testEntretien = entretienList.get(entretienList.size() - 1);
        assertThat(testEntretien.getCadre()).isEqualTo(UPDATED_CADRE);
        assertThat(testEntretien.getResultat()).isEqualTo(UPDATED_RESULTAT);
        assertThat(testEntretien.getInterlocuteur()).isEqualTo(UPDATED_INTERLOCUTEUR);
        assertThat(testEntretien.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testEntretien.getDateEntretien()).isEqualTo(UPDATED_DATE_ENTRETIEN);
    }

    @Test
    @Transactional
    public void updateNonExistingEntretien() throws Exception {
        int databaseSizeBeforeUpdate = entretienRepository.findAll().size();

        // Create the Entretien

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntretienMockMvc.perform(put("/api/entretiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entretien)))
            .andExpect(status().isCreated());

        // Validate the Entretien in the database
        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntretien() throws Exception {
        // Initialize the database
        entretienService.save(entretien);

        int databaseSizeBeforeDelete = entretienRepository.findAll().size();

        // Get the entretien
        restEntretienMockMvc.perform(delete("/api/entretiens/{id}", entretien.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entretien.class);
        Entretien entretien1 = new Entretien();
        entretien1.setId(1L);
        Entretien entretien2 = new Entretien();
        entretien2.setId(entretien1.getId());
        assertThat(entretien1).isEqualTo(entretien2);
        entretien2.setId(2L);
        assertThat(entretien1).isNotEqualTo(entretien2);
        entretien1.setId(null);
        assertThat(entretien1).isNotEqualTo(entretien2);
    }
}
