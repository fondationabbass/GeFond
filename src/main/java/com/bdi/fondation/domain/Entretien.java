package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Entretien.
 */
@Entity
@Table(name = "entretien")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Entretien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cadre")
    private String cadre;

    @Column(name = "resultat")
    private String resultat;

    @Column(name = "interlocuteur")
    private String interlocuteur;

    @Column(name = "etat")
    private String etat;

    @Column(name = "date_entretien")
    private LocalDate dateEntretien;

    @ManyToOne
    private Candidature candidature;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCadre() {
        return cadre;
    }

    public Entretien cadre(String cadre) {
        this.cadre = cadre;
        return this;
    }

    public void setCadre(String cadre) {
        this.cadre = cadre;
    }

    public String getResultat() {
        return resultat;
    }

    public Entretien resultat(String resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getInterlocuteur() {
        return interlocuteur;
    }

    public Entretien interlocuteur(String interlocuteur) {
        this.interlocuteur = interlocuteur;
        return this;
    }

    public void setInterlocuteur(String interlocuteur) {
        this.interlocuteur = interlocuteur;
    }

    public String getEtat() {
        return etat;
    }

    public Entretien etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public LocalDate getDateEntretien() {
        return dateEntretien;
    }

    public Entretien dateEntretien(LocalDate dateEntretien) {
        this.dateEntretien = dateEntretien;
        return this;
    }

    public void setDateEntretien(LocalDate dateEntretien) {
        this.dateEntretien = dateEntretien;
    }

    public Candidature getCandidature() {
        return candidature;
    }

    public Entretien candidature(Candidature candidature) {
        this.candidature = candidature;
        return this;
    }

    public void setCandidature(Candidature candidature) {
        this.candidature = candidature;
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
        Entretien entretien = (Entretien) o;
        if (entretien.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entretien.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entretien{" +
            "id=" + getId() +
            ", cadre='" + getCadre() + "'" +
            ", resultat='" + getResultat() + "'" +
            ", interlocuteur='" + getInterlocuteur() + "'" +
            ", etat='" + getEtat() + "'" +
            ", dateEntretien='" + getDateEntretien() + "'" +
            "}";
    }
}
