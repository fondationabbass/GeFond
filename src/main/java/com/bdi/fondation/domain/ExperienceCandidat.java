package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ExperienceCandidat.
 */
@Entity
@Table(name = "experience_candidat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExperienceCandidat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "type_info")
    private String typeInfo;

    @Column(name = "titre")
    private String titre;

    @Column(name = "etab")
    private String etab;

    @Column(name = "adress_etab")
    private String adressEtab;

    @Column(name = "date_deb")
    private LocalDate dateDeb;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @ManyToOne
    private Candidat candidat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeInfo() {
        return typeInfo;
    }

    public ExperienceCandidat typeInfo(String typeInfo) {
        this.typeInfo = typeInfo;
        return this;
    }

    public void setTypeInfo(String typeInfo) {
        this.typeInfo = typeInfo;
    }

    public String getTitre() {
        return titre;
    }

    public ExperienceCandidat titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEtab() {
        return etab;
    }

    public ExperienceCandidat etab(String etab) {
        this.etab = etab;
        return this;
    }

    public void setEtab(String etab) {
        this.etab = etab;
    }

    public String getAdressEtab() {
        return adressEtab;
    }

    public ExperienceCandidat adressEtab(String adressEtab) {
        this.adressEtab = adressEtab;
        return this;
    }

    public void setAdressEtab(String adressEtab) {
        this.adressEtab = adressEtab;
    }

    public LocalDate getDateDeb() {
        return dateDeb;
    }

    public ExperienceCandidat dateDeb(LocalDate dateDeb) {
        this.dateDeb = dateDeb;
        return this;
    }

    public void setDateDeb(LocalDate dateDeb) {
        this.dateDeb = dateDeb;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public ExperienceCandidat dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public ExperienceCandidat candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
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
        ExperienceCandidat experienceCandidat = (ExperienceCandidat) o;
        if (experienceCandidat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experienceCandidat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExperienceCandidat{" +
            "id=" + getId() +
            ", typeInfo='" + getTypeInfo() + "'" +
            ", titre='" + getTitre() + "'" +
            ", etab='" + getEtab() + "'" +
            ", adressEtab='" + getAdressEtab() + "'" +
            ", dateDeb='" + getDateDeb() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}
