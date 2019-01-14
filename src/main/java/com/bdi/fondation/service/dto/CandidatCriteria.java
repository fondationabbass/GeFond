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
 * Criteria class for the Candidat entity. This class is used in CandidatResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /candidats?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CandidatCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter nni;

    private StringFilter nom;

    private StringFilter prenom;

    private StringFilter prenomPere;

    private LocalDateFilter dateNaissance;

    private StringFilter lieuNaissance;

    private StringFilter adresse;

    private StringFilter tel;

    private StringFilter situation;

    public CandidatCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNni() {
        return nni;
    }

    public void setNni(IntegerFilter nni) {
        this.nni = nni;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getPrenom() {
        return prenom;
    }

    public void setPrenom(StringFilter prenom) {
        this.prenom = prenom;
    }

    public StringFilter getPrenomPere() {
        return prenomPere;
    }

    public void setPrenomPere(StringFilter prenomPere) {
        this.prenomPere = prenomPere;
    }

    public LocalDateFilter getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDateFilter dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public StringFilter getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(StringFilter lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }

    public StringFilter getTel() {
        return tel;
    }

    public void setTel(StringFilter tel) {
        this.tel = tel;
    }

    public StringFilter getSituation() {
        return situation;
    }

    public void setSituation(StringFilter situation) {
        this.situation = situation;
    }

    @Override
    public String toString() {
        return "CandidatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nni != null ? "nni=" + nni + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (prenomPere != null ? "prenomPere=" + prenomPere + ", " : "") +
                (dateNaissance != null ? "dateNaissance=" + dateNaissance + ", " : "") +
                (lieuNaissance != null ? "lieuNaissance=" + lieuNaissance + ", " : "") +
                (adresse != null ? "adresse=" + adresse + ", " : "") +
                (tel != null ? "tel=" + tel + ", " : "") +
                (situation != null ? "situation=" + situation + ", " : "") +
            "}";
    }

}
