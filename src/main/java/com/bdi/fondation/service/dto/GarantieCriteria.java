package com.bdi.fondation.service.dto;

import java.io.Serializable;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;



/**
 * Criteria class for the Garantie entity. This class is used in GarantieResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /garanties?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GarantieCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter typeGar;

    private LocalDateFilter dateDepot;

    private StringFilter numDocument;

    private StringFilter etat;

    private LocalDateFilter dateRetrait;

    private LongFilter pretId;

    public GarantieCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTypeGar() {
        return typeGar;
    }

    public void setTypeGar(StringFilter typeGar) {
        this.typeGar = typeGar;
    }

    public LocalDateFilter getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(LocalDateFilter dateDepot) {
        this.dateDepot = dateDepot;
    }

    public StringFilter getNumDocument() {
        return numDocument;
    }

    public void setNumDocument(StringFilter numDocument) {
        this.numDocument = numDocument;
    }

    public StringFilter getEtat() {
        return etat;
    }

    public void setEtat(StringFilter etat) {
        this.etat = etat;
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

    @Override
    public String toString() {
        return "GarantieCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (typeGar != null ? "typeGar=" + typeGar + ", " : "") +
                (dateDepot != null ? "dateDepot=" + dateDepot + ", " : "") +
                (numDocument != null ? "numDocument=" + numDocument + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (dateRetrait != null ? "dateRetrait=" + dateRetrait + ", " : "") +
                (pretId != null ? "pretId=" + pretId + ", " : "") +
            "}";
    }

}
