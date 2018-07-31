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
 * Criteria class for the Visite entity. This class is used in VisiteResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /visites?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VisiteCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter lieuVisite;

    private LocalDateFilter dateVisite;

    private IntegerFilter persRencontre;

    private StringFilter cadreVisite;

    private StringFilter etatLieu;

    private StringFilter visiteur;

    private StringFilter etat;

    private StringFilter recomendation;

    private StringFilter rapport;

    private LongFilter candidatureId;

    public VisiteCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLieuVisite() {
        return lieuVisite;
    }

    public void setLieuVisite(StringFilter lieuVisite) {
        this.lieuVisite = lieuVisite;
    }

    public LocalDateFilter getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(LocalDateFilter dateVisite) {
        this.dateVisite = dateVisite;
    }

    public IntegerFilter getPersRencontre() {
        return persRencontre;
    }

    public void setPersRencontre(IntegerFilter persRencontre) {
        this.persRencontre = persRencontre;
    }

    public StringFilter getCadreVisite() {
        return cadreVisite;
    }

    public void setCadreVisite(StringFilter cadreVisite) {
        this.cadreVisite = cadreVisite;
    }

    public StringFilter getEtatLieu() {
        return etatLieu;
    }

    public void setEtatLieu(StringFilter etatLieu) {
        this.etatLieu = etatLieu;
    }

    public StringFilter getVisiteur() {
        return visiteur;
    }

    public void setVisiteur(StringFilter visiteur) {
        this.visiteur = visiteur;
    }

    public StringFilter getEtat() {
        return etat;
    }

    public void setEtat(StringFilter etat) {
        this.etat = etat;
    }

    public StringFilter getRecomendation() {
        return recomendation;
    }

    public void setRecomendation(StringFilter recomendation) {
        this.recomendation = recomendation;
    }

    public StringFilter getRapport() {
        return rapport;
    }

    public void setRapport(StringFilter rapport) {
        this.rapport = rapport;
    }

    public LongFilter getCandidatureId() {
        return candidatureId;
    }

    public void setCandidatureId(LongFilter candidatureId) {
        this.candidatureId = candidatureId;
    }

    @Override
    public String toString() {
        return "VisiteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (lieuVisite != null ? "lieuVisite=" + lieuVisite + ", " : "") +
                (dateVisite != null ? "dateVisite=" + dateVisite + ", " : "") +
                (persRencontre != null ? "persRencontre=" + persRencontre + ", " : "") +
                (cadreVisite != null ? "cadreVisite=" + cadreVisite + ", " : "") +
                (etatLieu != null ? "etatLieu=" + etatLieu + ", " : "") +
                (visiteur != null ? "visiteur=" + visiteur + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (recomendation != null ? "recomendation=" + recomendation + ", " : "") +
                (rapport != null ? "rapport=" + rapport + ", " : "") +
                (candidatureId != null ? "candidatureId=" + candidatureId + ", " : "") +
            "}";
    }

}
