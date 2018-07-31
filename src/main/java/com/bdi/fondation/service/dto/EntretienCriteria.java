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
 * Criteria class for the Entretien entity. This class is used in EntretienResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /entretiens?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EntretienCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter cadre;

    private StringFilter resultat;

    private StringFilter interlocuteur;

    private StringFilter etat;

    private LocalDateFilter dateEntretien;

    private LongFilter candidatureId;

    public EntretienCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCadre() {
        return cadre;
    }

    public void setCadre(StringFilter cadre) {
        this.cadre = cadre;
    }

    public StringFilter getResultat() {
        return resultat;
    }

    public void setResultat(StringFilter resultat) {
        this.resultat = resultat;
    }

    public StringFilter getInterlocuteur() {
        return interlocuteur;
    }

    public void setInterlocuteur(StringFilter interlocuteur) {
        this.interlocuteur = interlocuteur;
    }

    public StringFilter getEtat() {
        return etat;
    }

    public void setEtat(StringFilter etat) {
        this.etat = etat;
    }

    public LocalDateFilter getDateEntretien() {
        return dateEntretien;
    }

    public void setDateEntretien(LocalDateFilter dateEntretien) {
        this.dateEntretien = dateEntretien;
    }

    public LongFilter getCandidatureId() {
        return candidatureId;
    }

    public void setCandidatureId(LongFilter candidatureId) {
        this.candidatureId = candidatureId;
    }

    @Override
    public String toString() {
        return "EntretienCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cadre != null ? "cadre=" + cadre + ", " : "") +
                (resultat != null ? "resultat=" + resultat + ", " : "") +
                (interlocuteur != null ? "interlocuteur=" + interlocuteur + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (dateEntretien != null ? "dateEntretien=" + dateEntretien + ", " : "") +
                (candidatureId != null ? "candidatureId=" + candidatureId + ", " : "") +
            "}";
    }

}
