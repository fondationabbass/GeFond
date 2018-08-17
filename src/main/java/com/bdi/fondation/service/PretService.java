package com.bdi.fondation.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdi.fondation.domain.Chapitre;
import com.bdi.fondation.domain.Compte;
import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.repository.CompteRepository;
import com.bdi.fondation.repository.PretRepository;
import com.bdi.fondation.service.dto.ChapitreCriteria;
import com.bdi.fondation.service.dto.CompteCriteria;
import com.bdi.fondation.service.dto.PretAggregate;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


/**
 * Service Implementation for managing Pret.
 */
@Service
@Transactional
public class PretService {

	private final Logger log = LoggerFactory.getLogger(PretService.class);

    @Autowired
    private PretRepository pretRepository;
    @Autowired
    private EcheanceService echeanceService;
    @Autowired
    private GarantieService garantieService;
    @Autowired
    private ElementFinancementService elementFinancementService;
	@Autowired
	private CompteQueryService compteQueryService;
	@Autowired
	private ChapitreQueryService chapitreQueryService;
	@Autowired
	private ChapitreService chapitreService;
	@Autowired
	private CompteRepository compteRepository;

	/**
     * Save a pret.
     *
     * @param pret the entity to save
     * @return the persisted entity
     */
    public Pret save(Pret pret) {
        log.debug("Request to save Pret : {}", pret);
        return pretRepository.save(pret);
    }
    public Pret save(PretAggregate aggregate) {
    	log.debug("Request to save full Pret : {}", aggregate);
    	Pret result = pretRepository.save(aggregate.getPret().montDebloq(0.0).encours(0.0));
    	echeanceService.save(Arrays.stream(aggregate.getEcheances()).map(a -> a.pret(result).montant(0.0)).collect(Collectors.toList()));
    	elementFinancementService.save(Arrays.stream(aggregate.getElementFinancements()).map(a -> a.pret(result)).collect(Collectors.toList()));
    	garantieService.save(Arrays.stream(aggregate.getGaranties()).map(a -> a.pret(result)).collect(Collectors.toList()));
    	return result;
	}
    public Compte getOrCreateCompte(Pret pret) {
		CompteCriteria criteria = new CompteCriteria();
		LongFilter longFilter = new LongFilter();
		longFilter.setEquals(pret.getId());
		criteria.setPretId(longFilter);
		List<Compte> comptes = compteQueryService.findByCriteria(criteria);
		if(comptes != null && !comptes.isEmpty()) {
			return comptes.get(0);
		} 
		Chapitre chapitre = getOrCreateChapitre(pret);
		Compte compte = new Compte();
		LocalDate now = LocalDate.now();
		compte.pret(pret).client(pret.getClient()).chapitre(chapitre).intituleCompte("Engagement")
			.dateOuverture(now).solde(0.0).dateDernierCredit(now).dateDernierDebit(now);
		return  compteRepository.save(compte);
	}

	public Chapitre getOrCreateChapitre(Pret pret) {
		ChapitreCriteria chapitreCriteria = new ChapitreCriteria();
		StringFilter libChapitre = new StringFilter();
		libChapitre.setEquals(pret.getTypPret());
		chapitreCriteria.setLibChapitre(libChapitre);
		List<Chapitre> chapitres = chapitreQueryService.findByCriteria(chapitreCriteria);
		if(chapitres!=null && !chapitres.isEmpty()) {
			return chapitres.get(0);
		}
		Chapitre chapitre = new Chapitre();
		chapitre.libChapitre(pret.getTypPret());
		return chapitreService.save(chapitre);
	}

	/**
     * Get all the prets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Pret> findAll(Pageable pageable) {
        log.debug("Request to get all Prets");
        return pretRepository.findAll(pageable);
    }

    /**
     * Get one pret by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Pret findOne(Long id) {
        log.debug("Request to get Pret : {}", id);
        return pretRepository.findOne(id);
    }

    /**
     * Delete the pret by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pret : {}", id);
        pretRepository.delete(id);
    }
}
