package com.bdi.fondation.service;

import com.bdi.fondation.domain.Echeance;
import com.bdi.fondation.domain.ElementFinancement;
import com.bdi.fondation.domain.Mouvement;
import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.repository.ElementFinancementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing ElementFinancement.
 */
@Service
@Transactional
public class ElementFinancementService {

	private final Logger log = LoggerFactory.getLogger(ElementFinancementService.class);

	@Autowired
	private ElementFinancementRepository elementFinancementRepository;
	@Autowired
	private PretService pretService;
	@Autowired
	private MouvementService mouvementService;
	@Autowired
	private EcheanceQueryService echeanceQueryService;

	/**
	 * Save a elementFinancement.
	 *
	 * @param elementFinancement the entity to save
	 * @return the persisted entity
	 */
	public Iterable<ElementFinancement> save(Iterable<ElementFinancement> elementFinancements) {
		List<ElementFinancement> result = new ArrayList<>();
		for (ElementFinancement elementFinancement : elementFinancements) {
			result.add(save(elementFinancement));
		}
		return result;
	}
	public ElementFinancement save(ElementFinancement elementFinancement) {
		log.debug("Request to save ElementFinancement : {}", elementFinancement);
		Double montant = elementFinancement.getMontant();
		if(montant!=null && montant > 0) {
			Pret pret = elementFinancement.getPret();
			if(pret.getMontDebloq() + montant > pret.getMontAaccord())
				throw new IllegalStateException("Le montant débloqué dépasse le plafond du pret.");
			pret.setMontDebloq(pret.getMontDebloq() + montant);
			pret.setDateDernierDebloq(LocalDate.now());
			pret.setEtat(PretService.MIS_EN_PLACE);
			pret.setEncours(pret.getEncours() + montant);
			pretService.save(pret);
			mouvementService.save(mouvementDeFinancement(elementFinancement));
			List<Echeance> echeances = echeanceQueryService.findNotPayedByPretId(pret.getId());
			if(echeances.size() > 0) {
				double addOn = montant/echeances.size();
				echeances.forEach(e->e.setMontant(e.getMontant() + addOn));
			}
		}
		return elementFinancementRepository.save(elementFinancement);
	}

	private Mouvement mouvementDeFinancement(ElementFinancement elementFinancement) {
		Mouvement mouvement = new Mouvement();
		mouvement.setMontant(-1 * elementFinancement.getMontant());
		mouvement.setPret(elementFinancement.getPret());
		mouvement.setDateMvt(elementFinancement.getDateFinancement());
		mouvement.setEtat(MouvementService.FAIT);
		mouvement.setSens(MouvementService.MOINS);
		mouvement.setLib(MouvementService.FINANCEMENT);
		return mouvement;
	}

	/**
	 * Get all the elementFinancements.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<ElementFinancement> findAll() {
		log.debug("Request to get all ElementFinancements");
		return elementFinancementRepository.findAll();
	}

	/**
	 * Get one elementFinancement by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public ElementFinancement findOne(Long id) {
		log.debug("Request to get ElementFinancement : {}", id);
		return elementFinancementRepository.findOne(id);
	}

	/**
	 * Delete the elementFinancement by id.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete ElementFinancement : {}", id);
		elementFinancementRepository.delete(id);
	}
}
