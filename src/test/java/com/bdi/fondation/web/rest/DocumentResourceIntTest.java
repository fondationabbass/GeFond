package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Document;
import com.bdi.fondation.repository.DocumentRepository;
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
        final DocumentResource documentResource = new DocumentResource(documentRepository);
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
    public void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);
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
        documentRepository.saveAndFlush(document);
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
