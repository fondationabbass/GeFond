package com.bdi.fondation.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdi.fondation.domain.Candidature;
import com.bdi.fondation.domain.Chapitre;
import com.bdi.fondation.domain.Client;
import com.bdi.fondation.domain.Compte;
import com.bdi.fondation.repository.CandidatRepository;
import com.bdi.fondation.repository.CandidatureRepository;
import com.bdi.fondation.repository.ClientRepository;
import com.bdi.fondation.repository.CompteRepository;
import com.bdi.fondation.repository.DocumentRepository;
import com.bdi.fondation.repository.ExperienceCandidatRepository;
import com.bdi.fondation.repository.ProjetRepository;
import com.bdi.fondation.service.dto.CandidatureAggregate;
import com.bdi.fondation.service.dto.ChapitreCriteria;

import io.github.jhipster.service.filter.StringFilter;


/**
 * Service Implementation for managing Candidature.
 */
@Service
@Transactional
public class CandidatureService {

    private static final String CHAPITRE_NEW_ACCOUNT = "Chapitre 1";

	private final Logger log = LoggerFactory.getLogger(CandidatureService.class);

    private final CandidatureRepository candidatureRepository;
    private final ClientRepository clientRepository;
    private final ChapitreQueryService chapitreQueryService;
    private final CompteRepository compteRepository;
    private final CandidatRepository candidatRepository;
    private final ExperienceCandidatRepository experianceRepository;
    private final DocumentRepository documentRepository;
    private final ProjetRepository  projetRepository;

	public CandidatureService(CandidatureRepository candidatureRepository, ClientRepository clientRepository,
            ChapitreQueryService chapitreQueryService, CompteRepository compteRepository,
            CandidatRepository candidatRepository, ExperienceCandidatRepository experianceRepository,
            DocumentRepository documentRepository, ProjetRepository projetRepository) {
        this.candidatureRepository = candidatureRepository;
        this.clientRepository = clientRepository;
        this.chapitreQueryService = chapitreQueryService;
        this.compteRepository = compteRepository;
        this.candidatRepository = candidatRepository;
        this.experianceRepository = experianceRepository;
        this.documentRepository = documentRepository;
        this.projetRepository = projetRepository;
    }
    /**
     * Save a candidature.
     *
     * @param candidature the entity to save
     * @return the persisted entity
     */
    public Candidature save(Candidature candidature) {
        log.debug("Request to save Candidature : {}", candidature);
        return candidatureRepository.save(candidature);
    }
    public Candidature validate(Candidature candidature) {
    	candidature = candidatureRepository.findOne(candidature.getId());
    	LocalDate now = LocalDate.now();
    	Client client = new Client();
    	client.adressPersonneContact("").arrondResid("").candidat(candidature.getCandidat()).dateCreat(now).dateMaj(now);
    	client = clientRepository.save(client);
    	ChapitreCriteria criteria = new ChapitreCriteria();
    	StringFilter libChapitre=new StringFilter();
    	libChapitre.setEquals(CHAPITRE_NEW_ACCOUNT);
		criteria.setLibChapitre(libChapitre);
		Chapitre chapitre = chapitreQueryService.findByCriteria(criteria).iterator().next();
		Compte compte = new Compte();
		compte.client(client).chapitre(chapitre).dateOuverture(now).solde(0.0).dateDernierCredit(now).dateDernierDebit(now);
		compte = compteRepository.save(compte);
		return candidature;
	}

    public Candidature save(CandidatureAggregate aggregate) {
    	log.debug("Request to save full Candidature : {}", aggregate);
    	Candidature result = candidatureRepository.save(aggregate.getCandidature());
    	candidatRepository.save(aggregate.getCandidat());
    	experianceRepository.save(aggregate.getExperianceCandidat());
    	documentRepository.save(aggregate.getDocument());
    	projetRepository.save(aggregate.getProjet());
    	return result;
	}


    /**
     * Get all the candidatures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Candidature> findAll(Pageable pageable) {
        log.debug("Request to get all Candidatures");
        return candidatureRepository.findAll(pageable);
    }

    /**
     * Get one candidature by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Candidature findOne(Long id) {
        log.debug("Request to get Candidature : {}", id);
        return candidatureRepository.findOne(id);
    }

    /**
     * Delete the candidature by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Candidature : {}", id);
        candidatureRepository.delete(id);
    }

}
