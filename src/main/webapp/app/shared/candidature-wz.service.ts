
import { Injectable } from "@angular/core";
import { CandidatureAggregate } from "../entities/candidature/candidature-wz.model";
import { Candidature } from "../entities/candidature";
import { Document } from "../entities/document";
import { Projet } from "../entities/projet";
import { Entretien } from "../entities/entretien";
import { Visite } from "../entities/visite";

@Injectable()
export class CandidatureWzService {
  
  aggregate: CandidatureAggregate = new CandidatureAggregate();

  constructor() {
    this.reset();
  }

  setCandidature(candiature: Candidature) {
    this.aggregate.candidature = candiature;
  }

  setProjet(projet: Projet) {
    this.aggregate.projet = projet;
  }

  setDocuments(documents: Document[]) {
    this.aggregate.documents = documents;
  }

  setEntretiens(entretiens: Entretien[]) {
    this.aggregate.entretiens = entretiens;
  }

  setVisites(visites: Visite[]) {
    this.aggregate.visites = visites;
  }

  reset() {
    this.aggregate = new CandidatureAggregate();
    this.aggregate.candidature = {};
    this.aggregate.candidature.candidat = {};
    this.aggregate.candidature.status = "Encours";
    this.aggregate.projet = {};
    this.aggregate.documents = [];
    this.aggregate.entretiens = [];
    this.aggregate.visites = [];
  }

}
