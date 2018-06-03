package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Echeance.
 */
@Entity
@Table(name = "echeance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Echeance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_tombe")
    private LocalDate dateTombe;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "etat_echeance")
    private String etatEcheance;

    @Column(name = "date_payement")
    private LocalDate datePayement;

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

    public LocalDate getDateTombe() {
        return dateTombe;
    }

    public Echeance dateTombe(LocalDate dateTombe) {
        this.dateTombe = dateTombe;
        return this;
    }

    public void setDateTombe(LocalDate dateTombe) {
        this.dateTombe = dateTombe;
    }

    public Double getMontant() {
        return montant;
    }

    public Echeance montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getEtatEcheance() {
        return etatEcheance;
    }

    public Echeance etatEcheance(String etatEcheance) {
        this.etatEcheance = etatEcheance;
        return this;
    }

    public void setEtatEcheance(String etatEcheance) {
        this.etatEcheance = etatEcheance;
    }

    public LocalDate getDatePayement() {
        return datePayement;
    }

    public Echeance datePayement(LocalDate datePayement) {
        this.datePayement = datePayement;
        return this;
    }

    public void setDatePayement(LocalDate datePayement) {
        this.datePayement = datePayement;
    }

    public LocalDate getDateRetrait() {
        return dateRetrait;
    }

    public Echeance dateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
        return this;
    }

    public void setDateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public Pret getPret() {
        return pret;
    }

    public Echeance pret(Pret pret) {
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
        Echeance echeance = (Echeance) o;
        if (echeance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), echeance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Echeance{" +
            "id=" + getId() +
            ", dateTombe='" + getDateTombe() + "'" +
            ", montant=" + getMontant() +
            ", etatEcheance='" + getEtatEcheance() + "'" +
            ", datePayement='" + getDatePayement() + "'" +
            ", dateRetrait='" + getDateRetrait() + "'" +
            "}";
    }
}
