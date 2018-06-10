package com.bdi.fondation.web.rest;

import com.bdi.fondation.GeFondApp;

import com.bdi.fondation.domain.Client;
import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.repository.ClientRepository;
import com.bdi.fondation.service.ClientService;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;
import com.bdi.fondation.service.dto.ClientCriteria;
import com.bdi.fondation.service.ClientQueryService;

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
 * Test class for the ClientResource REST controller.
 *
 * @see ClientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeFondApp.class)
public class ClientResourceIntTest {

    private static final LocalDate DEFAULT_DATE_CREAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREAT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_RESID = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_RESID = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_RESID = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_RESID = "BBBBBBBBBB";

    private static final String DEFAULT_ARROND_RESID = "AAAAAAAAAA";
    private static final String UPDATED_ARROND_RESID = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PERSONNE_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PERSONNE_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_PERSONNE_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_TEL_PERSONNE_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESS_PERSONNE_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS_PERSONNE_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CLIENT = "BBBBBBBBBB";

    private static final Double DEFAULT_POINTS_FIDEL = 1D;
    private static final Double UPDATED_POINTS_FIDEL = 2D;

    private static final LocalDate DEFAULT_DATE_MAJ = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MAJ = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientQueryService clientQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientMockMvc;

    private Client client;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientResource clientResource = new ClientResource(clientService, clientQueryService);
        this.restClientMockMvc = MockMvcBuilders.standaloneSetup(clientResource)
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
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .dateCreat(DEFAULT_DATE_CREAT)
            .lieuResid(DEFAULT_LIEU_RESID)
            .typeResid(DEFAULT_TYPE_RESID)
            .arrondResid(DEFAULT_ARROND_RESID)
            .nomPersonneContact(DEFAULT_NOM_PERSONNE_CONTACT)
            .telPersonneContact(DEFAULT_TEL_PERSONNE_CONTACT)
            .adressPersonneContact(DEFAULT_ADRESS_PERSONNE_CONTACT)
            .typeClient(DEFAULT_TYPE_CLIENT)
            .pointsFidel(DEFAULT_POINTS_FIDEL)
            .dateMaj(DEFAULT_DATE_MAJ);
        return client;
    }

    @Before
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getDateCreat()).isEqualTo(DEFAULT_DATE_CREAT);
        assertThat(testClient.getLieuResid()).isEqualTo(DEFAULT_LIEU_RESID);
        assertThat(testClient.getTypeResid()).isEqualTo(DEFAULT_TYPE_RESID);
        assertThat(testClient.getArrondResid()).isEqualTo(DEFAULT_ARROND_RESID);
        assertThat(testClient.getNomPersonneContact()).isEqualTo(DEFAULT_NOM_PERSONNE_CONTACT);
        assertThat(testClient.getTelPersonneContact()).isEqualTo(DEFAULT_TEL_PERSONNE_CONTACT);
        assertThat(testClient.getAdressPersonneContact()).isEqualTo(DEFAULT_ADRESS_PERSONNE_CONTACT);
        assertThat(testClient.getTypeClient()).isEqualTo(DEFAULT_TYPE_CLIENT);
        assertThat(testClient.getPointsFidel()).isEqualTo(DEFAULT_POINTS_FIDEL);
        assertThat(testClient.getDateMaj()).isEqualTo(DEFAULT_DATE_MAJ);
    }

    @Test
    @Transactional
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLieuResidIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setLieuResid(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeResidIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setTypeResid(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArrondResidIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setArrondResid(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomPersonneContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setNomPersonneContact(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelPersonneContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setTelPersonneContact(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdressPersonneContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setAdressPersonneContact(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setTypeClient(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreat").value(hasItem(DEFAULT_DATE_CREAT.toString())))
            .andExpect(jsonPath("$.[*].lieuResid").value(hasItem(DEFAULT_LIEU_RESID.toString())))
            .andExpect(jsonPath("$.[*].typeResid").value(hasItem(DEFAULT_TYPE_RESID.toString())))
            .andExpect(jsonPath("$.[*].arrondResid").value(hasItem(DEFAULT_ARROND_RESID.toString())))
            .andExpect(jsonPath("$.[*].nomPersonneContact").value(hasItem(DEFAULT_NOM_PERSONNE_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].telPersonneContact").value(hasItem(DEFAULT_TEL_PERSONNE_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].adressPersonneContact").value(hasItem(DEFAULT_ADRESS_PERSONNE_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].typeClient").value(hasItem(DEFAULT_TYPE_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].pointsFidel").value(hasItem(DEFAULT_POINTS_FIDEL.doubleValue())))
            .andExpect(jsonPath("$.[*].dateMaj").value(hasItem(DEFAULT_DATE_MAJ.toString())));
    }

    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.dateCreat").value(DEFAULT_DATE_CREAT.toString()))
            .andExpect(jsonPath("$.lieuResid").value(DEFAULT_LIEU_RESID.toString()))
            .andExpect(jsonPath("$.typeResid").value(DEFAULT_TYPE_RESID.toString()))
            .andExpect(jsonPath("$.arrondResid").value(DEFAULT_ARROND_RESID.toString()))
            .andExpect(jsonPath("$.nomPersonneContact").value(DEFAULT_NOM_PERSONNE_CONTACT.toString()))
            .andExpect(jsonPath("$.telPersonneContact").value(DEFAULT_TEL_PERSONNE_CONTACT.toString()))
            .andExpect(jsonPath("$.adressPersonneContact").value(DEFAULT_ADRESS_PERSONNE_CONTACT.toString()))
            .andExpect(jsonPath("$.typeClient").value(DEFAULT_TYPE_CLIENT.toString()))
            .andExpect(jsonPath("$.pointsFidel").value(DEFAULT_POINTS_FIDEL.doubleValue()))
            .andExpect(jsonPath("$.dateMaj").value(DEFAULT_DATE_MAJ.toString()));
    }

    @Test
    @Transactional
    public void getAllClientsByDateCreatIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateCreat equals to DEFAULT_DATE_CREAT
        defaultClientShouldBeFound("dateCreat.equals=" + DEFAULT_DATE_CREAT);

        // Get all the clientList where dateCreat equals to UPDATED_DATE_CREAT
        defaultClientShouldNotBeFound("dateCreat.equals=" + UPDATED_DATE_CREAT);
    }

    @Test
    @Transactional
    public void getAllClientsByDateCreatIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateCreat in DEFAULT_DATE_CREAT or UPDATED_DATE_CREAT
        defaultClientShouldBeFound("dateCreat.in=" + DEFAULT_DATE_CREAT + "," + UPDATED_DATE_CREAT);

        // Get all the clientList where dateCreat equals to UPDATED_DATE_CREAT
        defaultClientShouldNotBeFound("dateCreat.in=" + UPDATED_DATE_CREAT);
    }

    @Test
    @Transactional
    public void getAllClientsByDateCreatIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateCreat is not null
        defaultClientShouldBeFound("dateCreat.specified=true");

        // Get all the clientList where dateCreat is null
        defaultClientShouldNotBeFound("dateCreat.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByDateCreatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateCreat greater than or equals to DEFAULT_DATE_CREAT
        defaultClientShouldBeFound("dateCreat.greaterOrEqualThan=" + DEFAULT_DATE_CREAT);

        // Get all the clientList where dateCreat greater than or equals to UPDATED_DATE_CREAT
        defaultClientShouldNotBeFound("dateCreat.greaterOrEqualThan=" + UPDATED_DATE_CREAT);
    }

    @Test
    @Transactional
    public void getAllClientsByDateCreatIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateCreat less than or equals to DEFAULT_DATE_CREAT
        defaultClientShouldNotBeFound("dateCreat.lessThan=" + DEFAULT_DATE_CREAT);

        // Get all the clientList where dateCreat less than or equals to UPDATED_DATE_CREAT
        defaultClientShouldBeFound("dateCreat.lessThan=" + UPDATED_DATE_CREAT);
    }


    @Test
    @Transactional
    public void getAllClientsByLieuResidIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuResid equals to DEFAULT_LIEU_RESID
        defaultClientShouldBeFound("lieuResid.equals=" + DEFAULT_LIEU_RESID);

        // Get all the clientList where lieuResid equals to UPDATED_LIEU_RESID
        defaultClientShouldNotBeFound("lieuResid.equals=" + UPDATED_LIEU_RESID);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuResidIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuResid in DEFAULT_LIEU_RESID or UPDATED_LIEU_RESID
        defaultClientShouldBeFound("lieuResid.in=" + DEFAULT_LIEU_RESID + "," + UPDATED_LIEU_RESID);

        // Get all the clientList where lieuResid equals to UPDATED_LIEU_RESID
        defaultClientShouldNotBeFound("lieuResid.in=" + UPDATED_LIEU_RESID);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuResidIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuResid is not null
        defaultClientShouldBeFound("lieuResid.specified=true");

        // Get all the clientList where lieuResid is null
        defaultClientShouldNotBeFound("lieuResid.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByTypeResidIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where typeResid equals to DEFAULT_TYPE_RESID
        defaultClientShouldBeFound("typeResid.equals=" + DEFAULT_TYPE_RESID);

        // Get all the clientList where typeResid equals to UPDATED_TYPE_RESID
        defaultClientShouldNotBeFound("typeResid.equals=" + UPDATED_TYPE_RESID);
    }

    @Test
    @Transactional
    public void getAllClientsByTypeResidIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where typeResid in DEFAULT_TYPE_RESID or UPDATED_TYPE_RESID
        defaultClientShouldBeFound("typeResid.in=" + DEFAULT_TYPE_RESID + "," + UPDATED_TYPE_RESID);

        // Get all the clientList where typeResid equals to UPDATED_TYPE_RESID
        defaultClientShouldNotBeFound("typeResid.in=" + UPDATED_TYPE_RESID);
    }

    @Test
    @Transactional
    public void getAllClientsByTypeResidIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where typeResid is not null
        defaultClientShouldBeFound("typeResid.specified=true");

        // Get all the clientList where typeResid is null
        defaultClientShouldNotBeFound("typeResid.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByArrondResidIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where arrondResid equals to DEFAULT_ARROND_RESID
        defaultClientShouldBeFound("arrondResid.equals=" + DEFAULT_ARROND_RESID);

        // Get all the clientList where arrondResid equals to UPDATED_ARROND_RESID
        defaultClientShouldNotBeFound("arrondResid.equals=" + UPDATED_ARROND_RESID);
    }

    @Test
    @Transactional
    public void getAllClientsByArrondResidIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where arrondResid in DEFAULT_ARROND_RESID or UPDATED_ARROND_RESID
        defaultClientShouldBeFound("arrondResid.in=" + DEFAULT_ARROND_RESID + "," + UPDATED_ARROND_RESID);

        // Get all the clientList where arrondResid equals to UPDATED_ARROND_RESID
        defaultClientShouldNotBeFound("arrondResid.in=" + UPDATED_ARROND_RESID);
    }

    @Test
    @Transactional
    public void getAllClientsByArrondResidIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where arrondResid is not null
        defaultClientShouldBeFound("arrondResid.specified=true");

        // Get all the clientList where arrondResid is null
        defaultClientShouldNotBeFound("arrondResid.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByNomPersonneContactIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nomPersonneContact equals to DEFAULT_NOM_PERSONNE_CONTACT
        defaultClientShouldBeFound("nomPersonneContact.equals=" + DEFAULT_NOM_PERSONNE_CONTACT);

        // Get all the clientList where nomPersonneContact equals to UPDATED_NOM_PERSONNE_CONTACT
        defaultClientShouldNotBeFound("nomPersonneContact.equals=" + UPDATED_NOM_PERSONNE_CONTACT);
    }

    @Test
    @Transactional
    public void getAllClientsByNomPersonneContactIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nomPersonneContact in DEFAULT_NOM_PERSONNE_CONTACT or UPDATED_NOM_PERSONNE_CONTACT
        defaultClientShouldBeFound("nomPersonneContact.in=" + DEFAULT_NOM_PERSONNE_CONTACT + "," + UPDATED_NOM_PERSONNE_CONTACT);

        // Get all the clientList where nomPersonneContact equals to UPDATED_NOM_PERSONNE_CONTACT
        defaultClientShouldNotBeFound("nomPersonneContact.in=" + UPDATED_NOM_PERSONNE_CONTACT);
    }

    @Test
    @Transactional
    public void getAllClientsByNomPersonneContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nomPersonneContact is not null
        defaultClientShouldBeFound("nomPersonneContact.specified=true");

        // Get all the clientList where nomPersonneContact is null
        defaultClientShouldNotBeFound("nomPersonneContact.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByTelPersonneContactIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where telPersonneContact equals to DEFAULT_TEL_PERSONNE_CONTACT
        defaultClientShouldBeFound("telPersonneContact.equals=" + DEFAULT_TEL_PERSONNE_CONTACT);

        // Get all the clientList where telPersonneContact equals to UPDATED_TEL_PERSONNE_CONTACT
        defaultClientShouldNotBeFound("telPersonneContact.equals=" + UPDATED_TEL_PERSONNE_CONTACT);
    }

    @Test
    @Transactional
    public void getAllClientsByTelPersonneContactIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where telPersonneContact in DEFAULT_TEL_PERSONNE_CONTACT or UPDATED_TEL_PERSONNE_CONTACT
        defaultClientShouldBeFound("telPersonneContact.in=" + DEFAULT_TEL_PERSONNE_CONTACT + "," + UPDATED_TEL_PERSONNE_CONTACT);

        // Get all the clientList where telPersonneContact equals to UPDATED_TEL_PERSONNE_CONTACT
        defaultClientShouldNotBeFound("telPersonneContact.in=" + UPDATED_TEL_PERSONNE_CONTACT);
    }

    @Test
    @Transactional
    public void getAllClientsByTelPersonneContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where telPersonneContact is not null
        defaultClientShouldBeFound("telPersonneContact.specified=true");

        // Get all the clientList where telPersonneContact is null
        defaultClientShouldNotBeFound("telPersonneContact.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByAdressPersonneContactIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where adressPersonneContact equals to DEFAULT_ADRESS_PERSONNE_CONTACT
        defaultClientShouldBeFound("adressPersonneContact.equals=" + DEFAULT_ADRESS_PERSONNE_CONTACT);

        // Get all the clientList where adressPersonneContact equals to UPDATED_ADRESS_PERSONNE_CONTACT
        defaultClientShouldNotBeFound("adressPersonneContact.equals=" + UPDATED_ADRESS_PERSONNE_CONTACT);
    }

    @Test
    @Transactional
    public void getAllClientsByAdressPersonneContactIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where adressPersonneContact in DEFAULT_ADRESS_PERSONNE_CONTACT or UPDATED_ADRESS_PERSONNE_CONTACT
        defaultClientShouldBeFound("adressPersonneContact.in=" + DEFAULT_ADRESS_PERSONNE_CONTACT + "," + UPDATED_ADRESS_PERSONNE_CONTACT);

        // Get all the clientList where adressPersonneContact equals to UPDATED_ADRESS_PERSONNE_CONTACT
        defaultClientShouldNotBeFound("adressPersonneContact.in=" + UPDATED_ADRESS_PERSONNE_CONTACT);
    }

    @Test
    @Transactional
    public void getAllClientsByAdressPersonneContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where adressPersonneContact is not null
        defaultClientShouldBeFound("adressPersonneContact.specified=true");

        // Get all the clientList where adressPersonneContact is null
        defaultClientShouldNotBeFound("adressPersonneContact.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByTypeClientIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where typeClient equals to DEFAULT_TYPE_CLIENT
        defaultClientShouldBeFound("typeClient.equals=" + DEFAULT_TYPE_CLIENT);

        // Get all the clientList where typeClient equals to UPDATED_TYPE_CLIENT
        defaultClientShouldNotBeFound("typeClient.equals=" + UPDATED_TYPE_CLIENT);
    }

    @Test
    @Transactional
    public void getAllClientsByTypeClientIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where typeClient in DEFAULT_TYPE_CLIENT or UPDATED_TYPE_CLIENT
        defaultClientShouldBeFound("typeClient.in=" + DEFAULT_TYPE_CLIENT + "," + UPDATED_TYPE_CLIENT);

        // Get all the clientList where typeClient equals to UPDATED_TYPE_CLIENT
        defaultClientShouldNotBeFound("typeClient.in=" + UPDATED_TYPE_CLIENT);
    }

    @Test
    @Transactional
    public void getAllClientsByTypeClientIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where typeClient is not null
        defaultClientShouldBeFound("typeClient.specified=true");

        // Get all the clientList where typeClient is null
        defaultClientShouldNotBeFound("typeClient.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByPointsFidelIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pointsFidel equals to DEFAULT_POINTS_FIDEL
        defaultClientShouldBeFound("pointsFidel.equals=" + DEFAULT_POINTS_FIDEL);

        // Get all the clientList where pointsFidel equals to UPDATED_POINTS_FIDEL
        defaultClientShouldNotBeFound("pointsFidel.equals=" + UPDATED_POINTS_FIDEL);
    }

    @Test
    @Transactional
    public void getAllClientsByPointsFidelIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pointsFidel in DEFAULT_POINTS_FIDEL or UPDATED_POINTS_FIDEL
        defaultClientShouldBeFound("pointsFidel.in=" + DEFAULT_POINTS_FIDEL + "," + UPDATED_POINTS_FIDEL);

        // Get all the clientList where pointsFidel equals to UPDATED_POINTS_FIDEL
        defaultClientShouldNotBeFound("pointsFidel.in=" + UPDATED_POINTS_FIDEL);
    }

    @Test
    @Transactional
    public void getAllClientsByPointsFidelIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pointsFidel is not null
        defaultClientShouldBeFound("pointsFidel.specified=true");

        // Get all the clientList where pointsFidel is null
        defaultClientShouldNotBeFound("pointsFidel.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByDateMajIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateMaj equals to DEFAULT_DATE_MAJ
        defaultClientShouldBeFound("dateMaj.equals=" + DEFAULT_DATE_MAJ);

        // Get all the clientList where dateMaj equals to UPDATED_DATE_MAJ
        defaultClientShouldNotBeFound("dateMaj.equals=" + UPDATED_DATE_MAJ);
    }

    @Test
    @Transactional
    public void getAllClientsByDateMajIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateMaj in DEFAULT_DATE_MAJ or UPDATED_DATE_MAJ
        defaultClientShouldBeFound("dateMaj.in=" + DEFAULT_DATE_MAJ + "," + UPDATED_DATE_MAJ);

        // Get all the clientList where dateMaj equals to UPDATED_DATE_MAJ
        defaultClientShouldNotBeFound("dateMaj.in=" + UPDATED_DATE_MAJ);
    }

    @Test
    @Transactional
    public void getAllClientsByDateMajIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateMaj is not null
        defaultClientShouldBeFound("dateMaj.specified=true");

        // Get all the clientList where dateMaj is null
        defaultClientShouldNotBeFound("dateMaj.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByDateMajIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateMaj greater than or equals to DEFAULT_DATE_MAJ
        defaultClientShouldBeFound("dateMaj.greaterOrEqualThan=" + DEFAULT_DATE_MAJ);

        // Get all the clientList where dateMaj greater than or equals to UPDATED_DATE_MAJ
        defaultClientShouldNotBeFound("dateMaj.greaterOrEqualThan=" + UPDATED_DATE_MAJ);
    }

    @Test
    @Transactional
    public void getAllClientsByDateMajIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateMaj less than or equals to DEFAULT_DATE_MAJ
        defaultClientShouldNotBeFound("dateMaj.lessThan=" + DEFAULT_DATE_MAJ);

        // Get all the clientList where dateMaj less than or equals to UPDATED_DATE_MAJ
        defaultClientShouldBeFound("dateMaj.lessThan=" + UPDATED_DATE_MAJ);
    }


    @Test
    @Transactional
    public void getAllClientsByCandidatIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidat candidat = CandidatResourceIntTest.createEntity(em);
        em.persist(candidat);
        em.flush();
        client.setCandidat(candidat);
        clientRepository.saveAndFlush(client);
        Long candidatId = candidat.getId();

        // Get all the clientList where candidat equals to candidatId
        defaultClientShouldBeFound("candidatId.equals=" + candidatId);

        // Get all the clientList where candidat equals to candidatId + 1
        defaultClientShouldNotBeFound("candidatId.equals=" + (candidatId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClientShouldBeFound(String filter) throws Exception {
        restClientMockMvc.perform(get("/api/clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreat").value(hasItem(DEFAULT_DATE_CREAT.toString())))
            .andExpect(jsonPath("$.[*].lieuResid").value(hasItem(DEFAULT_LIEU_RESID.toString())))
            .andExpect(jsonPath("$.[*].typeResid").value(hasItem(DEFAULT_TYPE_RESID.toString())))
            .andExpect(jsonPath("$.[*].arrondResid").value(hasItem(DEFAULT_ARROND_RESID.toString())))
            .andExpect(jsonPath("$.[*].nomPersonneContact").value(hasItem(DEFAULT_NOM_PERSONNE_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].telPersonneContact").value(hasItem(DEFAULT_TEL_PERSONNE_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].adressPersonneContact").value(hasItem(DEFAULT_ADRESS_PERSONNE_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].typeClient").value(hasItem(DEFAULT_TYPE_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].pointsFidel").value(hasItem(DEFAULT_POINTS_FIDEL.doubleValue())))
            .andExpect(jsonPath("$.[*].dateMaj").value(hasItem(DEFAULT_DATE_MAJ.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClientShouldNotBeFound(String filter) throws Exception {
        restClientMockMvc.perform(get("/api/clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientService.save(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findOne(client.getId());
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .dateCreat(UPDATED_DATE_CREAT)
            .lieuResid(UPDATED_LIEU_RESID)
            .typeResid(UPDATED_TYPE_RESID)
            .arrondResid(UPDATED_ARROND_RESID)
            .nomPersonneContact(UPDATED_NOM_PERSONNE_CONTACT)
            .telPersonneContact(UPDATED_TEL_PERSONNE_CONTACT)
            .adressPersonneContact(UPDATED_ADRESS_PERSONNE_CONTACT)
            .typeClient(UPDATED_TYPE_CLIENT)
            .pointsFidel(UPDATED_POINTS_FIDEL)
            .dateMaj(UPDATED_DATE_MAJ);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClient)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getDateCreat()).isEqualTo(UPDATED_DATE_CREAT);
        assertThat(testClient.getLieuResid()).isEqualTo(UPDATED_LIEU_RESID);
        assertThat(testClient.getTypeResid()).isEqualTo(UPDATED_TYPE_RESID);
        assertThat(testClient.getArrondResid()).isEqualTo(UPDATED_ARROND_RESID);
        assertThat(testClient.getNomPersonneContact()).isEqualTo(UPDATED_NOM_PERSONNE_CONTACT);
        assertThat(testClient.getTelPersonneContact()).isEqualTo(UPDATED_TEL_PERSONNE_CONTACT);
        assertThat(testClient.getAdressPersonneContact()).isEqualTo(UPDATED_ADRESS_PERSONNE_CONTACT);
        assertThat(testClient.getTypeClient()).isEqualTo(UPDATED_TYPE_CLIENT);
        assertThat(testClient.getPointsFidel()).isEqualTo(UPDATED_POINTS_FIDEL);
        assertThat(testClient.getDateMaj()).isEqualTo(UPDATED_DATE_MAJ);
    }

    @Test
    @Transactional
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Create the Client

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientService.save(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Get the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = new Client();
        client1.setId(1L);
        Client client2 = new Client();
        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);
        client2.setId(2L);
        assertThat(client1).isNotEqualTo(client2);
        client1.setId(null);
        assertThat(client1).isNotEqualTo(client2);
    }
}
