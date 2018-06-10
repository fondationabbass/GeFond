package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ElementFinancement.
 */
@Entity
@Table(name = "element_financement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ElementFinancement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "date_financement")
    private LocalDate dateFinancement;

    @ManyToOne
    private Pret pret;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public ElementFinancement type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMontant() {
        return montant;
    }

    public ElementFinancement montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDate getDateFinancement() {
        return dateFinancement;
    }

    public ElementFinancement dateFinancement(LocalDate dateFinancement) {
        this.dateFinancement = dateFinancement;
        return this;
    }

    public void setDateFinancement(LocalDate dateFinancement) {
        this.dateFinancement = dateFinancement;
    }

    public Pret getPret() {
        return pret;
    }

    public ElementFinancement pret(Pret pret) {
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
        ElementFinancement elementFinancement = (ElementFinancement) o;
        if (elementFinancement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), elementFinancement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ElementFinancement{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", montant=" + getMontant() +
            ", dateFinancement='" + getDateFinancement() + "'" +
            "}";
    }
}
