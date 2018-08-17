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
 * Criteria class for the Mouvement entity. This class is used in MouvementResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /mouvements?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MouvementCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter dateMvt;

    private StringFilter lib;

    private DoubleFilter montant;

    private StringFilter sens;

    private StringFilter etat;

    private LongFilter compteId;

    private LongFilter operationId;

    public MouvementCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateMvt() {
        return dateMvt;
    }

    public void setDateMvt(LocalDateFilter dateMvt) {
        this.dateMvt = dateMvt;
    }

    public StringFilter getLib() {
        return lib;
    }

    public void setLib(StringFilter lib) {
        this.lib = lib;
    }

    public DoubleFilter getMontant() {
        return montant;
    }

    public void setMontant(DoubleFilter montant) {
        this.montant = montant;
    }

    public StringFilter getSens() {
        return sens;
    }

    public void setSens(StringFilter sens) {
        this.sens = sens;
    }

    public StringFilter getEtat() {
        return etat;
    }

    public void setEtat(StringFilter etat) {
        this.etat = etat;
    }

    public LongFilter getCompteId() {
        return compteId;
    }

    public void setCompteId(LongFilter compteId) {
        this.compteId = compteId;
    }

    public LongFilter getOperationId() {
        return operationId;
    }

    public void setOperationId(LongFilter operationId) {
        this.operationId = operationId;
    }

    @Override
    public String toString() {
        return "MouvementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateMvt != null ? "dateMvt=" + dateMvt + ", " : "") +
                (lib != null ? "lib=" + lib + ", " : "") +
                (montant != null ? "montant=" + montant + ", " : "") +
                (sens != null ? "sens=" + sens + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (compteId != null ? "compteId=" + compteId + ", " : "") +
                (operationId != null ? "operationId=" + operationId + ", " : "") +
            "}";
    }

}
