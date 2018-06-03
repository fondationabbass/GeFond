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
 * Criteria class for the Projet entity. This class is used in ProjetResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /projets?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjetCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter intitule;

    private DoubleFilter montEstime;

    private DoubleFilter montApp;

    private StringFilter domaine;

    private StringFilter type;

    private StringFilter description;

    private LocalDateFilter dateCreation;

    private StringFilter etat;

    private StringFilter lieu;

    private LongFilter candidatureId;

    public ProjetCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIntitule() {
        return intitule;
    }

    public void setIntitule(StringFilter intitule) {
        this.intitule = intitule;
    }

    public DoubleFilter getMontEstime() {
        return montEstime;
    }

    public void setMontEstime(DoubleFilter montEstime) {
        this.montEstime = montEstime;
    }

    public DoubleFilter getMontApp() {
        return montApp;
    }

    public void setMontApp(DoubleFilter montApp) {
        this.montApp = montApp;
    }

    public StringFilter getDomaine() {
        return domaine;
    }

    public void setDomaine(StringFilter domaine) {
        this.domaine = domaine;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LocalDateFilter getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateFilter dateCreation) {
        this.dateCreation = dateCreation;
    }

    public StringFilter getEtat() {
        return etat;
    }

    public void setEtat(StringFilter etat) {
        this.etat = etat;
    }

    public StringFilter getLieu() {
        return lieu;
    }

    public void setLieu(StringFilter lieu) {
        this.lieu = lieu;
    }

    public LongFilter getCandidatureId() {
        return candidatureId;
    }

    public void setCandidatureId(LongFilter candidatureId) {
        this.candidatureId = candidatureId;
    }

    @Override
    public String toString() {
        return "ProjetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (intitule != null ? "intitule=" + intitule + ", " : "") +
                (montEstime != null ? "montEstime=" + montEstime + ", " : "") +
                (montApp != null ? "montApp=" + montApp + ", " : "") +
                (domaine != null ? "domaine=" + domaine + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (dateCreation != null ? "dateCreation=" + dateCreation + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (lieu != null ? "lieu=" + lieu + ", " : "") +
                (candidatureId != null ? "candidatureId=" + candidatureId + ", " : "") +
            "}";
    }

}
