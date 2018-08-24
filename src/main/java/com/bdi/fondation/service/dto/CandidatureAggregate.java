package com.bdi.fondation.service.dto;

import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.domain.Candidature;
import com.bdi.fondation.domain.Document;
import com.bdi.fondation.domain.ExperienceCandidat;
import com.bdi.fondation.domain.Projet;

public class CandidatureAggregate {
	private Candidature candidature;
	private Candidat candidat;
	private ExperienceCandidat experianceCandidat;
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
	public ExperienceCandidat getExperianceCandidat() {
		return experianceCandidat;
	}
	public void setExperianceCandidat(ExperienceCandidat experianceCandidat) {
		this.experianceCandidat = experianceCandidat;
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
		return "CandidatureAggregate [candidature=" + candidature + ", candidat=" + candidat + ", experianceCandidat="
				+ experianceCandidat + ", document=" + document + ", projet=" + projet + "]";
	}


	

}
