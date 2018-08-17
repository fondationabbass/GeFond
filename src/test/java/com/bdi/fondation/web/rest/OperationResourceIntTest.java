package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Operation;
import com.bdi.fondation.domain.Compte;
import com.bdi.fondation.domain.Compte;
import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.domain.Caisse;
import com.bdi.fondation.domain.Echeance;
import com.bdi.fondation.repository.OperationRepository;
import com.bdi.fondation.service.OperationService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.OperationCriteria;
import com.bdi.fondation.service.OperationQueryService;

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
 * Test class for the OperationResource REST controller.
 *
 * @see OperationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class OperationResourceIntTest {

    private static final LocalDate DEFAULT_DATE_OPERATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OPERATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TYPE_OPERATION = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_OPERATION = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_MOYEN_PAIEMENT = "AAAAAAAAAA";
    private static final String UPDATED_MOYEN_PAIEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private OperationService operationService;

    @Autowired
    private OperationQueryService operationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperationMockMvc;

    private Operation operation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperationResource operationResource = new OperationResource(operationService, operationQueryService);
        this.restOperationMockMvc = MockMvcBuilders.standaloneSetup(operationResource)
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
    public static Operation createEntity(EntityManager em) {
        Operation operation = new Operation()
            .dateOperation(DEFAULT_DATE_OPERATION)
            .typeOperation(DEFAULT_TYPE_OPERATION)
            .montant(DEFAULT_MONTANT)
            .etat(DEFAULT_ETAT)
            .moyenPaiement(DEFAULT_MOYEN_PAIEMENT)
            .commentaire(DEFAULT_COMMENTAIRE)
            .description(DEFAULT_DESCRIPTION);
        return operation;
    }

    @Before
    public void initTest() {
        operation = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperation() throws Exception {
        int databaseSizeBeforeCreate = operationRepository.findAll().size();

        // Create the Operation
        restOperationMockMvc.perform(post("/api/operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operation)))
            .andExpect(status().isCreated());

        // Validate the Operation in the database
        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeCreate + 1);
        Operation testOperation = operationList.get(operationList.size() - 1);
        assertThat(testOperation.getDateOperation()).isEqualTo(DEFAULT_DATE_OPERATION);
        assertThat(testOperation.getTypeOperation()).isEqualTo(DEFAULT_TYPE_OPERATION);
        assertThat(testOperation.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testOperation.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testOperation.getMoyenPaiement()).isEqualTo(DEFAULT_MOYEN_PAIEMENT);
        assertThat(testOperation.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testOperation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOperationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operationRepository.findAll().size();

        // Create the Operation with an existing ID
        operation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationMockMvc.perform(post("/api/operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operation)))
            .andExpect(status().isBadRequest());

        // Validate the Operation in the database
        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeOperationIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationRepository.findAll().size();
        // set the field null
        operation.setTypeOperation(null);

        // Create the Operation, which fails.

        restOperationMockMvc.perform(post("/api/operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operation)))
            .andExpect(status().isBadRequest());

        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationRepository.findAll().size();
        // set the field null
        operation.setMontant(null);

        // Create the Operation, which fails.

        restOperationMockMvc.perform(post("/api/operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operation)))
            .andExpect(status().isBadRequest());

        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperations() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList
        restOperationMockMvc.perform(get("/api/operations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOperation").value(hasItem(DEFAULT_DATE_OPERATION.toString())))
            .andExpect(jsonPath("$.[*].typeOperation").value(hasItem(DEFAULT_TYPE_OPERATION.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].moyenPaiement").value(hasItem(DEFAULT_MOYEN_PAIEMENT.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getOperation() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get the operation
        restOperationMockMvc.perform(get("/api/operations/{id}", operation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operation.getId().intValue()))
            .andExpect(jsonPath("$.dateOperation").value(DEFAULT_DATE_OPERATION.toString()))
            .andExpect(jsonPath("$.typeOperation").value(DEFAULT_TYPE_OPERATION.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.moyenPaiement").value(DEFAULT_MOYEN_PAIEMENT.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllOperationsByDateOperationIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where dateOperation equals to DEFAULT_DATE_OPERATION
        defaultOperationShouldBeFound("dateOperation.equals=" + DEFAULT_DATE_OPERATION);

        // Get all the operationList where dateOperation equals to UPDATED_DATE_OPERATION
        defaultOperationShouldNotBeFound("dateOperation.equals=" + UPDATED_DATE_OPERATION);
    }

    @Test
    @Transactional
    public void getAllOperationsByDateOperationIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where dateOperation in DEFAULT_DATE_OPERATION or UPDATED_DATE_OPERATION
        defaultOperationShouldBeFound("dateOperation.in=" + DEFAULT_DATE_OPERATION + "," + UPDATED_DATE_OPERATION);

        // Get all the operationList where dateOperation equals to UPDATED_DATE_OPERATION
        defaultOperationShouldNotBeFound("dateOperation.in=" + UPDATED_DATE_OPERATION);
    }

    @Test
    @Transactional
    public void getAllOperationsByDateOperationIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where dateOperation is not null
        defaultOperationShouldBeFound("dateOperation.specified=true");

        // Get all the operationList where dateOperation is null
        defaultOperationShouldNotBeFound("dateOperation.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByDateOperationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where dateOperation greater than or equals to DEFAULT_DATE_OPERATION
        defaultOperationShouldBeFound("dateOperation.greaterOrEqualThan=" + DEFAULT_DATE_OPERATION);

        // Get all the operationList where dateOperation greater than or equals to UPDATED_DATE_OPERATION
        defaultOperationShouldNotBeFound("dateOperation.greaterOrEqualThan=" + UPDATED_DATE_OPERATION);
    }

    @Test
    @Transactional
    public void getAllOperationsByDateOperationIsLessThanSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where dateOperation less than or equals to DEFAULT_DATE_OPERATION
        defaultOperationShouldNotBeFound("dateOperation.lessThan=" + DEFAULT_DATE_OPERATION);

        // Get all the operationList where dateOperation less than or equals to UPDATED_DATE_OPERATION
        defaultOperationShouldBeFound("dateOperation.lessThan=" + UPDATED_DATE_OPERATION);
    }


    @Test
    @Transactional
    public void getAllOperationsByTypeOperationIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where typeOperation equals to DEFAULT_TYPE_OPERATION
        defaultOperationShouldBeFound("typeOperation.equals=" + DEFAULT_TYPE_OPERATION);

        // Get all the operationList where typeOperation equals to UPDATED_TYPE_OPERATION
        defaultOperationShouldNotBeFound("typeOperation.equals=" + UPDATED_TYPE_OPERATION);
    }

    @Test
    @Transactional
    public void getAllOperationsByTypeOperationIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where typeOperation in DEFAULT_TYPE_OPERATION or UPDATED_TYPE_OPERATION
        defaultOperationShouldBeFound("typeOperation.in=" + DEFAULT_TYPE_OPERATION + "," + UPDATED_TYPE_OPERATION);

        // Get all the operationList where typeOperation equals to UPDATED_TYPE_OPERATION
        defaultOperationShouldNotBeFound("typeOperation.in=" + UPDATED_TYPE_OPERATION);
    }

    @Test
    @Transactional
    public void getAllOperationsByTypeOperationIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where typeOperation is not null
        defaultOperationShouldBeFound("typeOperation.specified=true");

        // Get all the operationList where typeOperation is null
        defaultOperationShouldNotBeFound("typeOperation.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByMontantIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where montant equals to DEFAULT_MONTANT
        defaultOperationShouldBeFound("montant.equals=" + DEFAULT_MONTANT);

        // Get all the operationList where montant equals to UPDATED_MONTANT
        defaultOperationShouldNotBeFound("montant.equals=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void getAllOperationsByMontantIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where montant in DEFAULT_MONTANT or UPDATED_MONTANT
        defaultOperationShouldBeFound("montant.in=" + DEFAULT_MONTANT + "," + UPDATED_MONTANT);

        // Get all the operationList where montant equals to UPDATED_MONTANT
        defaultOperationShouldNotBeFound("montant.in=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void getAllOperationsByMontantIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where montant is not null
        defaultOperationShouldBeFound("montant.specified=true");

        // Get all the operationList where montant is null
        defaultOperationShouldNotBeFound("montant.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where etat equals to DEFAULT_ETAT
        defaultOperationShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the operationList where etat equals to UPDATED_ETAT
        defaultOperationShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllOperationsByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultOperationShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the operationList where etat equals to UPDATED_ETAT
        defaultOperationShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllOperationsByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where etat is not null
        defaultOperationShouldBeFound("etat.specified=true");

        // Get all the operationList where etat is null
        defaultOperationShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByMoyenPaiementIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where moyenPaiement equals to DEFAULT_MOYEN_PAIEMENT
        defaultOperationShouldBeFound("moyenPaiement.equals=" + DEFAULT_MOYEN_PAIEMENT);

        // Get all the operationList where moyenPaiement equals to UPDATED_MOYEN_PAIEMENT
        defaultOperationShouldNotBeFound("moyenPaiement.equals=" + UPDATED_MOYEN_PAIEMENT);
    }

    @Test
    @Transactional
    public void getAllOperationsByMoyenPaiementIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where moyenPaiement in DEFAULT_MOYEN_PAIEMENT or UPDATED_MOYEN_PAIEMENT
        defaultOperationShouldBeFound("moyenPaiement.in=" + DEFAULT_MOYEN_PAIEMENT + "," + UPDATED_MOYEN_PAIEMENT);

        // Get all the operationList where moyenPaiement equals to UPDATED_MOYEN_PAIEMENT
        defaultOperationShouldNotBeFound("moyenPaiement.in=" + UPDATED_MOYEN_PAIEMENT);
    }

    @Test
    @Transactional
    public void getAllOperationsByMoyenPaiementIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where moyenPaiement is not null
        defaultOperationShouldBeFound("moyenPaiement.specified=true");

        // Get all the operationList where moyenPaiement is null
        defaultOperationShouldNotBeFound("moyenPaiement.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByCommentaireIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where commentaire equals to DEFAULT_COMMENTAIRE
        defaultOperationShouldBeFound("commentaire.equals=" + DEFAULT_COMMENTAIRE);

        // Get all the operationList where commentaire equals to UPDATED_COMMENTAIRE
        defaultOperationShouldNotBeFound("commentaire.equals=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllOperationsByCommentaireIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where commentaire in DEFAULT_COMMENTAIRE or UPDATED_COMMENTAIRE
        defaultOperationShouldBeFound("commentaire.in=" + DEFAULT_COMMENTAIRE + "," + UPDATED_COMMENTAIRE);

        // Get all the operationList where commentaire equals to UPDATED_COMMENTAIRE
        defaultOperationShouldNotBeFound("commentaire.in=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllOperationsByCommentaireIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where commentaire is not null
        defaultOperationShouldBeFound("commentaire.specified=true");

        // Get all the operationList where commentaire is null
        defaultOperationShouldNotBeFound("commentaire.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where description equals to DEFAULT_DESCRIPTION
        defaultOperationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the operationList where description equals to UPDATED_DESCRIPTION
        defaultOperationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOperationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOperationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the operationList where description equals to UPDATED_DESCRIPTION
        defaultOperationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOperationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where description is not null
        defaultOperationShouldBeFound("description.specified=true");

        // Get all the operationList where description is null
        defaultOperationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByCompteOriginIsEqualToSomething() throws Exception {
        // Initialize the database
        Compte compteOrigin = CompteResourceIntTest.createEntity(em);
        em.persist(compteOrigin);
        em.flush();
        operation.setCompteOrigin(compteOrigin);
        operationRepository.saveAndFlush(operation);
        Long compteOriginId = compteOrigin.getId();

        // Get all the operationList where compteOrigin equals to compteOriginId
        defaultOperationShouldBeFound("compteOriginId.equals=" + compteOriginId);

        // Get all the operationList where compteOrigin equals to compteOriginId + 1
        defaultOperationShouldNotBeFound("compteOriginId.equals=" + (compteOriginId + 1));
    }


    @Test
    @Transactional
    public void getAllOperationsByCompteDestinataireIsEqualToSomething() throws Exception {
        // Initialize the database
        Compte compteDestinataire = CompteResourceIntTest.createEntity(em);
        em.persist(compteDestinataire);
        em.flush();
        operation.setCompteDestinataire(compteDestinataire);
        operationRepository.saveAndFlush(operation);
        Long compteDestinataireId = compteDestinataire.getId();

        // Get all the operationList where compteDestinataire equals to compteDestinataireId
        defaultOperationShouldBeFound("compteDestinataireId.equals=" + compteDestinataireId);

        // Get all the operationList where compteDestinataire equals to compteDestinataireId + 1
        defaultOperationShouldNotBeFound("compteDestinataireId.equals=" + (compteDestinataireId + 1));
    }


    @Test
    @Transactional
    public void getAllOperationsByPretIsEqualToSomething() throws Exception {
        // Initialize the database
        Pret pret = PretResourceIntTest.createEntity(em);
        em.persist(pret);
        em.flush();
        operation.setPret(pret);
        operationRepository.saveAndFlush(operation);
        Long pretId = pret.getId();

        // Get all the operationList where pret equals to pretId
        defaultOperationShouldBeFound("pretId.equals=" + pretId);

        // Get all the operationList where pret equals to pretId + 1
        defaultOperationShouldNotBeFound("pretId.equals=" + (pretId + 1));
    }


    @Test
    @Transactional
    public void getAllOperationsByCaisseIsEqualToSomething() throws Exception {
        // Initialize the database
        Caisse caisse = CaisseResourceIntTest.createEntity(em);
        em.persist(caisse);
        em.flush();
        operation.setCaisse(caisse);
        operationRepository.saveAndFlush(operation);
        Long caisseId = caisse.getId();

        // Get all the operationList where caisse equals to caisseId
        defaultOperationShouldBeFound("caisseId.equals=" + caisseId);

        // Get all the operationList where caisse equals to caisseId + 1
        defaultOperationShouldNotBeFound("caisseId.equals=" + (caisseId + 1));
    }


    @Test
    @Transactional
    public void getAllOperationsByEcheanceIsEqualToSomething() throws Exception {
        // Initialize the database
        Echeance echeance = EcheanceResourceIntTest.createEntity(em);
        em.persist(echeance);
        em.flush();
        operation.addEcheance(echeance);
        operationRepository.saveAndFlush(operation);
        Long echeanceId = echeance.getId();

        // Get all the operationList where echeance equals to echeanceId
        defaultOperationShouldBeFound("echeanceId.equals=" + echeanceId);

        // Get all the operationList where echeance equals to echeanceId + 1
        defaultOperationShouldNotBeFound("echeanceId.equals=" + (echeanceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOperationShouldBeFound(String filter) throws Exception {
        restOperationMockMvc.perform(get("/api/operations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOperation").value(hasItem(DEFAULT_DATE_OPERATION.toString())))
            .andExpect(jsonPath("$.[*].typeOperation").value(hasItem(DEFAULT_TYPE_OPERATION.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].moyenPaiement").value(hasItem(DEFAULT_MOYEN_PAIEMENT.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOperationShouldNotBeFound(String filter) throws Exception {
        restOperationMockMvc.perform(get("/api/operations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingOperation() throws Exception {
        // Get the operation
        restOperationMockMvc.perform(get("/api/operations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperation() throws Exception {
        // Initialize the database
        operationService.save(operation);

        int databaseSizeBeforeUpdate = operationRepository.findAll().size();

        // Update the operation
        Operation updatedOperation = operationRepository.findOne(operation.getId());
        // Disconnect from session so that the updates on updatedOperation are not directly saved in db
        em.detach(updatedOperation);
        updatedOperation
            .dateOperation(UPDATED_DATE_OPERATION)
            .typeOperation(UPDATED_TYPE_OPERATION)
            .montant(UPDATED_MONTANT)
            .etat(UPDATED_ETAT)
            .moyenPaiement(UPDATED_MOYEN_PAIEMENT)
            .commentaire(UPDATED_COMMENTAIRE)
            .description(UPDATED_DESCRIPTION);

        restOperationMockMvc.perform(put("/api/operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperation)))
            .andExpect(status().isOk());

        // Validate the Operation in the database
        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeUpdate);
        Operation testOperation = operationList.get(operationList.size() - 1);
        assertThat(testOperation.getDateOperation()).isEqualTo(UPDATED_DATE_OPERATION);
        assertThat(testOperation.getTypeOperation()).isEqualTo(UPDATED_TYPE_OPERATION);
        assertThat(testOperation.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testOperation.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testOperation.getMoyenPaiement()).isEqualTo(UPDATED_MOYEN_PAIEMENT);
        assertThat(testOperation.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testOperation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOperation() throws Exception {
        int databaseSizeBeforeUpdate = operationRepository.findAll().size();

        // Create the Operation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOperationMockMvc.perform(put("/api/operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operation)))
            .andExpect(status().isCreated());

        // Validate the Operation in the database
        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOperation() throws Exception {
        // Initialize the database
        operationService.save(operation);

        int databaseSizeBeforeDelete = operationRepository.findAll().size();

        // Get the operation
        restOperationMockMvc.perform(delete("/api/operations/{id}", operation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operation.class);
        Operation operation1 = new Operation();
        operation1.setId(1L);
        Operation operation2 = new Operation();
        operation2.setId(operation1.getId());
        assertThat(operation1).isEqualTo(operation2);
        operation2.setId(2L);
        assertThat(operation1).isNotEqualTo(operation2);
        operation1.setId(null);
        assertThat(operation1).isNotEqualTo(operation2);
    }
}
