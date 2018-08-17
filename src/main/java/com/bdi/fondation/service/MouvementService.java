package com.bdi.fondation.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdi.fondation.config.Constants;
import com.bdi.fondation.domain.Compte;
import com.bdi.fondation.domain.Mouvement;
import com.bdi.fondation.repository.MouvementRepository;


/**
 * Service Implementation for managing Mouvement.
 */
@Service
@Transactional
public class MouvementService {
	private final Logger log = LoggerFactory.getLogger(MouvementService.class);

	@Autowired
	private MouvementRepository mouvementRepository;
	@Autowired
	private CompteService compteService;

	/**
	 * Save a mouvement.
	 *
	 * @param mouvement the entity to save
	 * @return the persisted entity
	 */
	public Iterable<Mouvement> save(Iterable<Mouvement> mvts) {
        List<Mouvement> result = new ArrayList<>();
        for (Mouvement mvt : mvts) {
            result.add(save(mvt));
        }
        return result;
    }
	public Mouvement save(Mouvement mouvement) {
		log.debug("Request to save Mouvement : {}", mouvement);
		updateCompte(mouvement);
		mouvement.setEtat(Constants.FAIT);
		return mouvementRepository.save(mouvement);
	}
    private void updateCompte(Mouvement mouvement) {
        LocalDate now = LocalDate.now();
		Compte compte = mouvement.getCompte();
		if(Constants.DEBIT.equals(mouvement.getSens())) {
		    compte.setDateDernierDebit(now);
		}
		if(Constants.CREDIT.equals(mouvement.getSens())) {
		    compte.setDateDernierCredit(now);
		}
		compte.setSolde(compte.getSolde() + montant(mouvement));
		compteService.save(compte);
    }
    private Double montant(Mouvement mouvement) {
        if(Constants.DEBIT.equals(mouvement.getSens())) {
            return -1 * mouvement.getMontant();
        }
        return mouvement.getMontant();
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
	public Mouvement findOne(Long id) {
		log.debug("Request to get Mouvement : {}", id);
		return mouvementRepository.findOne(id);
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
