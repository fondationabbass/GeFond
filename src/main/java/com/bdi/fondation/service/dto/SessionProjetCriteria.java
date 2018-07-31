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
 * Criteria class for the SessionProjet entity. This class is used in SessionProjetResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /session-projets?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SessionProjetCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter dateOuvert;

    private LocalDateFilter dateFermeture;

    private DoubleFilter plafondFinance;

    private IntegerFilter nombreClient;

    private DoubleFilter plafondClient;

    private LocalDateFilter dateCreat;

    private StringFilter dateMaj;

    private StringFilter etat;

    public SessionProjetCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateOuvert() {
        return dateOuvert;
    }

    public void setDateOuvert(LocalDateFilter dateOuvert) {
        this.dateOuvert = dateOuvert;
    }

    public LocalDateFilter getDateFermeture() {
        return dateFermeture;
    }

    public void setDateFermeture(LocalDateFilter dateFermeture) {
        this.dateFermeture = dateFermeture;
    }

    public DoubleFilter getPlafondFinance() {
        return plafondFinance;
    }

    public void setPlafondFinance(DoubleFilter plafondFinance) {
        this.plafondFinance = plafondFinance;
    }

    public IntegerFilter getNombreClient() {
        return nombreClient;
    }

    public void setNombreClient(IntegerFilter nombreClient) {
        this.nombreClient = nombreClient;
    }

    public DoubleFilter getPlafondClient() {
        return plafondClient;
    }

    public void setPlafondClient(DoubleFilter plafondClient) {
        this.plafondClient = plafondClient;
    }

    public LocalDateFilter getDateCreat() {
        return dateCreat;
    }

    public void setDateCreat(LocalDateFilter dateCreat) {
        this.dateCreat = dateCreat;
    }

    public StringFilter getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(StringFilter dateMaj) {
        this.dateMaj = dateMaj;
    }

    public StringFilter getEtat() {
        return etat;
    }

    public void setEtat(StringFilter etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "SessionProjetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateOuvert != null ? "dateOuvert=" + dateOuvert + ", " : "") +
                (dateFermeture != null ? "dateFermeture=" + dateFermeture + ", " : "") +
                (plafondFinance != null ? "plafondFinance=" + plafondFinance + ", " : "") +
                (nombreClient != null ? "nombreClient=" + nombreClient + ", " : "") +
                (plafondClient != null ? "plafondClient=" + plafondClient + ", " : "") +
                (dateCreat != null ? "dateCreat=" + dateCreat + ", " : "") +
                (dateMaj != null ? "dateMaj=" + dateMaj + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
            "}";
    }

}
