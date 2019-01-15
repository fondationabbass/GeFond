package com.bdi.fondation.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdi.fondation.domain.Garantie;
import com.bdi.fondation.repository.GarantieRepository;


/**
 * Service Implementation for managing Garantie.
 */
@Service
@Transactional
public class GarantieService {

    private final Logger log = LoggerFactory.getLogger(GarantieService.class);

    private final GarantieRepository garantieRepository;

    public GarantieService(GarantieRepository garantieRepository) {
        this.garantieRepository = garantieRepository;
    }

    /**
     * Save a garantie.
     *
     * @param garantie the entity to save
     * @return the persisted entity
     */
    public Garantie save(Garantie garantie) {
        log.debug("Request to save Garantie : {}", garantie);
        //validate(garantie);

        return garantieRepository.save(garantie);
    }

    private void validate(Garantie garantie) {
        StringBuilder sb = new StringBuilder();
        if(garantie.getMontantEvalue() < garantie.getMontantAfect()){
        	sb.append("Le montant affecté est supérieur au montant évalué.\n");
        }
        if(garantie.getPret().getEncours() < garantie.getMontantAfect()){
        	sb.append("Le montant affecté est supérieur à l'encours du prêt");
        }

        if(sb.length() > 0){
        	throw new IllegalStateException(sb.toString());
        }
    }
    public Iterable<Garantie> save(Iterable<Garantie> items) {
		List<Garantie> result = new ArrayList<>();
		for (Garantie item : items) {
			result.add(save(item));
		}
		return result;
	}

    /**
     * Get all the garanties.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Garantie> findAll() {
        log.debug("Request to get all Garanties");
        return garantieRepository.findAll();
    }

    /**
     * Get one garantie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Garantie findOne(Long id) {
        log.debug("Request to get Garantie : {}", id);
        return garantieRepository.findOne(id);
    }

    /**
     * Delete the garantie by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Garantie : {}", id);
        garantieRepository.delete(id);
    }
}
