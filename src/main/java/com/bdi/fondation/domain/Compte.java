package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Compte.
 */
@Entity
@Table(name = "compte")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "intitule_compte")
    private String intituleCompte;

    @Column(name = "date_ouverture")
    private LocalDate dateOuverture;

    @Column(name = "solde")
    private Double solde;

    @Column(name = "date_dernier_credit")
    private String dateDernierCredit;

    @Column(name = "date_dernier_debit")
    private LocalDate dateDernierDebit;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Chapitre chapitre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntituleCompte() {
        return intituleCompte;
    }

    public Compte intituleCompte(String intituleCompte) {
        this.intituleCompte = intituleCompte;
        return this;
    }

    public void setIntituleCompte(String intituleCompte) {
        this.intituleCompte = intituleCompte;
    }

    public LocalDate getDateOuverture() {
        return dateOuverture;
    }

    public Compte dateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
        return this;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public Double getSolde() {
        return solde;
    }

    public Compte solde(Double solde) {
        this.solde = solde;
        return this;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public String getDateDernierCredit() {
        return dateDernierCredit;
    }

    public Compte dateDernierCredit(String dateDernierCredit) {
        this.dateDernierCredit = dateDernierCredit;
        return this;
    }

    public void setDateDernierCredit(String dateDernierCredit) {
        this.dateDernierCredit = dateDernierCredit;
    }

    public LocalDate getDateDernierDebit() {
        return dateDernierDebit;
    }

    public Compte dateDernierDebit(LocalDate dateDernierDebit) {
        this.dateDernierDebit = dateDernierDebit;
        return this;
    }

    public void setDateDernierDebit(LocalDate dateDernierDebit) {
        this.dateDernierDebit = dateDernierDebit;
    }

    public Client getClient() {
        return client;
    }

    public Compte client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Chapitre getChapitre() {
        return chapitre;
    }

    public Compte chapitre(Chapitre chapitre) {
        this.chapitre = chapitre;
        return this;
    }

    public void setChapitre(Chapitre chapitre) {
        this.chapitre = chapitre;
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
        Compte compte = (Compte) o;
        if (compte.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Compte{" +
            "id=" + getId() +
            ", intituleCompte='" + getIntituleCompte() + "'" +
            ", dateOuverture='" + getDateOuverture() + "'" +
            ", solde=" + getSolde() +
            ", dateDernierCredit='" + getDateDernierCredit() + "'" +
            ", dateDernierDebit='" + getDateDernierDebit() + "'" +
            "}";
    }
}
