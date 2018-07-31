package com.bdi.fondation.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Chapitre entity. This class is used in ChapitreResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /chapitres?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChapitreCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter libChapitre;

    private StringFilter categorieCompte;

    public ChapitreCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibChapitre() {
        return libChapitre;
    }

    public void setLibChapitre(StringFilter libChapitre) {
        this.libChapitre = libChapitre;
    }

    public StringFilter getCategorieCompte() {
        return categorieCompte;
    }

    public void setCategorieCompte(StringFilter categorieCompte) {
        this.categorieCompte = categorieCompte;
    }

    @Override
    public String toString() {
        return "ChapitreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libChapitre != null ? "libChapitre=" + libChapitre + ", " : "") +
                (categorieCompte != null ? "categorieCompte=" + categorieCompte + ", " : "") +
            "}";
    }

}
