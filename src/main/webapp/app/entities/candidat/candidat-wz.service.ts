import { Injectable } from '@angular/core';
import { Candidat } from './candidat.model';
import { ExperienceCandidat } from '../experience-candidat';
import { CandidatAggregate } from './candidat-wz.model';

@Injectable()
export class CandidatWzService {
    
    aggregate: CandidatAggregate = new CandidatAggregate(new Candidat(), []);
    
    constructor() {
        this.setCandidat(new Candidat());
        this.setExpCandidat([]);
    }

    setCandidat(candidat: Candidat) {
        this.aggregate.candidat = candidat;
    }

    setExpCandidat(exp: ExperienceCandidat[]) {
        this.aggregate.exps = exp;
    }

    reset() {
        this.aggregate = new CandidatAggregate(new Candidat(), []);
    }
}
