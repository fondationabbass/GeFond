package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Parametrage.
 */
@Entity
@Table(name = "parametrage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Parametrage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_param")
    private String codeTypeParam;

    @Column(name = "code_param")
    private String codeParam;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "lib_1")
    private String lib1;

    @Column(name = "lib_2")
    private String lib2;

    @Column(name = "lib_3")
    private String lib3;

    @Column(name = "mnt_1")
    private Double mnt1;

    @Column(name = "mnt_2")
    private Double mnt2;

    @Column(name = "mnt_3")
    private Double mnt3;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeParam() {
        return codeTypeParam;
    }

    public Parametrage codeTypeParam(String codeTypeParam) {
        this.codeTypeParam = codeTypeParam;
        return this;
    }

    public void setCodeTypeParam(String codeTypeParam) {
        this.codeTypeParam = codeTypeParam;
    }

    public String getCodeParam() {
        return codeParam;
    }

    public Parametrage codeParam(String codeParam) {
        this.codeParam = codeParam;
        return this;
    }

    public void setCodeParam(String codeParam) {
        this.codeParam = codeParam;
    }

    public String getLibelle() {
        return libelle;
    }

    public Parametrage libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLib1() {
        return lib1;
    }

    public Parametrage lib1(String lib1) {
        this.lib1 = lib1;
        return this;
    }

    public void setLib1(String lib1) {
        this.lib1 = lib1;
    }

    public String getLib2() {
        return lib2;
    }

    public Parametrage lib2(String lib2) {
        this.lib2 = lib2;
        return this;
    }

    public void setLib2(String lib2) {
        this.lib2 = lib2;
    }

    public String getLib3() {
        return lib3;
    }

    public Parametrage lib3(String lib3) {
        this.lib3 = lib3;
        return this;
    }

    public void setLib3(String lib3) {
        this.lib3 = lib3;
    }

    public Double getMnt1() {
        return mnt1;
    }

    public Parametrage mnt1(Double mnt1) {
        this.mnt1 = mnt1;
        return this;
    }

    public void setMnt1(Double mnt1) {
        this.mnt1 = mnt1;
    }

    public Double getMnt2() {
        return mnt2;
    }

    public Parametrage mnt2(Double mnt2) {
        this.mnt2 = mnt2;
        return this;
    }

    public void setMnt2(Double mnt2) {
        this.mnt2 = mnt2;
    }

    public Double getMnt3() {
        return mnt3;
    }

    public Parametrage mnt3(Double mnt3) {
        this.mnt3 = mnt3;
        return this;
    }

    public void setMnt3(Double mnt3) {
        this.mnt3 = mnt3;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parametrage parametrage = (Parametrage) o;
        if (parametrage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parametrage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Parametrage{" +
            "id=" + getId() +
            ", codeTypeParam='" + getCodeTypeParam() + "'" +
            ", codeParam='" + getCodeParam() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", lib1='" + getLib1() + "'" +
            ", lib2='" + getLib2() + "'" +
            ", lib3='" + getLib3() + "'" +
            ", mnt1=" + getMnt1() +
            ", mnt2=" + getMnt2() +
            ", mnt3=" + getMnt3() +
            "}";
    }
}
