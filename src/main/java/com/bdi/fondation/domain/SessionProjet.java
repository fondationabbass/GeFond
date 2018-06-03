package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

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

    @Column(name = "date_ouvert")
    private LocalDate dateOuvert;

    @Column(name = "date_fermeture")
    private LocalDate dateFermeture;

    @Column(name = "plafond_finance")
    private Integer plafondFinance;

    @Column(name = "nombre_client")
    private Integer nombreClient;

    @Column(name = "plafond_client")
    private Integer plafondClient;

    @Column(name = "date_creat")
    private String dateCreat;

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

    public Integer getPlafondFinance() {
        return plafondFinance;
    }

    public SessionProjet plafondFinance(Integer plafondFinance) {
        this.plafondFinance = plafondFinance;
        return this;
    }

    public void setPlafondFinance(Integer plafondFinance) {
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

    public Integer getPlafondClient() {
        return plafondClient;
    }

    public SessionProjet plafondClient(Integer plafondClient) {
        this.plafondClient = plafondClient;
        return this;
    }

    public void setPlafondClient(Integer plafondClient) {
        this.plafondClient = plafondClient;
    }

    public String getDateCreat() {
        return dateCreat;
    }

    public SessionProjet dateCreat(String dateCreat) {
        this.dateCreat = dateCreat;
        return this;
    }

    public void setDateCreat(String dateCreat) {
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
