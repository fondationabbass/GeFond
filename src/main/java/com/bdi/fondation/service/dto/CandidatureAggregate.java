package com.bdi.fondation.service.dto;

import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.domain.Candidature;
import com.bdi.fondation.domain.Document;
import com.bdi.fondation.domain.ExperienceCandidat;
import com.bdi.fondation.domain.Projet;

public class CandidatureAggregate {
	private Candidature candidature;
	private Candidat candidat;
	private ExperienceCandidat experienceCandidat;
	private Document document;
	private Projet projet;


	public Candidature getCandidature() {
		return candidature;
	}
	public void setCandidature(Candidature candidature) {
		this.candidature = candidature;
	}
	public Candidat getCandidat() {
		return candidat;
	}
	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	public ExperienceCandidat getExperienceCandidat() {
        return experienceCandidat;
    }
    public void setExperienceCandidat(ExperienceCandidat experienceCandidat) {
        this.experienceCandidat = experienceCandidat;
    }
    public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public Projet getProjet() {
		return projet;
	}
	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	@Override
	public String toString() {
		return "CandidatureAggregate [candidature=" + candidature + ", candidat=" + candidat + ", experienceCandidat="
				+ experienceCandidat + ", document=" + document + ", projet=" + projet + "]";
	}




}
