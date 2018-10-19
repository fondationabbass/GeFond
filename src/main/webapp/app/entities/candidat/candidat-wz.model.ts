import { ExperienceCandidat } from '../experience-candidat';
import { Candidat } from './candidat.model';

export class CandidatAggregate {
    constructor(
        public candidat: Candidat,
        public exps: ExperienceCandidat[] = []
    ) {
    }
}
