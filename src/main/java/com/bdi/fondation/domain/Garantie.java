package com.bdi.fondation.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Garantie.
 */
@Entity
@Table(name = "garantie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Garantie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "type_gar", nullable = false)
    private String typeGar;

    @Column(name = "date_depot")
    private LocalDate dateDepot;

    @NotNull
    @Column(name = "num_document", nullable = false)
    private String numDocument;

    @NotNull
    @Column(name = "etat", nullable = false)
    private String etat;

    @Column(name = "date_retrait")
    private LocalDate dateRetrait;

    @ManyToOne
    private Pret pret;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeGar() {
        return typeGar;
    }

    public Garantie typeGar(String typeGar) {
        this.typeGar = typeGar;
        return this;
    }

    public void setTypeGar(String typeGar) {
        this.typeGar = typeGar;
    }

    public LocalDate getDateDepot() {
        return dateDepot;
    }

    public Garantie dateDepot(LocalDate dateDepot) {
        this.dateDepot = dateDepot;
        return this;
    }

    public void setDateDepot(LocalDate dateDepot) {
        this.dateDepot = dateDepot;
    }

    public String getNumDocument() {
        return numDocument;
    }

    public Garantie numDocument(String numDocument) {
        this.numDocument = numDocument;
        return this;
    }

    public void setNumDocument(String numDocument) {
        this.numDocument = numDocument;
    }

    public String getEtat() {
        return etat;
    }

    public Garantie etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public LocalDate getDateRetrait() {
        return dateRetrait;
    }

    public Garantie dateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
        return this;
    }

    public void setDateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public Pret getPret() {
        return pret;
    }

    public Garantie pret(Pret pret) {
        this.pret = pret;
        return this;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
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
        Garantie garantie = (Garantie) o;
        if (garantie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), garantie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Garantie{" +
            "id=" + getId() +
            ", typeGar='" + getTypeGar() + "'" +
            ", dateDepot='" + getDateDepot() + "'" +
            ", numDocument='" + getNumDocument() + "'" +
            ", etat='" + getEtat() + "'" +
            ", dateRetrait='" + getDateRetrait() + "'" +
            "}";
    }
}
