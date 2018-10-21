package com.bdi.fondation.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdi.fondation.domain.Caisse_;
import com.bdi.fondation.domain.Chapitre_;
import com.bdi.fondation.domain.Client_;
import com.bdi.fondation.domain.Compte;
import com.bdi.fondation.domain.Compte_;
import com.bdi.fondation.domain.Parametrage;
import com.bdi.fondation.domain.Pret_;
import com.bdi.fondation.repository.CompteRepository;
import com.bdi.fondation.service.dto.CompteCriteria;
import com.bdi.fondation.service.dto.ParametrageCriteria;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


/**
 * Service for executing complex queries for Compte entities in the database.
 * The main input is a {@link CompteCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Compte} or a {@link Page} of {@link Compte} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompteQueryService extends QueryService<Compte> {

    private final Logger log = LoggerFactory.getLogger(CompteQueryService.class);


    private final CompteRepository compteRepository;
    private final ParametrageQueryService parametrage;

    public CompteQueryService(CompteRepository compteRepository, ParametrageQueryService parametrage) {
        this.compteRepository = compteRepository;
        this.parametrage = parametrage;
    }

    /**
     * Return a {@link List} of {@link Compte} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Compte> findByCriteria(CompteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Compte> specification = createSpecification(criteria);
        return compteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Compte} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Compte> findByCriteria(CompteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Compte> specification = createSpecification(criteria);
        return compteRepository.findAll(specification, page);
    }

    /**
     * Function to convert CompteCriteria to a {@link Specifications}
     */
    private Specifications<Compte> createSpecification(CompteCriteria criteria) {
        Specifications<Compte> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Compte_.id));
            }
            if (criteria.getIntituleCompte() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIntituleCompte(), Compte_.intituleCompte));
            }
            if (criteria.getNumCompte() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumCompte(), Compte_.numCompte));
            }
            if (criteria.getTypeCompte() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeCompte(), Compte_.typeCompte));
            }
            if (criteria.getDateOuverture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOuverture(), Compte_.dateOuverture));
            }
            if (criteria.getSolde() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSolde(), Compte_.solde));
            }
            if (criteria.getDateDernierCredit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDernierCredit(), Compte_.dateDernierCredit));
            }
            if (criteria.getDateDernierDebit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDernierDebit(), Compte_.dateDernierDebit));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClientId(), Compte_.client, Client_.id));
            }
            if (criteria.getPretId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPretId(), Compte_.pret, Pret_.id));
            }
            if (criteria.getCaisseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCaisseId(), Compte_.caisse, Caisse_.id));
            }
            if (criteria.getChapitreId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getChapitreId(), Compte_.chapitre, Chapitre_.id));
            }
        }
        return specification;
    }
    @Transactional(readOnly = true)
    public Compte getCompteByPretId(Long pretId) {
        CompteCriteria criteria = new CompteCriteria();
        LongFilter pretFilter = new LongFilter();
        pretFilter.setEquals(pretId);
        criteria.setPretId(pretFilter);
        List<Compte> list = findByCriteria(criteria);
        if(list!=null && list.size()==1) {
            return list.get(0);
        }
        throw new IllegalStateException("Trouvé "+list.size()+" compte(s) par pret Id = "+pretId);
    }
    @Transactional(readOnly = true)
    public List<Compte> findByChapitre(Long chapitreId) {
        CompteCriteria criteria = new CompteCriteria();
        LongFilter chapitreFilter = new LongFilter();
        chapitreFilter.setEquals(chapitreId);
        criteria.setChapitreId(chapitreFilter);
        return findByCriteria(criteria);
    }

    public Compte getCompteByCaisseId(Long caisseId) {
        CompteCriteria criteria = new CompteCriteria();
        LongFilter caissetFilter = new LongFilter();
        caissetFilter.setEquals(caisseId);
        criteria.setCaisseId(caissetFilter);
        List<Compte> list = findByCriteria(criteria);
        if(list!=null && list.size()==1) {
            return list.get(0);
        }
        throw new IllegalStateException("Trouvé "+list.size()+" compte(s) par caisse Id = "+caisseId);
    }

    public Compte getCompteBanque() {
        ParametrageCriteria paramCriteria = new ParametrageCriteria();
        StringFilter typeParam = new StringFilter();
        typeParam.setEquals("Compte");
        StringFilter codeParam = new StringFilter();
        codeParam.setEquals("CPT BANQ");
        paramCriteria.setCodeTypeParam(typeParam);
        paramCriteria.setCodeParam(codeParam);
        List<Parametrage> params = parametrage.findByCriteria(paramCriteria );
        String numCompte = params.get(0).getLibelle();
        CompteCriteria criteria = new CompteCriteria();
        StringFilter num = new StringFilter();
        num.setEquals(numCompte);
        criteria.setNumCompte(num);
        List<Compte> list = findByCriteria(criteria);
        if(list!=null && list.size()==1) {
            return list.get(0);
        }
        throw new IllegalStateException("Trouvé "+list.size()+" compte(s) en cherchant par le paramètre CPT BANQ ");
    }

}
