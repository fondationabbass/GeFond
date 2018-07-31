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
 * Criteria class for the Parametrage entity. This class is used in ParametrageResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /parametrages?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParametrageCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter codeTypeParam;

    private StringFilter codeParam;

    private StringFilter libelle;

    private StringFilter lib1;

    private StringFilter lib2;

    private StringFilter lib3;

    private DoubleFilter mnt1;

    private DoubleFilter mnt2;

    private DoubleFilter mnt3;

    public ParametrageCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodeTypeParam() {
        return codeTypeParam;
    }

    public void setCodeTypeParam(StringFilter codeTypeParam) {
        this.codeTypeParam = codeTypeParam;
    }

    public StringFilter getCodeParam() {
        return codeParam;
    }

    public void setCodeParam(StringFilter codeParam) {
        this.codeParam = codeParam;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public StringFilter getLib1() {
        return lib1;
    }

    public void setLib1(StringFilter lib1) {
        this.lib1 = lib1;
    }

    public StringFilter getLib2() {
        return lib2;
    }

    public void setLib2(StringFilter lib2) {
        this.lib2 = lib2;
    }

    public StringFilter getLib3() {
        return lib3;
    }

    public void setLib3(StringFilter lib3) {
        this.lib3 = lib3;
    }

    public DoubleFilter getMnt1() {
        return mnt1;
    }

    public void setMnt1(DoubleFilter mnt1) {
        this.mnt1 = mnt1;
    }

    public DoubleFilter getMnt2() {
        return mnt2;
    }

    public void setMnt2(DoubleFilter mnt2) {
        this.mnt2 = mnt2;
    }

    public DoubleFilter getMnt3() {
        return mnt3;
    }

    public void setMnt3(DoubleFilter mnt3) {
        this.mnt3 = mnt3;
    }

    @Override
    public String toString() {
        return "ParametrageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codeTypeParam != null ? "codeTypeParam=" + codeTypeParam + ", " : "") +
                (codeParam != null ? "codeParam=" + codeParam + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (lib1 != null ? "lib1=" + lib1 + ", " : "") +
                (lib2 != null ? "lib2=" + lib2 + ", " : "") +
                (lib3 != null ? "lib3=" + lib3 + ", " : "") +
                (mnt1 != null ? "mnt1=" + mnt1 + ", " : "") +
                (mnt2 != null ? "mnt2=" + mnt2 + ", " : "") +
                (mnt3 != null ? "mnt3=" + mnt3 + ", " : "") +
            "}";
    }

}
