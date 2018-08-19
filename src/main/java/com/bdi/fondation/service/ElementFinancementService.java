package com.bdi.fondation.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdi.fondation.config.Constants;
import com.bdi.fondation.domain.ElementFinancement;
import com.bdi.fondation.domain.Operation;
import com.bdi.fondation.repository.ElementFinancementRepository;

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
	private OperationService operationService;

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
        ElementFinancement result = elementFinancementRepository.save(elementFinancement);
        Operation operation = new Operation();
        operation.pret(result.getPret()).montant(result.getMontant()).typeOperation(Constants.FINANCEMENT).moyenPaiement(result.getType())
                .dateOperation(result.getDateFinancement()).description("Débloquage numéro " + result.getId());
        operationService.save(operation);
        return result;
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
