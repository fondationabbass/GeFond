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
 * Criteria class for the Echeance entity. This class is used in EcheanceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /echeances?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EcheanceCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter dateTombe;

    private DoubleFilter montant;

    private StringFilter etatEcheance;

    private LocalDateFilter datePayement;

    private LocalDateFilter dateRetrait;

    private LongFilter pretId;

    private LongFilter mouvementId;

    public EcheanceCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateTombe() {
        return dateTombe;
    }

    public void setDateTombe(LocalDateFilter dateTombe) {
        this.dateTombe = dateTombe;
    }

    public DoubleFilter getMontant() {
        return montant;
    }

    public void setMontant(DoubleFilter montant) {
        this.montant = montant;
    }

    public StringFilter getEtatEcheance() {
        return etatEcheance;
    }

    public void setEtatEcheance(StringFilter etatEcheance) {
        this.etatEcheance = etatEcheance;
    }

    public LocalDateFilter getDatePayement() {
        return datePayement;
    }

    public void setDatePayement(LocalDateFilter datePayement) {
        this.datePayement = datePayement;
    }

    public LocalDateFilter getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(LocalDateFilter dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public LongFilter getPretId() {
        return pretId;
    }

    public void setPretId(LongFilter pretId) {
        this.pretId = pretId;
    }

    public LongFilter getMouvementId() {
        return mouvementId;
    }

    public void setMouvementId(LongFilter mouvementId) {
        this.mouvementId = mouvementId;
    }

    @Override
    public String toString() {
        return "EcheanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateTombe != null ? "dateTombe=" + dateTombe + ", " : "") +
                (montant != null ? "montant=" + montant + ", " : "") +
                (etatEcheance != null ? "etatEcheance=" + etatEcheance + ", " : "") +
                (datePayement != null ? "datePayement=" + datePayement + ", " : "") +
                (dateRetrait != null ? "dateRetrait=" + dateRetrait + ", " : "") +
                (pretId != null ? "pretId=" + pretId + ", " : "") +
                (mouvementId != null ? "mouvementId=" + mouvementId + ", " : "") +
            "}";
    }

}
