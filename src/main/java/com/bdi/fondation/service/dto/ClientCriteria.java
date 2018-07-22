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
 * Criteria class for the Client entity. This class is used in ClientResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /clients?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClientCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter dateCreat;

    private StringFilter lieuResid;
    
    private StringFilter code;

    private StringFilter typeResid;

    private StringFilter arrondResid;

    private StringFilter nomPersonneContact;

    private StringFilter telPersonneContact;

    private StringFilter adressPersonneContact;

    private StringFilter typeClient;

    private DoubleFilter pointsFidel;

    private LocalDateFilter dateMaj;

    private LongFilter candidatId;

    public ClientCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateCreat() {
        return dateCreat;
    }

    public void setDateCreat(LocalDateFilter dateCreat) {
        this.dateCreat = dateCreat;
    }

    public StringFilter getLieuResid() {
        return lieuResid;
    }

    public void setLieuResid(StringFilter lieuResid) {
        this.lieuResid = lieuResid;
    }

    public StringFilter getTypeResid() {
        return typeResid;
    }

    public void setTypeResid(StringFilter typeResid) {
        this.typeResid = typeResid;
    }

    public StringFilter getArrondResid() {
        return arrondResid;
    }

    public void setArrondResid(StringFilter arrondResid) {
        this.arrondResid = arrondResid;
    }

    public StringFilter getNomPersonneContact() {
        return nomPersonneContact;
    }

    public void setNomPersonneContact(StringFilter nomPersonneContact) {
        this.nomPersonneContact = nomPersonneContact;
    }

    public StringFilter getTelPersonneContact() {
        return telPersonneContact;
    }

    public void setTelPersonneContact(StringFilter telPersonneContact) {
        this.telPersonneContact = telPersonneContact;
    }

    public StringFilter getAdressPersonneContact() {
        return adressPersonneContact;
    }

    public void setAdressPersonneContact(StringFilter adressPersonneContact) {
        this.adressPersonneContact = adressPersonneContact;
    }

    public StringFilter getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(StringFilter typeClient) {
        this.typeClient = typeClient;
    }

    public DoubleFilter getPointsFidel() {
        return pointsFidel;
    }

    public void setPointsFidel(DoubleFilter pointsFidel) {
        this.pointsFidel = pointsFidel;
    }

    public LocalDateFilter getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(LocalDateFilter dateMaj) {
        this.dateMaj = dateMaj;
    }

    public LongFilter getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(LongFilter candidatId) {
        this.candidatId = candidatId;
    }

    public StringFilter getCode() {
		return code;
	}

	public void setCode(StringFilter code) {
		this.code = code;
	}

	@Override
    public String toString() {
        return "ClientCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (dateCreat != null ? "dateCreat=" + dateCreat + ", " : "") +
                (lieuResid != null ? "lieuResid=" + lieuResid + ", " : "") +
                (typeResid != null ? "typeResid=" + typeResid + ", " : "") +
                (arrondResid != null ? "arrondResid=" + arrondResid + ", " : "") +
                (nomPersonneContact != null ? "nomPersonneContact=" + nomPersonneContact + ", " : "") +
                (telPersonneContact != null ? "telPersonneContact=" + telPersonneContact + ", " : "") +
                (adressPersonneContact != null ? "adressPersonneContact=" + adressPersonneContact + ", " : "") +
                (typeClient != null ? "typeClient=" + typeClient + ", " : "") +
                (pointsFidel != null ? "pointsFidel=" + pointsFidel + ", " : "") +
                (dateMaj != null ? "dateMaj=" + dateMaj + ", " : "") +
                (candidatId != null ? "candidatId=" + candidatId + ", " : "") +
            "}";
    }

}
