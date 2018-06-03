package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.repository.CandidatRepository;
import com.bdi.fondation.service.CandidatService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.CandidatCriteria;
import com.bdi.fondation.service.CandidatQueryService;

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
 * Test class for the CandidatResource REST controller.
 *
 * @see CandidatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class CandidatResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_SITUATION = "AAAAAAAAAA";
    private static final String UPDATED_SITUATION = "BBBBBBBBBB";

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private CandidatService candidatService;

    @Autowired
    private CandidatQueryService candidatQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandidatMockMvc;

    private Candidat candidat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidatResource candidatResource = new CandidatResource(candidatService, candidatQueryService);
        this.restCandidatMockMvc = MockMvcBuilders.standaloneSetup(candidatResource)
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
    public static Candidat createEntity(EntityManager em) {
        Candidat candidat = new Candidat()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .adresse(DEFAULT_ADRESSE)
            .tel(DEFAULT_TEL)
            .situation(DEFAULT_SITUATION);
        return candidat;
    }

    @Before
    public void initTest() {
        candidat = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidat() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();

        // Create the Candidat
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isCreated());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate + 1);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCandidat.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testCandidat.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testCandidat.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testCandidat.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testCandidat.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testCandidat.getSituation()).isEqualTo(DEFAULT_SITUATION);
    }

    @Test
    @Transactional
    public void createCandidatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();

        // Create the Candidat with an existing ID
        candidat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCandidats() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList
        restCandidatMockMvc.perform(get("/api/candidats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
            .andExpect(jsonPath("$.[*].situation").value(hasItem(DEFAULT_SITUATION.toString())));
    }

    @Test
    @Transactional
    public void getCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", candidat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidat.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()))
            .andExpect(jsonPath("$.situation").value(DEFAULT_SITUATION.toString()));
    }

    @Test
    @Transactional
    public void getAllCandidatsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where nom equals to DEFAULT_NOM
        defaultCandidatShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the candidatList where nom equals to UPDATED_NOM
        defaultCandidatShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultCandidatShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the candidatList where nom equals to UPDATED_NOM
        defaultCandidatShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where nom is not null
        defaultCandidatShouldBeFound("nom.specified=true");

        // Get all the candidatList where nom is null
        defaultCandidatShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatsByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where prenom equals to DEFAULT_PRENOM
        defaultCandidatShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the candidatList where prenom equals to UPDATED_PRENOM
        defaultCandidatShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultCandidatShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the candidatList where prenom equals to UPDATED_PRENOM
        defaultCandidatShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where prenom is not null
        defaultCandidatShouldBeFound("prenom.specified=true");

        // Get all the candidatList where prenom is null
        defaultCandidatShouldNotBeFound("prenom.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatsByDateNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where dateNaissance equals to DEFAULT_DATE_NAISSANCE
        defaultCandidatShouldBeFound("dateNaissance.equals=" + DEFAULT_DATE_NAISSANCE);

        // Get all the candidatList where dateNaissance equals to UPDATED_DATE_NAISSANCE
        defaultCandidatShouldNotBeFound("dateNaissance.equals=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByDateNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where dateNaissance in DEFAULT_DATE_NAISSANCE or UPDATED_DATE_NAISSANCE
        defaultCandidatShouldBeFound("dateNaissance.in=" + DEFAULT_DATE_NAISSANCE + "," + UPDATED_DATE_NAISSANCE);

        // Get all the candidatList where dateNaissance equals to UPDATED_DATE_NAISSANCE
        defaultCandidatShouldNotBeFound("dateNaissance.in=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByDateNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where dateNaissance is not null
        defaultCandidatShouldBeFound("dateNaissance.specified=true");

        // Get all the candidatList where dateNaissance is null
        defaultCandidatShouldNotBeFound("dateNaissance.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatsByDateNaissanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where dateNaissance greater than or equals to DEFAULT_DATE_NAISSANCE
        defaultCandidatShouldBeFound("dateNaissance.greaterOrEqualThan=" + DEFAULT_DATE_NAISSANCE);

        // Get all the candidatList where dateNaissance greater than or equals to UPDATED_DATE_NAISSANCE
        defaultCandidatShouldNotBeFound("dateNaissance.greaterOrEqualThan=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByDateNaissanceIsLessThanSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where dateNaissance less than or equals to DEFAULT_DATE_NAISSANCE
        defaultCandidatShouldNotBeFound("dateNaissance.lessThan=" + DEFAULT_DATE_NAISSANCE);

        // Get all the candidatList where dateNaissance less than or equals to UPDATED_DATE_NAISSANCE
        defaultCandidatShouldBeFound("dateNaissance.lessThan=" + UPDATED_DATE_NAISSANCE);
    }


    @Test
    @Transactional
    public void getAllCandidatsByLieuNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where lieuNaissance equals to DEFAULT_LIEU_NAISSANCE
        defaultCandidatShouldBeFound("lieuNaissance.equals=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the candidatList where lieuNaissance equals to UPDATED_LIEU_NAISSANCE
        defaultCandidatShouldNotBeFound("lieuNaissance.equals=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByLieuNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where lieuNaissance in DEFAULT_LIEU_NAISSANCE or UPDATED_LIEU_NAISSANCE
        defaultCandidatShouldBeFound("lieuNaissance.in=" + DEFAULT_LIEU_NAISSANCE + "," + UPDATED_LIEU_NAISSANCE);

        // Get all the candidatList where lieuNaissance equals to UPDATED_LIEU_NAISSANCE
        defaultCandidatShouldNotBeFound("lieuNaissance.in=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByLieuNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where lieuNaissance is not null
        defaultCandidatShouldBeFound("lieuNaissance.specified=true");

        // Get all the candidatList where lieuNaissance is null
        defaultCandidatShouldNotBeFound("lieuNaissance.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatsByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where adresse equals to DEFAULT_ADRESSE
        defaultCandidatShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the candidatList where adresse equals to UPDATED_ADRESSE
        defaultCandidatShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultCandidatShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the candidatList where adresse equals to UPDATED_ADRESSE
        defaultCandidatShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where adresse is not null
        defaultCandidatShouldBeFound("adresse.specified=true");

        // Get all the candidatList where adresse is null
        defaultCandidatShouldNotBeFound("adresse.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatsByTelIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where tel equals to DEFAULT_TEL
        defaultCandidatShouldBeFound("tel.equals=" + DEFAULT_TEL);

        // Get all the candidatList where tel equals to UPDATED_TEL
        defaultCandidatShouldNotBeFound("tel.equals=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllCandidatsByTelIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where tel in DEFAULT_TEL or UPDATED_TEL
        defaultCandidatShouldBeFound("tel.in=" + DEFAULT_TEL + "," + UPDATED_TEL);

        // Get all the candidatList where tel equals to UPDATED_TEL
        defaultCandidatShouldNotBeFound("tel.in=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllCandidatsByTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where tel is not null
        defaultCandidatShouldBeFound("tel.specified=true");

        // Get all the candidatList where tel is null
        defaultCandidatShouldNotBeFound("tel.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatsBySituationIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where situation equals to DEFAULT_SITUATION
        defaultCandidatShouldBeFound("situation.equals=" + DEFAULT_SITUATION);

        // Get all the candidatList where situation equals to UPDATED_SITUATION
        defaultCandidatShouldNotBeFound("situation.equals=" + UPDATED_SITUATION);
    }

    @Test
    @Transactional
    public void getAllCandidatsBySituationIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where situation in DEFAULT_SITUATION or UPDATED_SITUATION
        defaultCandidatShouldBeFound("situation.in=" + DEFAULT_SITUATION + "," + UPDATED_SITUATION);

        // Get all the candidatList where situation equals to UPDATED_SITUATION
        defaultCandidatShouldNotBeFound("situation.in=" + UPDATED_SITUATION);
    }

    @Test
    @Transactional
    public void getAllCandidatsBySituationIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where situation is not null
        defaultCandidatShouldBeFound("situation.specified=true");

        // Get all the candidatList where situation is null
        defaultCandidatShouldNotBeFound("situation.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCandidatShouldBeFound(String filter) throws Exception {
        restCandidatMockMvc.perform(get("/api/candidats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
            .andExpect(jsonPath("$.[*].situation").value(hasItem(DEFAULT_SITUATION.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCandidatShouldNotBeFound(String filter) throws Exception {
        restCandidatMockMvc.perform(get("/api/candidats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCandidat() throws Exception {
        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidat() throws Exception {
        // Initialize the database
        candidatService.save(candidat);

        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // Update the candidat
        Candidat updatedCandidat = candidatRepository.findOne(candidat.getId());
        // Disconnect from session so that the updates on updatedCandidat are not directly saved in db
        em.detach(updatedCandidat);
        updatedCandidat
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .adresse(UPDATED_ADRESSE)
            .tel(UPDATED_TEL)
            .situation(UPDATED_SITUATION);

        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandidat)))
            .andExpect(status().isOk());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCandidat.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCandidat.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testCandidat.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testCandidat.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testCandidat.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testCandidat.getSituation()).isEqualTo(UPDATED_SITUATION);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidat() throws Exception {
        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // Create the Candidat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isCreated());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCandidat() throws Exception {
        // Initialize the database
        candidatService.save(candidat);

        int databaseSizeBeforeDelete = candidatRepository.findAll().size();

        // Get the candidat
        restCandidatMockMvc.perform(delete("/api/candidats/{id}", candidat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidat.class);
        Candidat candidat1 = new Candidat();
        candidat1.setId(1L);
        Candidat candidat2 = new Candidat();
        candidat2.setId(candidat1.getId());
        assertThat(candidat1).isEqualTo(candidat2);
        candidat2.setId(2L);
        assertThat(candidat1).isNotEqualTo(candidat2);
        candidat1.setId(null);
        assertThat(candidat1).isNotEqualTo(candidat2);
    }
}
