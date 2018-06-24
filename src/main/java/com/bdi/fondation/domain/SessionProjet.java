package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SessionProjet.
 */
@Entity
@Table(name = "session_projet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SessionProjet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_ouvert", nullable = false)
    private LocalDate dateOuvert;

    @NotNull
    @Column(name = "date_fermeture", nullable = false)
    private LocalDate dateFermeture;

    @NotNull
    @Column(name = "plafond_finance", nullable = false)
    private Double plafondFinance;

    @NotNull
    @Column(name = "nombre_client", nullable = false)
    private Integer nombreClient;

    @NotNull
    @Column(name = "plafond_client", nullable = false)
    private Double plafondClient;

    @Column(name = "date_creat")
    private LocalDate dateCreat;

    @Column(name = "date_maj")
    private String dateMaj;

    @Column(name = "etat")
    private String etat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOuvert() {
        return dateOuvert;
    }

    public SessionProjet dateOuvert(LocalDate dateOuvert) {
        this.dateOuvert = dateOuvert;
        return this;
    }

    public void setDateOuvert(LocalDate dateOuvert) {
        this.dateOuvert = dateOuvert;
    }

    public LocalDate getDateFermeture() {
        return dateFermeture;
    }

    public SessionProjet dateFermeture(LocalDate dateFermeture) {
        this.dateFermeture = dateFermeture;
        return this;
    }

    public void setDateFermeture(LocalDate dateFermeture) {
        this.dateFermeture = dateFermeture;
    }

    public Double getPlafondFinance() {
        return plafondFinance;
    }

    public SessionProjet plafondFinance(Double plafondFinance) {
        this.plafondFinance = plafondFinance;
        return this;
    }

    public void setPlafondFinance(Double plafondFinance) {
        this.plafondFinance = plafondFinance;
    }

    public Integer getNombreClient() {
        return nombreClient;
    }

    public SessionProjet nombreClient(Integer nombreClient) {
        this.nombreClient = nombreClient;
        return this;
    }

    public void setNombreClient(Integer nombreClient) {
        this.nombreClient = nombreClient;
    }

    public Double getPlafondClient() {
        return plafondClient;
    }

    public SessionProjet plafondClient(Double plafondClient) {
        this.plafondClient = plafondClient;
        return this;
    }

    public void setPlafondClient(Double plafondClient) {
        this.plafondClient = plafondClient;
    }

    public LocalDate getDateCreat() {
        return dateCreat;
    }

    public SessionProjet dateCreat(LocalDate localDate) {
        this.dateCreat = localDate;
        return this;
    }

    public void setDateCreat(LocalDate dateCreat) {
        this.dateCreat = dateCreat;
    }

    public String getDateMaj() {
        return dateMaj;
    }

    public SessionProjet dateMaj(String dateMaj) {
        this.dateMaj = dateMaj;
        return this;
    }

    public void setDateMaj(String dateMaj) {
        this.dateMaj = dateMaj;
    }

    public String getEtat() {
        return etat;
    }

    public SessionProjet etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
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
        SessionProjet sessionProjet = (SessionProjet) o;
        if (sessionProjet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sessionProjet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SessionProjet{" +
            "id=" + getId() +
            ", dateOuvert='" + getDateOuvert() + "'" +
            ", dateFermeture='" + getDateFermeture() + "'" +
            ", plafondFinance=" + getPlafondFinance() +
            ", nombreClient=" + getNombreClient() +
            ", plafondClient=" + getPlafondClient() +
            ", dateCreat='" + getDateCreat() + "'" +
            ", dateMaj='" + getDateMaj() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
