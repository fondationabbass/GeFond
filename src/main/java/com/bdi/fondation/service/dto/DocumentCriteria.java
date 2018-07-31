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
 * Criteria class for the Document entity. This class is used in DocumentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /documents?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DocumentCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter dateEnreg;

    private StringFilter lib;

    private StringFilter typeDocument;

    private StringFilter module;

    private StringFilter etat;

    private StringFilter fichier;

    private StringFilter tail;

    private LongFilter candidatureId;

    public DocumentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateEnreg() {
        return dateEnreg;
    }

    public void setDateEnreg(LocalDateFilter dateEnreg) {
        this.dateEnreg = dateEnreg;
    }

    public StringFilter getLib() {
        return lib;
    }

    public void setLib(StringFilter lib) {
        this.lib = lib;
    }

    public StringFilter getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(StringFilter typeDocument) {
        this.typeDocument = typeDocument;
    }

    public StringFilter getModule() {
        return module;
    }

    public void setModule(StringFilter module) {
        this.module = module;
    }

    public StringFilter getEtat() {
        return etat;
    }

    public void setEtat(StringFilter etat) {
        this.etat = etat;
    }

    public StringFilter getFichier() {
        return fichier;
    }

    public void setFichier(StringFilter fichier) {
        this.fichier = fichier;
    }

    public StringFilter getTail() {
        return tail;
    }

    public void setTail(StringFilter tail) {
        this.tail = tail;
    }

    public LongFilter getCandidatureId() {
        return candidatureId;
    }

    public void setCandidatureId(LongFilter candidatureId) {
        this.candidatureId = candidatureId;
    }

    @Override
    public String toString() {
        return "DocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateEnreg != null ? "dateEnreg=" + dateEnreg + ", " : "") +
                (lib != null ? "lib=" + lib + ", " : "") +
                (typeDocument != null ? "typeDocument=" + typeDocument + ", " : "") +
                (module != null ? "module=" + module + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (fichier != null ? "fichier=" + fichier + ", " : "") +
                (tail != null ? "tail=" + tail + ", " : "") +
                (candidatureId != null ? "candidatureId=" + candidatureId + ", " : "") +
            "}";
    }

}
