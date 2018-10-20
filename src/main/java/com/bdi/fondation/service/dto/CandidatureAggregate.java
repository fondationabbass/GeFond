package com.bdi.fondation.service.dto;

import java.util.Arrays;

import com.bdi.fondation.domain.Candidature;
import com.bdi.fondation.domain.Document;
import com.bdi.fondation.domain.Entretien;
import com.bdi.fondation.domain.Projet;
import com.bdi.fondation.domain.Visite;

public class CandidatureAggregate {
	private Candidature candidature;
	private Projet projet;
	private Document[] documents;
	private Entretien[] entretiens;
	private Visite[] visites;
    public Candidature getCandidature() {
        return candidature;
    }
    public void setCandidature(Candidature candidature) {
        this.candidature = candidature;
    }
    public Projet getProjet() {
        return projet;
    }
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    public Document[] getDocuments() {
        return documents;
    }
    public void setDocuments(Document[] documents) {
        this.documents = documents;
    }
    public Entretien[] getEntretiens() {
        return entretiens;
    }
    public void setEntretiens(Entretien[] entretiens) {
        this.entretiens = entretiens;
    }
    public Visite[] getVisites() {
        return visites;
    }
    public void setVisites(Visite[] visites) {
        this.visites = visites;
    }
    @Override
    public String toString() {
        return "CandidatureAggregate [candidature=" + candidature + ", projet=" + projet + ", documents="
                + Arrays.toString(documents) + ", entretiens=" + Arrays.toString(entretiens) + ", visites="
                + Arrays.toString(visites) + "]";
    }
}
