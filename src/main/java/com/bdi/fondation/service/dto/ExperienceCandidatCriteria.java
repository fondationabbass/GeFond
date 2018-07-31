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
 * Criteria class for the ExperienceCandidat entity. This class is used in ExperienceCandidatResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /experience-candidats?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExperienceCandidatCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter typeInfo;

    private StringFilter titre;

    private StringFilter etab;

    private StringFilter adressEtab;

    private LocalDateFilter dateDeb;

    private LocalDateFilter dateFin;

    private LongFilter candidatId;

    public ExperienceCandidatCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(StringFilter typeInfo) {
        this.typeInfo = typeInfo;
    }

    public StringFilter getTitre() {
        return titre;
    }

    public void setTitre(StringFilter titre) {
        this.titre = titre;
    }

    public StringFilter getEtab() {
        return etab;
    }

    public void setEtab(StringFilter etab) {
        this.etab = etab;
    }

    public StringFilter getAdressEtab() {
        return adressEtab;
    }

    public void setAdressEtab(StringFilter adressEtab) {
        this.adressEtab = adressEtab;
    }

    public LocalDateFilter getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(LocalDateFilter dateDeb) {
        this.dateDeb = dateDeb;
    }

    public LocalDateFilter getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateFilter dateFin) {
        this.dateFin = dateFin;
    }

    public LongFilter getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(LongFilter candidatId) {
        this.candidatId = candidatId;
    }

    @Override
    public String toString() {
        return "ExperienceCandidatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (typeInfo != null ? "typeInfo=" + typeInfo + ", " : "") +
                (titre != null ? "titre=" + titre + ", " : "") +
                (etab != null ? "etab=" + etab + ", " : "") +
                (adressEtab != null ? "adressEtab=" + adressEtab + ", " : "") +
                (dateDeb != null ? "dateDeb=" + dateDeb + ", " : "") +
                (dateFin != null ? "dateFin=" + dateFin + ", " : "") +
                (candidatId != null ? "candidatId=" + candidatId + ", " : "") +
            "}";
    }

}
