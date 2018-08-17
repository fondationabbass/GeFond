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
 * Criteria class for the Caisse entity. This class is used in CaisseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /caisses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CaisseCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter intituleCaisse;

    private LocalDateFilter dateOuverture;

    private LongFilter userId;

    public CaisseCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIntituleCaisse() {
        return intituleCaisse;
    }

    public void setIntituleCaisse(StringFilter intituleCaisse) {
        this.intituleCaisse = intituleCaisse;
    }

    public LocalDateFilter getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(LocalDateFilter dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CaisseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (intituleCaisse != null ? "intituleCaisse=" + intituleCaisse + ", " : "") +
                (dateOuverture != null ? "dateOuverture=" + dateOuverture + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
