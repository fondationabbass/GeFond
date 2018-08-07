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
 * Criteria class for the Compte entity. This class is used in CompteResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /comptes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompteCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter intituleCompte;

    private LocalDateFilter dateOuverture;

    private DoubleFilter solde;

    private LocalDateFilter dateDernierCredit;

    private LocalDateFilter dateDernierDebit;

    private LongFilter clientId;

    private LongFilter pretId;

    private LongFilter userId;

    private LongFilter chapitreId;

    public CompteCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIntituleCompte() {
        return intituleCompte;
    }

    public void setIntituleCompte(StringFilter intituleCompte) {
        this.intituleCompte = intituleCompte;
    }

    public LocalDateFilter getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(LocalDateFilter dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public DoubleFilter getSolde() {
        return solde;
    }

    public void setSolde(DoubleFilter solde) {
        this.solde = solde;
    }

    public LocalDateFilter getDateDernierCredit() {
        return dateDernierCredit;
    }

    public void setDateDernierCredit(LocalDateFilter dateDernierCredit) {
        this.dateDernierCredit = dateDernierCredit;
    }

    public LocalDateFilter getDateDernierDebit() {
        return dateDernierDebit;
    }

    public void setDateDernierDebit(LocalDateFilter dateDernierDebit) {
        this.dateDernierDebit = dateDernierDebit;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public LongFilter getPretId() {
        return pretId;
    }

    public void setPretId(LongFilter pretId) {
        this.pretId = pretId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getChapitreId() {
        return chapitreId;
    }

    public void setChapitreId(LongFilter chapitreId) {
        this.chapitreId = chapitreId;
    }

    @Override
    public String toString() {
        return "CompteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (intituleCompte != null ? "intituleCompte=" + intituleCompte + ", " : "") +
                (dateOuverture != null ? "dateOuverture=" + dateOuverture + ", " : "") +
                (solde != null ? "solde=" + solde + ", " : "") +
                (dateDernierCredit != null ? "dateDernierCredit=" + dateDernierCredit + ", " : "") +
                (dateDernierDebit != null ? "dateDernierDebit=" + dateDernierDebit + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (pretId != null ? "pretId=" + pretId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (chapitreId != null ? "chapitreId=" + chapitreId + ", " : "") +
            "}";
    }

}
