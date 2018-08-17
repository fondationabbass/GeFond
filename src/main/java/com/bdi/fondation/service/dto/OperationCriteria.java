package com.bdi.fondation.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Operation entity. This class is used in OperationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /operations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OperationCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter dateOperation;

    private StringFilter typeOperation;

    private DoubleFilter montant;

    private StringFilter etat;

    private StringFilter moyenPaiement;

    private StringFilter commentaire;

    private StringFilter description;

    private LongFilter compteOriginId;

    private LongFilter compteDestinataireId;

    private LongFilter pretId;

    private LongFilter caisseId;

    private LongFilter echeanceId;

    public OperationCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(LocalDateFilter dateOperation) {
        this.dateOperation = dateOperation;
    }

    public StringFilter getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(StringFilter typeOperation) {
        this.typeOperation = typeOperation;
    }

    public DoubleFilter getMontant() {
        return montant;
    }

    public void setMontant(DoubleFilter montant) {
        this.montant = montant;
    }

    public StringFilter getEtat() {
        return etat;
    }

    public void setEtat(StringFilter etat) {
        this.etat = etat;
    }

    public StringFilter getMoyenPaiement() {
        return moyenPaiement;
    }

    public void setMoyenPaiement(StringFilter moyenPaiement) {
        this.moyenPaiement = moyenPaiement;
    }

    public StringFilter getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(StringFilter commentaire) {
        this.commentaire = commentaire;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getCompteOriginId() {
        return compteOriginId;
    }

    public void setCompteOriginId(LongFilter compteOriginId) {
        this.compteOriginId = compteOriginId;
    }

    public LongFilter getCompteDestinataireId() {
        return compteDestinataireId;
    }

    public void setCompteDestinataireId(LongFilter compteDestinataireId) {
        this.compteDestinataireId = compteDestinataireId;
    }

    public LongFilter getPretId() {
        return pretId;
    }

    public void setPretId(LongFilter pretId) {
        this.pretId = pretId;
    }

    public LongFilter getCaisseId() {
        return caisseId;
    }

    public void setCaisseId(LongFilter caisseId) {
        this.caisseId = caisseId;
    }

    public LongFilter getEcheanceId() {
        return echeanceId;
    }

    public void setEcheanceId(LongFilter echeanceId) {
        this.echeanceId = echeanceId;
    }

    @Override
    public String toString() {
        return "OperationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateOperation != null ? "dateOperation=" + dateOperation + ", " : "") +
                (typeOperation != null ? "typeOperation=" + typeOperation + ", " : "") +
                (montant != null ? "montant=" + montant + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (moyenPaiement != null ? "moyenPaiement=" + moyenPaiement + ", " : "") +
                (commentaire != null ? "commentaire=" + commentaire + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (compteOriginId != null ? "compteOriginId=" + compteOriginId + ", " : "") +
                (compteDestinataireId != null ? "compteDestinataireId=" + compteDestinataireId + ", " : "") +
                (pretId != null ? "pretId=" + pretId + ", " : "") +
                (caisseId != null ? "caisseId=" + caisseId + ", " : "") +
                (echeanceId != null ? "echeanceId=" + echeanceId + ", " : "") +
            "}";
    }

}
