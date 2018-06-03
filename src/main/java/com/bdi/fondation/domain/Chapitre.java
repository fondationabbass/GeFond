package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Chapitre.
 */
@Entity
@Table(name = "chapitre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chapitre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "lib_chapitre")
    private String libChapitre;

    @Column(name = "categorie_compte")
    private String categorieCompte;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibChapitre() {
        return libChapitre;
    }

    public Chapitre libChapitre(String libChapitre) {
        this.libChapitre = libChapitre;
        return this;
    }

    public void setLibChapitre(String libChapitre) {
        this.libChapitre = libChapitre;
    }

    public String getCategorieCompte() {
        return categorieCompte;
    }

    public Chapitre categorieCompte(String categorieCompte) {
        this.categorieCompte = categorieCompte;
        return this;
    }

    public void setCategorieCompte(String categorieCompte) {
        this.categorieCompte = categorieCompte;
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
        Chapitre chapitre = (Chapitre) o;
        if (chapitre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chapitre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chapitre{" +
            "id=" + getId() +
            ", libChapitre='" + getLibChapitre() + "'" +
            ", categorieCompte='" + getCategorieCompte() + "'" +
            "}";
    }
}
