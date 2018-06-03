package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Entretien;
import com.bdi.fondation.repository.EntretienRepository;
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
        final EntretienResource entretienResource = new EntretienResource(entretienRepository);
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
    public void getNonExistingEntretien() throws Exception {
        // Get the entretien
        restEntretienMockMvc.perform(get("/api/entretiens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntretien() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);
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
        entretienRepository.saveAndFlush(entretien);
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
