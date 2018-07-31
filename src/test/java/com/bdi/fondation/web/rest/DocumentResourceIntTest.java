package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Document;
import com.bdi.fondation.domain.Candidature;
import com.bdi.fondation.repository.DocumentRepository;
import com.bdi.fondation.service.DocumentService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.DocumentCriteria;
import com.bdi.fondation.service.DocumentQueryService;

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
 * Test class for the DocumentResource REST controller.
 *
 * @see DocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class DocumentResourceIntTest {

    private static final LocalDate DEFAULT_DATE_ENREG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENREG = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIB = "AAAAAAAAAA";
    private static final String UPDATED_LIB = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_DOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_MODULE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_FICHIER = "AAAAAAAAAA";
    private static final String UPDATED_FICHIER = "BBBBBBBBBB";

    private static final String DEFAULT_TAIL = "AAAAAAAAAA";
    private static final String UPDATED_TAIL = "BBBBBBBBBB";

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentQueryService documentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocumentMockMvc;

    private Document document;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentResource documentResource = new DocumentResource(documentService, documentQueryService);
        this.restDocumentMockMvc = MockMvcBuilders.standaloneSetup(documentResource)
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
    public static Document createEntity(EntityManager em) {
        Document document = new Document()
            .dateEnreg(DEFAULT_DATE_ENREG)
            .lib(DEFAULT_LIB)
            .typeDocument(DEFAULT_TYPE_DOCUMENT)
            .module(DEFAULT_MODULE)
            .etat(DEFAULT_ETAT)
            .fichier(DEFAULT_FICHIER)
            .tail(DEFAULT_TAIL);
        return document;
    }

    @Before
    public void initTest() {
        document = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocument() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // Create the Document
        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isCreated());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate + 1);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDateEnreg()).isEqualTo(DEFAULT_DATE_ENREG);
        assertThat(testDocument.getLib()).isEqualTo(DEFAULT_LIB);
        assertThat(testDocument.getTypeDocument()).isEqualTo(DEFAULT_TYPE_DOCUMENT);
        assertThat(testDocument.getModule()).isEqualTo(DEFAULT_MODULE);
        assertThat(testDocument.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testDocument.getFichier()).isEqualTo(DEFAULT_FICHIER);
        assertThat(testDocument.getTail()).isEqualTo(DEFAULT_TAIL);
    }

    @Test
    @Transactional
    public void createDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // Create the Document with an existing ID
        document.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRepository.findAll().size();
        // set the field null
        document.setLib(null);

        // Create the Document, which fails.

        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeDocumentIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRepository.findAll().size();
        // set the field null
        document.setTypeDocument(null);

        // Create the Document, which fails.

        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModuleIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRepository.findAll().size();
        // set the field null
        document.setModule(null);

        // Create the Document, which fails.

        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc.perform(get("/api/documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEnreg").value(hasItem(DEFAULT_DATE_ENREG.toString())))
            .andExpect(jsonPath("$.[*].lib").value(hasItem(DEFAULT_LIB.toString())))
            .andExpect(jsonPath("$.[*].typeDocument").value(hasItem(DEFAULT_TYPE_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].fichier").value(hasItem(DEFAULT_FICHIER.toString())))
            .andExpect(jsonPath("$.[*].tail").value(hasItem(DEFAULT_TAIL.toString())));
    }

    @Test
    @Transactional
    public void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.dateEnreg").value(DEFAULT_DATE_ENREG.toString()))
            .andExpect(jsonPath("$.lib").value(DEFAULT_LIB.toString()))
            .andExpect(jsonPath("$.typeDocument").value(DEFAULT_TYPE_DOCUMENT.toString()))
            .andExpect(jsonPath("$.module").value(DEFAULT_MODULE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.fichier").value(DEFAULT_FICHIER.toString()))
            .andExpect(jsonPath("$.tail").value(DEFAULT_TAIL.toString()));
    }

    @Test
    @Transactional
    public void getAllDocumentsByDateEnregIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where dateEnreg equals to DEFAULT_DATE_ENREG
        defaultDocumentShouldBeFound("dateEnreg.equals=" + DEFAULT_DATE_ENREG);

        // Get all the documentList where dateEnreg equals to UPDATED_DATE_ENREG
        defaultDocumentShouldNotBeFound("dateEnreg.equals=" + UPDATED_DATE_ENREG);
    }

    @Test
    @Transactional
    public void getAllDocumentsByDateEnregIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where dateEnreg in DEFAULT_DATE_ENREG or UPDATED_DATE_ENREG
        defaultDocumentShouldBeFound("dateEnreg.in=" + DEFAULT_DATE_ENREG + "," + UPDATED_DATE_ENREG);

        // Get all the documentList where dateEnreg equals to UPDATED_DATE_ENREG
        defaultDocumentShouldNotBeFound("dateEnreg.in=" + UPDATED_DATE_ENREG);
    }

    @Test
    @Transactional
    public void getAllDocumentsByDateEnregIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where dateEnreg is not null
        defaultDocumentShouldBeFound("dateEnreg.specified=true");

        // Get all the documentList where dateEnreg is null
        defaultDocumentShouldNotBeFound("dateEnreg.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentsByDateEnregIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where dateEnreg greater than or equals to DEFAULT_DATE_ENREG
        defaultDocumentShouldBeFound("dateEnreg.greaterOrEqualThan=" + DEFAULT_DATE_ENREG);

        // Get all the documentList where dateEnreg greater than or equals to UPDATED_DATE_ENREG
        defaultDocumentShouldNotBeFound("dateEnreg.greaterOrEqualThan=" + UPDATED_DATE_ENREG);
    }

    @Test
    @Transactional
    public void getAllDocumentsByDateEnregIsLessThanSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where dateEnreg less than or equals to DEFAULT_DATE_ENREG
        defaultDocumentShouldNotBeFound("dateEnreg.lessThan=" + DEFAULT_DATE_ENREG);

        // Get all the documentList where dateEnreg less than or equals to UPDATED_DATE_ENREG
        defaultDocumentShouldBeFound("dateEnreg.lessThan=" + UPDATED_DATE_ENREG);
    }


    @Test
    @Transactional
    public void getAllDocumentsByLibIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where lib equals to DEFAULT_LIB
        defaultDocumentShouldBeFound("lib.equals=" + DEFAULT_LIB);

        // Get all the documentList where lib equals to UPDATED_LIB
        defaultDocumentShouldNotBeFound("lib.equals=" + UPDATED_LIB);
    }

    @Test
    @Transactional
    public void getAllDocumentsByLibIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where lib in DEFAULT_LIB or UPDATED_LIB
        defaultDocumentShouldBeFound("lib.in=" + DEFAULT_LIB + "," + UPDATED_LIB);

        // Get all the documentList where lib equals to UPDATED_LIB
        defaultDocumentShouldNotBeFound("lib.in=" + UPDATED_LIB);
    }

    @Test
    @Transactional
    public void getAllDocumentsByLibIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where lib is not null
        defaultDocumentShouldBeFound("lib.specified=true");

        // Get all the documentList where lib is null
        defaultDocumentShouldNotBeFound("lib.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentsByTypeDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where typeDocument equals to DEFAULT_TYPE_DOCUMENT
        defaultDocumentShouldBeFound("typeDocument.equals=" + DEFAULT_TYPE_DOCUMENT);

        // Get all the documentList where typeDocument equals to UPDATED_TYPE_DOCUMENT
        defaultDocumentShouldNotBeFound("typeDocument.equals=" + UPDATED_TYPE_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllDocumentsByTypeDocumentIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where typeDocument in DEFAULT_TYPE_DOCUMENT or UPDATED_TYPE_DOCUMENT
        defaultDocumentShouldBeFound("typeDocument.in=" + DEFAULT_TYPE_DOCUMENT + "," + UPDATED_TYPE_DOCUMENT);

        // Get all the documentList where typeDocument equals to UPDATED_TYPE_DOCUMENT
        defaultDocumentShouldNotBeFound("typeDocument.in=" + UPDATED_TYPE_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllDocumentsByTypeDocumentIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where typeDocument is not null
        defaultDocumentShouldBeFound("typeDocument.specified=true");

        // Get all the documentList where typeDocument is null
        defaultDocumentShouldNotBeFound("typeDocument.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentsByModuleIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where module equals to DEFAULT_MODULE
        defaultDocumentShouldBeFound("module.equals=" + DEFAULT_MODULE);

        // Get all the documentList where module equals to UPDATED_MODULE
        defaultDocumentShouldNotBeFound("module.equals=" + UPDATED_MODULE);
    }

    @Test
    @Transactional
    public void getAllDocumentsByModuleIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where module in DEFAULT_MODULE or UPDATED_MODULE
        defaultDocumentShouldBeFound("module.in=" + DEFAULT_MODULE + "," + UPDATED_MODULE);

        // Get all the documentList where module equals to UPDATED_MODULE
        defaultDocumentShouldNotBeFound("module.in=" + UPDATED_MODULE);
    }

    @Test
    @Transactional
    public void getAllDocumentsByModuleIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where module is not null
        defaultDocumentShouldBeFound("module.specified=true");

        // Get all the documentList where module is null
        defaultDocumentShouldNotBeFound("module.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentsByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where etat equals to DEFAULT_ETAT
        defaultDocumentShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the documentList where etat equals to UPDATED_ETAT
        defaultDocumentShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllDocumentsByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultDocumentShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the documentList where etat equals to UPDATED_ETAT
        defaultDocumentShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllDocumentsByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where etat is not null
        defaultDocumentShouldBeFound("etat.specified=true");

        // Get all the documentList where etat is null
        defaultDocumentShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentsByFichierIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fichier equals to DEFAULT_FICHIER
        defaultDocumentShouldBeFound("fichier.equals=" + DEFAULT_FICHIER);

        // Get all the documentList where fichier equals to UPDATED_FICHIER
        defaultDocumentShouldNotBeFound("fichier.equals=" + UPDATED_FICHIER);
    }

    @Test
    @Transactional
    public void getAllDocumentsByFichierIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fichier in DEFAULT_FICHIER or UPDATED_FICHIER
        defaultDocumentShouldBeFound("fichier.in=" + DEFAULT_FICHIER + "," + UPDATED_FICHIER);

        // Get all the documentList where fichier equals to UPDATED_FICHIER
        defaultDocumentShouldNotBeFound("fichier.in=" + UPDATED_FICHIER);
    }

    @Test
    @Transactional
    public void getAllDocumentsByFichierIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fichier is not null
        defaultDocumentShouldBeFound("fichier.specified=true");

        // Get all the documentList where fichier is null
        defaultDocumentShouldNotBeFound("fichier.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentsByTailIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where tail equals to DEFAULT_TAIL
        defaultDocumentShouldBeFound("tail.equals=" + DEFAULT_TAIL);

        // Get all the documentList where tail equals to UPDATED_TAIL
        defaultDocumentShouldNotBeFound("tail.equals=" + UPDATED_TAIL);
    }

    @Test
    @Transactional
    public void getAllDocumentsByTailIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where tail in DEFAULT_TAIL or UPDATED_TAIL
        defaultDocumentShouldBeFound("tail.in=" + DEFAULT_TAIL + "," + UPDATED_TAIL);

        // Get all the documentList where tail equals to UPDATED_TAIL
        defaultDocumentShouldNotBeFound("tail.in=" + UPDATED_TAIL);
    }

    @Test
    @Transactional
    public void getAllDocumentsByTailIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where tail is not null
        defaultDocumentShouldBeFound("tail.specified=true");

        // Get all the documentList where tail is null
        defaultDocumentShouldNotBeFound("tail.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentsByCandidatureIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidature candidature = CandidatureResourceIntTest.createEntity(em);
        em.persist(candidature);
        em.flush();
        document.setCandidature(candidature);
        documentRepository.saveAndFlush(document);
        Long candidatureId = candidature.getId();

        // Get all the documentList where candidature equals to candidatureId
        defaultDocumentShouldBeFound("candidatureId.equals=" + candidatureId);

        // Get all the documentList where candidature equals to candidatureId + 1
        defaultDocumentShouldNotBeFound("candidatureId.equals=" + (candidatureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDocumentShouldBeFound(String filter) throws Exception {
        restDocumentMockMvc.perform(get("/api/documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEnreg").value(hasItem(DEFAULT_DATE_ENREG.toString())))
            .andExpect(jsonPath("$.[*].lib").value(hasItem(DEFAULT_LIB.toString())))
            .andExpect(jsonPath("$.[*].typeDocument").value(hasItem(DEFAULT_TYPE_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].fichier").value(hasItem(DEFAULT_FICHIER.toString())))
            .andExpect(jsonPath("$.[*].tail").value(hasItem(DEFAULT_TAIL.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDocumentShouldNotBeFound(String filter) throws Exception {
        restDocumentMockMvc.perform(get("/api/documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocument() throws Exception {
        // Initialize the database
        documentService.save(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document
        Document updatedDocument = documentRepository.findOne(document.getId());
        // Disconnect from session so that the updates on updatedDocument are not directly saved in db
        em.detach(updatedDocument);
        updatedDocument
            .dateEnreg(UPDATED_DATE_ENREG)
            .lib(UPDATED_LIB)
            .typeDocument(UPDATED_TYPE_DOCUMENT)
            .module(UPDATED_MODULE)
            .etat(UPDATED_ETAT)
            .fichier(UPDATED_FICHIER)
            .tail(UPDATED_TAIL);

        restDocumentMockMvc.perform(put("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocument)))
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDateEnreg()).isEqualTo(UPDATED_DATE_ENREG);
        assertThat(testDocument.getLib()).isEqualTo(UPDATED_LIB);
        assertThat(testDocument.getTypeDocument()).isEqualTo(UPDATED_TYPE_DOCUMENT);
        assertThat(testDocument.getModule()).isEqualTo(UPDATED_MODULE);
        assertThat(testDocument.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testDocument.getFichier()).isEqualTo(UPDATED_FICHIER);
        assertThat(testDocument.getTail()).isEqualTo(UPDATED_TAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Create the Document

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDocumentMockMvc.perform(put("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isCreated());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDocument() throws Exception {
        // Initialize the database
        documentService.save(document);

        int databaseSizeBeforeDelete = documentRepository.findAll().size();

        // Get the document
        restDocumentMockMvc.perform(delete("/api/documents/{id}", document.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Document.class);
        Document document1 = new Document();
        document1.setId(1L);
        Document document2 = new Document();
        document2.setId(document1.getId());
        assertThat(document1).isEqualTo(document2);
        document2.setId(2L);
        assertThat(document1).isNotEqualTo(document2);
        document1.setId(null);
        assertThat(document1).isNotEqualTo(document2);
    }
}
