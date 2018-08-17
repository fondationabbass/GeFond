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
 * A Operation.
 */
@Entity
@Table(name = "operation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_operation")
    private LocalDate dateOperation;

    @NotNull
    @Column(name = "type_operation", nullable = false)
    private String typeOperation;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "etat")
    private String etat;

    @Column(name = "moyen_paiement")
    private String moyenPaiement;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Compte compteOrigin;

    @ManyToOne
    private Compte compteDestinataire;

    @ManyToOne
    private Pret pret;

    @ManyToOne
    private Caisse caisse;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "operation_echeance",
               joinColumns = @JoinColumn(name="operations_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="echeances_id", referencedColumnName="id"))
    private Set<Echeance> echeances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOperation() {
        return dateOperation;
    }

    public Operation dateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
        return this;
    }

    public void setDateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public Operation typeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
        return this;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Double getMontant() {
        return montant;
    }

    public Operation montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getEtat() {
        return etat;
    }

    public Operation etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getMoyenPaiement() {
        return moyenPaiement;
    }

    public Operation moyenPaiement(String moyenPaiement) {
        this.moyenPaiement = moyenPaiement;
        return this;
    }

    public void setMoyenPaiement(String moyenPaiement) {
        this.moyenPaiement = moyenPaiement;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Operation commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getDescription() {
        return description;
    }

    public Operation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Compte getCompteOrigin() {
        return compteOrigin;
    }

    public Operation compteOrigin(Compte compte) {
        this.compteOrigin = compte;
        return this;
    }

    public void setCompteOrigin(Compte compte) {
        this.compteOrigin = compte;
    }

    public Compte getCompteDestinataire() {
        return compteDestinataire;
    }

    public Operation compteDestinataire(Compte compte) {
        this.compteDestinataire = compte;
        return this;
    }

    public void setCompteDestinataire(Compte compte) {
        this.compteDestinataire = compte;
    }

    public Pret getPret() {
        return pret;
    }

    public Operation pret(Pret pret) {
        this.pret = pret;
        return this;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public Caisse getCaisse() {
        return caisse;
    }

    public Operation caisse(Caisse caisse) {
        this.caisse = caisse;
        return this;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public Set<Echeance> getEcheances() {
        return echeances;
    }

    public Operation echeances(Set<Echeance> echeances) {
        this.echeances = echeances;
        return this;
    }

    public Operation addEcheance(Echeance echeance) {
        this.echeances.add(echeance);
        echeance.getOperations().add(this);
        return this;
    }

    public Operation removeEcheance(Echeance echeance) {
        this.echeances.remove(echeance);
        echeance.getOperations().remove(this);
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
        Operation operation = (Operation) o;
        if (operation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operation{" +
            "id=" + getId() +
            ", dateOperation='" + getDateOperation() + "'" +
            ", typeOperation='" + getTypeOperation() + "'" +
            ", montant=" + getMontant() +
            ", etat='" + getEtat() + "'" +
            ", moyenPaiement='" + getMoyenPaiement() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
