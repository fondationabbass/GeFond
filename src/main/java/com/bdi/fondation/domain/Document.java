package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_enreg")
    private LocalDate dateEnreg;

    @Column(name = "lib")
    private String lib;

    @Column(name = "type_document")
    private String typeDocument;

    @Column(name = "module")
    private String module;

    @Column(name = "etat")
    private String etat;

    @Column(name = "fichier")
    private String fichier;

    @Column(name = "tail")
    private String tail;

    @ManyToOne
    private Candidature candidature;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEnreg() {
        return dateEnreg;
    }

    public Document dateEnreg(LocalDate dateEnreg) {
        this.dateEnreg = dateEnreg;
        return this;
    }

    public void setDateEnreg(LocalDate dateEnreg) {
        this.dateEnreg = dateEnreg;
    }

    public String getLib() {
        return lib;
    }

    public Document lib(String lib) {
        this.lib = lib;
        return this;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public Document typeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
        return this;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getModule() {
        return module;
    }

    public Document module(String module) {
        this.module = module;
        return this;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getEtat() {
        return etat;
    }

    public Document etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getFichier() {
        return fichier;
    }

    public Document fichier(String fichier) {
        this.fichier = fichier;
        return this;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getTail() {
        return tail;
    }

    public Document tail(String tail) {
        this.tail = tail;
        return this;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public Candidature getCandidature() {
        return candidature;
    }

    public Document candidature(Candidature candidature) {
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
        Document document = (Document) o;
        if (document.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), document.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", dateEnreg='" + getDateEnreg() + "'" +
            ", lib='" + getLib() + "'" +
            ", typeDocument='" + getTypeDocument() + "'" +
            ", module='" + getModule() + "'" +
            ", etat='" + getEtat() + "'" +
            ", fichier='" + getFichier() + "'" +
            ", tail='" + getTail() + "'" +
            "}";
    }
}
