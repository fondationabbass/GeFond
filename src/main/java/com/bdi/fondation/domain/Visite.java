package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Visite.
 */
@Entity
@Table(name = "visite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Visite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "lieu_visite")
    private String lieuVisite;

    @Column(name = "date_visite")
    private LocalDate dateVisite;

    @Column(name = "pers_rencontre")
    private Integer persRencontre;

    @Column(name = "cadre_visite")
    private String cadreVisite;

    @Column(name = "etat_lieu")
    private String etatLieu;

    @Column(name = "visiteur")
    private String visiteur;

    @Column(name = "etat")
    private String etat;

    @Column(name = "recomendation")
    private String recomendation;

    @Column(name = "rapport")
    private String rapport;

    @ManyToOne
    private Candidature candidature;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLieuVisite() {
        return lieuVisite;
    }

    public Visite lieuVisite(String lieuVisite) {
        this.lieuVisite = lieuVisite;
        return this;
    }

    public void setLieuVisite(String lieuVisite) {
        this.lieuVisite = lieuVisite;
    }

    public LocalDate getDateVisite() {
        return dateVisite;
    }

    public Visite dateVisite(LocalDate dateVisite) {
        this.dateVisite = dateVisite;
        return this;
    }

    public void setDateVisite(LocalDate dateVisite) {
        this.dateVisite = dateVisite;
    }

    public Integer getPersRencontre() {
        return persRencontre;
    }

    public Visite persRencontre(Integer persRencontre) {
        this.persRencontre = persRencontre;
        return this;
    }

    public void setPersRencontre(Integer persRencontre) {
        this.persRencontre = persRencontre;
    }

    public String getCadreVisite() {
        return cadreVisite;
    }

    public Visite cadreVisite(String cadreVisite) {
        this.cadreVisite = cadreVisite;
        return this;
    }

    public void setCadreVisite(String cadreVisite) {
        this.cadreVisite = cadreVisite;
    }

    public String getEtatLieu() {
        return etatLieu;
    }

    public Visite etatLieu(String etatLieu) {
        this.etatLieu = etatLieu;
        return this;
    }

    public void setEtatLieu(String etatLieu) {
        this.etatLieu = etatLieu;
    }

    public String getVisiteur() {
        return visiteur;
    }

    public Visite visiteur(String visiteur) {
        this.visiteur = visiteur;
        return this;
    }

    public void setVisiteur(String visiteur) {
        this.visiteur = visiteur;
    }

    public String getEtat() {
        return etat;
    }

    public Visite etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getRecomendation() {
        return recomendation;
    }

    public Visite recomendation(String recomendation) {
        this.recomendation = recomendation;
        return this;
    }

    public void setRecomendation(String recomendation) {
        this.recomendation = recomendation;
    }

    public String getRapport() {
        return rapport;
    }

    public Visite rapport(String rapport) {
        this.rapport = rapport;
        return this;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }

    public Candidature getCandidature() {
        return candidature;
    }

    public Visite candidature(Candidature candidature) {
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
        Visite visite = (Visite) o;
        if (visite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Visite{" +
            "id=" + getId() +
            ", lieuVisite='" + getLieuVisite() + "'" +
            ", dateVisite='" + getDateVisite() + "'" +
            ", persRencontre=" + getPersRencontre() +
            ", cadreVisite='" + getCadreVisite() + "'" +
            ", etatLieu='" + getEtatLieu() + "'" +
            ", visiteur='" + getVisiteur() + "'" +
            ", etat='" + getEtat() + "'" +
            ", recomendation='" + getRecomendation() + "'" +
            ", rapport='" + getRapport() + "'" +
            "}";
    }
}
