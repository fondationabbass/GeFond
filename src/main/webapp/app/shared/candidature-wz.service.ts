
import { Injectable } from '@angular/core';
import { CandidatureAggregate } from '../entities/candidature/candidature-wz.model';
import { Candidature } from '../entities/candidature';
import { Document } from '../entities/document';
import { Projet } from '../entities/projet';
import { Entretien } from '../entities/entretien';
import { Visite } from '../entities/visite';
import { ExperienceCandidat } from '../entities/experience-candidat';

@Injectable()
export class CandidatureWzService {
  aggregate: CandidatureAggregate = new CandidatureAggregate();

  constructor() {
    this.reset();
  }
  setExps(exp: ExperienceCandidat[]) {
    this.aggregate.exps = exp;
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

  isProjet(): boolean {
    return this.aggregate.candidature.type === 'Projet';
  }

  reset() {
    this.aggregate = new CandidatureAggregate();
    this.aggregate.candidature = {};
    this.aggregate.candidature.candidat = {};
    this.aggregate.candidature.status = 'Encours';
    this.aggregate.projet = {};
    this.aggregate.documents = [];
    this.aggregate.entretiens = [];
    this.aggregate.visites = [];
  }

}
