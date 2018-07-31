package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Mouvement.
 */
@Entity
@Table(name = "mouvement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mouvement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_mvt")
    private LocalDate dateMvt;

    @NotNull
    @Column(name = "lib", nullable = false)
    private String lib;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @NotNull
    @Column(name = "sens", nullable = false)
    private String sens;

    @Column(name = "etat")
    private String etat;

    @ManyToOne
    private Compte compte;

    @ManyToOne
    private Pret pret;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "mouvement_echeance",
               joinColumns = @JoinColumn(name="mouvements_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="echeances_id", referencedColumnName="id"))
    private Set<Echeance> echeances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateMvt() {
        return dateMvt;
    }

    public Mouvement dateMvt(LocalDate dateMvt) {
        this.dateMvt = dateMvt;
        return this;
    }

    public void setDateMvt(LocalDate dateMvt) {
        this.dateMvt = dateMvt;
    }

    public String getLib() {
        return lib;
    }

    public Mouvement lib(String lib) {
        this.lib = lib;
        return this;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }

    public Double getMontant() {
        return montant;
    }

    public Mouvement montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getSens() {
        return sens;
    }

    public Mouvement sens(String sens) {
        this.sens = sens;
        return this;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public String getEtat() {
        return etat;
    }

    public Mouvement etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Compte getCompte() {
        return compte;
    }

    public Mouvement compte(Compte compte) {
        this.compte = compte;
        return this;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Pret getPret() {
        return pret;
    }

    public Mouvement pret(Pret pret) {
        this.pret = pret;
        return this;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public Set<Echeance> getEcheances() {
        return echeances;
    }

    public Mouvement echeances(Set<Echeance> echeances) {
        this.echeances = echeances;
        return this;
    }

    public Mouvement addEcheance(Echeance echeance) {
        this.echeances.add(echeance);
        echeance.getMouvements().add(this);
        return this;
    }

    public Mouvement removeEcheance(Echeance echeance) {
        this.echeances.remove(echeance);
        echeance.getMouvements().remove(this);
        return this;
    }

    public void setEcheances(Set<Echeance> echeances) {
        this.echeances = echeances;
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
        Mouvement mouvement = (Mouvement) o;
        if (mouvement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mouvement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mouvement{" +
            "id=" + getId() +
            ", dateMvt='" + getDateMvt() + "'" +
            ", lib='" + getLib() + "'" +
            ", montant=" + getMontant() +
            ", sens='" + getSens() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
