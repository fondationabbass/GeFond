package com.bdi.fondation.service.dto;

import java.io.Serializable;

import org.hibernate.tuple.entity.EntityBasedAssociationAttribute;

import com.bdi.fondation.domain.Echeance;
import com.bdi.fondation.domain.Echeance_;

import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Pret entity. This class is used in PretResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /prets?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PretCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter typPret;

    private DoubleFilter montAaccord;

    private DoubleFilter montDebloq;

    private IntegerFilter nbrEcheance;

    private StringFilter periodicite;

    private LocalDateFilter dateMisePlace;

    private LocalDateFilter datePremiereEcheance;

    private LocalDateFilter dateDerniereEcheance;

    private LocalDateFilter dateDernierDebloq;

    private StringFilter etat;

    private DoubleFilter encours;

    private StringFilter userInitial;

    private StringFilter userDecideur;

    private StringFilter userDebloq;

    private LongFilter clientId;
    
    public Echeance_ getEcheance() {
		return Echeance;
	}

	public void setEcheance(Echeance_ echeance) {
		Echeance = echeance;
	}

	private Echeance_ Echeance;

    public PretCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTypPret() {
        return typPret;
    }

    public void setTypPret(StringFilter typPret) {
        this.typPret = typPret;
    }

    public DoubleFilter getMontAaccord() {
        return montAaccord;
    }

    public void setMontAaccord(DoubleFilter montAaccord) {
        this.montAaccord = montAaccord;
    }

    public DoubleFilter getMontDebloq() {
        return montDebloq;
    }

    public void setMontDebloq(DoubleFilter montDebloq) {
        this.montDebloq = montDebloq;
    }

    public IntegerFilter getNbrEcheance() {
        return nbrEcheance;
    }

    public void setNbrEcheance(IntegerFilter nbrEcheance) {
        this.nbrEcheance = nbrEcheance;
    }

    public StringFilter getPeriodicite() {
        return periodicite;
    }

    public void setPeriodicite(StringFilter periodicite) {
        this.periodicite = periodicite;
    }

    public LocalDateFilter getDateMisePlace() {
        return dateMisePlace;
    }

    public void setDateMisePlace(LocalDateFilter dateMisePlace) {
        this.dateMisePlace = dateMisePlace;
    }

    public LocalDateFilter getDatePremiereEcheance() {
        return datePremiereEcheance;
    }

    public void setDatePremiereEcheance(LocalDateFilter datePremiereEcheance) {
        this.datePremiereEcheance = datePremiereEcheance;
    }

    public LocalDateFilter getDateDerniereEcheance() {
        return dateDerniereEcheance;
    }

    public void setDateDerniereEcheance(LocalDateFilter dateDerniereEcheance) {
        this.dateDerniereEcheance = dateDerniereEcheance;
    }

    public LocalDateFilter getDateDernierDebloq() {
        return dateDernierDebloq;
    }

    public void setDateDernierDebloq(LocalDateFilter dateDernierDebloq) {
        this.dateDernierDebloq = dateDernierDebloq;
    }

    public StringFilter getEtat() {
        return etat;
    }

    public void setEtat(StringFilter etat) {
        this.etat = etat;
    }

    public DoubleFilter getEncours() {
        return encours;
    }

    public void setEncours(DoubleFilter encours) {
        this.encours = encours;
    }

    public StringFilter getUserInitial() {
        return userInitial;
    }

    public void setUserInitial(StringFilter userInitial) {
        this.userInitial = userInitial;
    }

    public StringFilter getUserDecideur() {
        return userDecideur;
    }

    public void setUserDecideur(StringFilter userDecideur) {
        this.userDecideur = userDecideur;
    }

    public StringFilter getUserDebloq() {
        return userDebloq;
    }

    public void setUserDebloq(StringFilter userDebloq) {
        this.userDebloq = userDebloq;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "PretCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (typPret != null ? "typPret=" + typPret + ", " : "") +
                (montAaccord != null ? "montAaccord=" + montAaccord + ", " : "") +
                (montDebloq != null ? "montDebloq=" + montDebloq + ", " : "") +
                (nbrEcheance != null ? "nbrEcheance=" + nbrEcheance + ", " : "") +
                (periodicite != null ? "periodicite=" + periodicite + ", " : "") +
                (dateMisePlace != null ? "dateMisePlace=" + dateMisePlace + ", " : "") +
                (datePremiereEcheance != null ? "datePremiereEcheance=" + datePremiereEcheance + ", " : "") +
                (dateDerniereEcheance != null ? "dateDerniereEcheance=" + dateDerniereEcheance + ", " : "") +
                (dateDernierDebloq != null ? "dateDernierDebloq=" + dateDernierDebloq + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (encours != null ? "encours=" + encours + ", " : "") +
                (userInitial != null ? "userInitial=" + userInitial + ", " : "") +
                (userDecideur != null ? "userDecideur=" + userDecideur + ", " : "") +
                (userDebloq != null ? "userDebloq=" + userDebloq + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
            "}";
    }

}
