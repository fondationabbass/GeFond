package com.bdi.fondation.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdi.fondation.domain.Compte;
import com.bdi.fondation.domain.Echeance;
import com.bdi.fondation.domain.Mouvement;
import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.repository.MouvementRepository;


/**
 * Service Implementation for managing Mouvement.
 */
@Service
@Transactional
public class MouvementService {
	public static final String FAIT = "Fait";

	public static final String MOINS = "-";

	public static final String FINANCEMENT = "Financement";
	public static final String REMBOURSEMENT = "Rembourement";
	private final Logger log = LoggerFactory.getLogger(MouvementService.class);

	@Autowired
	private MouvementRepository mouvementRepository;
	@Autowired
	private CompteService compteService;
	@Autowired
	private PretService pretService;
	@Autowired
	private EcheanceQueryService echeanceQueryService;
	@Autowired
	private EcheanceService echeanceService;
	
	/**
	 * Save a mouvement.
	 *
	 * @param mouvement the entity to save
	 * @return the persisted entity
	 */
	public Mouvement save(Mouvement mouvement) {
		log.debug("Request to save Mouvement : {}", mouvement);
		LocalDate now = LocalDate.now();
		Pret pret = mouvement.getPret();
		if(pret!=null && mouvement.getCompte()==null) {
			Compte comptePret = pretService.getOrCreateCompte(pret);
			mouvement.setCompte(comptePret);
		}
		Compte compte = mouvement.getCompte();
		if(REMBOURSEMENT.equals(mouvement.getLib())) {
			if(pret.getEncours() - mouvement.getMontant() < 0)
				throw new IllegalStateException("Le remboursement dépasse l'encours");
			compte.setDateDernierCredit(now);
			Set<Echeance> set = dispatcherEcheances(mouvement);
			mouvement.setEcheances(set);
			pret.setEncours(pret.getEncours() - mouvement.getMontant());
			pretService.save(pret);
		}
		if(FINANCEMENT.equals(mouvement.getLib())) {
			compte.setDateDernierDebit(now);
		}
		compte.setSolde(compte.getSolde() + mouvement.getMontant());
		compteService.save(compte);
		mouvement.setEtat(MouvementService.FAIT);
		return mouvementRepository.save(mouvement);
	}

	private Set<Echeance> dispatcherEcheances(Mouvement mouvement) {
		List<Echeance> echeances = echeanceQueryService.findNotPayedByPretId(mouvement.getPret().getId() );
		Collections.sort(echeances, new Comparator<Echeance>() {

			@Override
			public int compare(Echeance o1, Echeance o2) {
				return o1.getDateTombe().compareTo(o2.getDateTombe());
			}
		});
		Set<Echeance> set = new HashSet<>();
		double plafond = mouvement.getMontant();
		for (Echeance echeance : echeances) {
			if(plafond > 0) {
				if(echeance.getMontantPaye()==null) {
					echeance.setMontantPaye(0.0);
				}
				set.add(echeance);
				double aPayer = echeance.getMontant() - echeance.getMontantPaye();
				if(aPayer <= plafond) {
					echeance.setMontantPaye(echeance.getMontantPaye() + aPayer);
					echeance.setEtatEcheance("Payée");
					echeance.setDatePayement(LocalDate.now());
					plafond-=aPayer;
				} else {
					echeance.setMontantPaye(plafond);
					plafond = 0;
				}
				echeanceService.save(echeance);
			} else {
				break;
			}
		}
		return set;
	}

	/**
	 * Get all the mouvements.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Mouvement> findAll(Pageable pageable) {
		log.debug("Request to get all Mouvements");
		return mouvementRepository.findAll(pageable);
	}

	/**
	 * Get one mouvement by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public Optional<Mouvement> findOne(Long id) {
		log.debug("Request to get Mouvement : {}", id);
		return mouvementRepository.findOneWithEagerRelationships(id);
	}

	/**
	 * Delete the mouvement by id.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete Mouvement : {}", id);
		mouvementRepository.delete(id);
	}
}
