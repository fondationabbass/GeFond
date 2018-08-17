package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Caisse.
 */
@Entity
@Table(name = "caisse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Caisse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "intitule_caisse")
    private String intituleCaisse;

    @Column(name = "date_ouverture")
    private LocalDate dateOuverture;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntituleCaisse() {
        return intituleCaisse;
    }

    public Caisse intituleCaisse(String intituleCaisse) {
        this.intituleCaisse = intituleCaisse;
        return this;
    }

    public void setIntituleCaisse(String intituleCaisse) {
        this.intituleCaisse = intituleCaisse;
    }

    public LocalDate getDateOuverture() {
        return dateOuverture;
    }

    public Caisse dateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
        return this;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public User getUser() {
        return user;
    }

    public Caisse user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Caisse caisse = (Caisse) o;
        if (caisse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caisse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Caisse{" +
            "id=" + getId() +
            ", intituleCaisse='" + getIntituleCaisse() + "'" +
            ", dateOuverture='" + getDateOuverture() + "'" +
            "}";
    }
}
