package com.bdi.fondation.service;

import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.repository.EcheanceRepository;
import com.bdi.fondation.repository.ElementFinancementRepository;
import com.bdi.fondation.repository.GarantieRepository;
import com.bdi.fondation.repository.PretRepository;
import com.bdi.fondation.service.dto.PretAggregate;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Pret.
 */
@Service
@Transactional
public class PretService {

    private final Logger log = LoggerFactory.getLogger(PretService.class);

    private final PretRepository pretRepository;
    private final EcheanceRepository echeanceRepository;
    private final GarantieRepository garantieRepository;
    private final ElementFinancementRepository elementFinancementRepository;

   

	public PretService(PretRepository pretRepository, EcheanceRepository echeanceRepository,
			GarantieRepository garantieRepository, ElementFinancementRepository elementFinancementRepository) {
		this.pretRepository = pretRepository;
		this.echeanceRepository = echeanceRepository;
		this.garantieRepository = garantieRepository;
		this.elementFinancementRepository = elementFinancementRepository;
	}
	/**
     * Save a pret.
     *
     * @param pret the entity to save
     * @return the persisted entity
     */
    public Pret save(Pret pret) {
        log.debug("Request to save Pret : {}", pret);
        Pret result = pretRepository.save(pret);
//        List<Echeance> echeances = new ArrayList<>();
//        for (int i = 0; i < pret.getNbrEcheance(); i++) {
//			Echeance echeance = new Echeance();
//			echeance.setEtatEcheance("En cours");
//			echeance.setDateTombe(computeDate(pret.getDatePremiereEcheance(), pret.getPeriodicite(), i));
//			echeance.setMontant(pret.getMontAaccord() / pret.getNbrEcheance());
//			
//			
//			echeance.setPret(result);
//			echeances.add(echeance);
//		}
//        echeanceRepository.save(echeances);
		return result;
    }
    public Pret save(PretAggregate aggregate) {
    	log.debug("Request to save full Pret : {}", aggregate);
    	Pret result = pretRepository.save(aggregate.getPret());
    	elementFinancementRepository.save(Arrays.stream(aggregate.getElementFinancements()).map(a -> a.pret(result)).collect(Collectors.toList()));
    	garantieRepository.save(Arrays.stream(aggregate.getGaranties()).map(a -> a.pret(result)).collect(Collectors.toList()));
    	echeanceRepository.save(Arrays.stream(aggregate.getEcheances()).map(a -> a.pret(result)).collect(Collectors.toList()));
    	return result;
	}
//
//    private LocalDate computeDate(LocalDate reference, String periodicite, int i) {
//    	return reference.plus(i, ChronoUnit.MONTHS);
//	}

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
