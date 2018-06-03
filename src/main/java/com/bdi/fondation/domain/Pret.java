package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Pret.
 */
@Entity
@Table(name = "pret")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pret implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "typ_pret")
    private String typPret;

    @Column(name = "mont_aaccord")
    private Double montAaccord;

    @Column(name = "mont_debloq")
    private Double montDebloq;

    @Column(name = "nbr_echeance")
    private Integer nbrEcheance;

    @Column(name = "periodicite")
    private String periodicite;

    @Column(name = "date_mise_place")
    private LocalDate dateMisePlace;

    @Column(name = "date_premiere_echeance")
    private LocalDate datePremiereEcheance;

    @Column(name = "date_derniere_echeance")
    private LocalDate dateDerniereEcheance;

    @Column(name = "date_dernier_debloq")
    private LocalDate dateDernierDebloq;

    @Column(name = "etat")
    private String etat;

    @Column(name = "encours")
    private Double encours;

    @Column(name = "user_initial")
    private String userInitial;

    @Column(name = "user_decideur")
    private String userDecideur;

    @Column(name = "user_debloq")
    private String userDebloq;

    @ManyToOne
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypPret() {
        return typPret;
    }

    public Pret typPret(String typPret) {
        this.typPret = typPret;
        return this;
    }

    public void setTypPret(String typPret) {
        this.typPret = typPret;
    }

    public Double getMontAaccord() {
        return montAaccord;
    }

    public Pret montAaccord(Double montAaccord) {
        this.montAaccord = montAaccord;
        return this;
    }

    public void setMontAaccord(Double montAaccord) {
        this.montAaccord = montAaccord;
    }

    public Double getMontDebloq() {
        return montDebloq;
    }

    public Pret montDebloq(Double montDebloq) {
        this.montDebloq = montDebloq;
        return this;
    }

    public void setMontDebloq(Double montDebloq) {
        this.montDebloq = montDebloq;
    }

    public Integer getNbrEcheance() {
        return nbrEcheance;
    }

    public Pret nbrEcheance(Integer nbrEcheance) {
        this.nbrEcheance = nbrEcheance;
        return this;
    }

    public void setNbrEcheance(Integer nbrEcheance) {
        this.nbrEcheance = nbrEcheance;
    }

    public String getPeriodicite() {
        return periodicite;
    }

    public Pret periodicite(String periodicite) {
        this.periodicite = periodicite;
        return this;
    }

    public void setPeriodicite(String periodicite) {
        this.periodicite = periodicite;
    }

    public LocalDate getDateMisePlace() {
        return dateMisePlace;
    }

    public Pret dateMisePlace(LocalDate dateMisePlace) {
        this.dateMisePlace = dateMisePlace;
        return this;
    }

    public void setDateMisePlace(LocalDate dateMisePlace) {
        this.dateMisePlace = dateMisePlace;
    }

    public LocalDate getDatePremiereEcheance() {
        return datePremiereEcheance;
    }

    public Pret datePremiereEcheance(LocalDate datePremiereEcheance) {
        this.datePremiereEcheance = datePremiereEcheance;
        return this;
    }

    public void setDatePremiereEcheance(LocalDate datePremiereEcheance) {
        this.datePremiereEcheance = datePremiereEcheance;
    }

    public LocalDate getDateDerniereEcheance() {
        return dateDerniereEcheance;
    }

    public Pret dateDerniereEcheance(LocalDate dateDerniereEcheance) {
        this.dateDerniereEcheance = dateDerniereEcheance;
        return this;
    }

    public void setDateDerniereEcheance(LocalDate dateDerniereEcheance) {
        this.dateDerniereEcheance = dateDerniereEcheance;
    }

    public LocalDate getDateDernierDebloq() {
        return dateDernierDebloq;
    }

    public Pret dateDernierDebloq(LocalDate dateDernierDebloq) {
        this.dateDernierDebloq = dateDernierDebloq;
        return this;
    }

    public void setDateDernierDebloq(LocalDate dateDernierDebloq) {
        this.dateDernierDebloq = dateDernierDebloq;
    }

    public String getEtat() {
        return etat;
    }

    public Pret etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Double getEncours() {
        return encours;
    }

    public Pret encours(Double encours) {
        this.encours = encours;
        return this;
    }

    public void setEncours(Double encours) {
        this.encours = encours;
    }

    public String getUserInitial() {
        return userInitial;
    }

    public Pret userInitial(String userInitial) {
        this.userInitial = userInitial;
        return this;
    }

    public void setUserInitial(String userInitial) {
        this.userInitial = userInitial;
    }

    public String getUserDecideur() {
        return userDecideur;
    }

    public Pret userDecideur(String userDecideur) {
        this.userDecideur = userDecideur;
        return this;
    }

    public void setUserDecideur(String userDecideur) {
        this.userDecideur = userDecideur;
    }

    public String getUserDebloq() {
        return userDebloq;
    }

    public Pret userDebloq(String userDebloq) {
        this.userDebloq = userDebloq;
        return this;
    }

    public void setUserDebloq(String userDebloq) {
        this.userDebloq = userDebloq;
    }

    public Client getClient() {
        return client;
    }

    public Pret client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
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
        Pret pret = (Pret) o;
        if (pret.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pret.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pret{" +
            "id=" + getId() +
            ", typPret='" + getTypPret() + "'" +
            ", montAaccord=" + getMontAaccord() +
            ", montDebloq=" + getMontDebloq() +
            ", nbrEcheance=" + getNbrEcheance() +
            ", periodicite='" + getPeriodicite() + "'" +
            ", dateMisePlace='" + getDateMisePlace() + "'" +
            ", datePremiereEcheance='" + getDatePremiereEcheance() + "'" +
            ", dateDerniereEcheance='" + getDateDerniereEcheance() + "'" +
            ", dateDernierDebloq='" + getDateDernierDebloq() + "'" +
            ", etat='" + getEtat() + "'" +
            ", encours=" + getEncours() +
            ", userInitial='" + getUserInitial() + "'" +
            ", userDecideur='" + getUserDecideur() + "'" +
            ", userDebloq='" + getUserDebloq() + "'" +
            "}";
    }
}
