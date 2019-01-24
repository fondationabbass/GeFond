package com.bdi.fondation.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdi.fondation.config.Constants;
import com.bdi.fondation.domain.Caisse;
import com.bdi.fondation.domain.Compte;
import com.bdi.fondation.domain.Echeance;
import com.bdi.fondation.domain.Mouvement;
import com.bdi.fondation.domain.Operation;
import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.repository.OperationRepository;
import com.bdi.fondation.security.SecurityUtils;


/**
 * Service Implementation for managing Operation.
 */
@Service
@Transactional
public class OperationService {

    private final Logger log = LoggerFactory.getLogger(OperationService.class);

    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private PretService pretService;
    @Autowired
    private MouvementService mouvementService;
    @Autowired
    private EcheanceService echeanceService;
    @Autowired
    private EcheanceQueryService echeanceQueryService;
    @Autowired
    private CompteQueryService compteQueryService;
    @Autowired
    private CaisseQueryService caisseQueryService;

    /**
     * Save a operation.
     *
     * @param operation the entity to save
     * @return the persisted entity
     */

    private static class CompteMouvement {

        public static CompteMouvement of(Compte compte, String lib) {
           return new CompteMouvement(compte, lib);
        }
        public CompteMouvement(Compte compte, String lib) {
            this.compte = compte;
            this.lib = lib;
        }
        private Compte compte;
        private String lib;
    }

    public Operation save(Operation operation) {
        log.debug("Request to save Operation : {}", operation);
        Double montant = operation.getMontant();
        if(montant == null || operation.getMontant() < 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }
        operation.setDateOperation(LocalDate.now());
        Pret pret = operation.getPret();
        if(Constants.FINANCEMENT.equals(operation.getTypeOperation())) {
            financementPret(pret, montant);
            ajusterEcheancier(montant, pret);
            Caisse caisse = caisseQueryService.getCurrentCaisse();
            Compte destinataire = pretService.getOrCreateCompte(pret);
            Compte origin = compteQueryService.getCompteByCaisseId(caisse.getId());
            operation.setCaisse(caisse);
            operation.setCompteOrigin(origin);
            operation.setCompteDestinataire(destinataire);
            mouvementService.save(mouvements(operation,
                    CompteMouvement.of(origin,Constants.FINANCEMENT),
                    CompteMouvement.of(destinataire,Constants.FINANCEMENT)));
        } else if(Constants.REMBOURSEMENT.equals(operation.getTypeOperation())) {
            remboursementPret(pret, montant);
            Set<Echeance> set = dispatcherEcheances(operation);
            operation.setEcheances(set);
            Compte origin = compteQueryService.getCompteByPretId(pret.getId());
            Compte destinataire = null;
            if(Constants.REMBOURSEMENT_SUR_COMPTE.equals(operation.getMoyenPaiement())) {
                destinataire = compteQueryService.getCompteBanque();
            } else {
                Caisse caisse = caisseQueryService.getCurrentCaisse();
                destinataire = compteQueryService.getCompteByCaisseId(caisse.getId());
                operation.setCaisse(caisse);
            }
            operation.setCompteOrigin(origin);
            operation.setCompteDestinataire(destinataire);
            mouvementService.save(mouvements(operation,
                    CompteMouvement.of(origin,Constants.REMBOURSEMENT),
                    CompteMouvement.of(destinataire,Constants.REMBOURSEMENT)));
        }
        else {
            if(operation.getCompteOrigin() == null || operation.getCompteDestinataire() == null) {
                throw new IllegalArgumentException("Les comptes doivent être renseigné pour une opération générique.");
            }
            mouvementService.save(mouvements(operation,
                    CompteMouvement.of(operation.getCompteOrigin(),operation.getTypeOperation()),
                    CompteMouvement.of(operation.getCompteDestinataire(),operation.getTypeOperation())));

        }
        Operation result = operationRepository.save(operation);
        return result;
    }
    private void remboursementPret(Pret pret, Double montant) {
        if(pret.getEncours() - montant < 0) {
            throw new IllegalStateException("Le remboursement dépasse l'encours");
        }
        pret.setEncours(pret.getEncours() - montant);
        pretService.save(pret);
    }
    private void ajusterEcheancier(Double montant, Pret pret) {
        List<Echeance> echeances = echeanceQueryService.findNotPayedByPretId(pret.getId());
        if(echeances.size() > 0) {
            double rounded = Math.round(100 * montant/echeances.size());
            double addOn = rounded / 100d;
            echeances.forEach(e->e.setMontant(e.getMontant() + addOn));
        }
    }
    private void financementPret(Pret pret, Double montant) {
        if(pret.getMontDebloq() + montant > pret.getMontAaccord()) {
            throw new IllegalStateException("Le montant débloqué dépasse le plafond du pret.");
        }
        pret.setMontDebloq(pret.getMontDebloq() + montant);
        pret.setDateDernierDebloq(LocalDate.now());
        pret.setEtat(Constants.MIS_EN_PLACE);
        pret.setEncours(pret.getEncours() + montant);
        pret.setUserDebloq(SecurityUtils.getCurrentUserLogin().get());
        pretService.save(pret);
    }

    private List<Mouvement>  mouvements(Operation operation, CompteMouvement credit, CompteMouvement debit) {
        List<Mouvement> mvts = new ArrayList<>();
        mvts.add(credit(operation).operation(operation).compte(credit.compte).lib(credit.lib));
        mvts.add(debit(operation).operation(operation).compte(debit.compte).lib(debit.lib));
        return mvts;
    }
    private Mouvement credit(Operation operation) {
        Mouvement mouvement = mouvement(operation);
        mouvement.setSens(Constants.CREDIT);
        return mouvement;
    }
    private Mouvement mouvement(Operation operation) {
        Mouvement mouvement = new Mouvement();
        mouvement.setMontant(operation.getMontant());
        mouvement.setDateMvt(operation.getDateOperation());
        mouvement.setEtat(Constants.FAIT);
        return mouvement;
    }
    private Mouvement debit(Operation operation) {
        Mouvement mouvement = mouvement(operation);
        mouvement.setSens(Constants.DEBIT);
        return mouvement;
    }
    private Set<Echeance> dispatcherEcheances(Operation operation) {
        List<Echeance> echeances = echeanceQueryService.findNotPayedByPretId(operation.getPret().getId());
        Collections.sort(echeances, (o1, o2) -> o1.getDateTombe().compareTo(o2.getDateTombe()));
        Set<Echeance> set = new HashSet<>();
        double plafond = operation.getMontant();
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
     * Get all the operations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Operation> findAll(Pageable pageable) {
        log.debug("Request to get all Operations");
        return operationRepository.findAll(pageable);
    }

    /**
     * Get one operation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Operation findOne(Long id) {
        log.debug("Request to get Operation : {}", id);
        return operationRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the operation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Operation : {}", id);
        operationRepository.delete(id);
    }
}
