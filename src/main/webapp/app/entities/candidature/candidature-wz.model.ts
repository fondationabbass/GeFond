import { Candidature } from "./candidature.model";
import { Candidat} from "../candidat";
import { Document} from "../document";
import { Projet} from "../projet";
import { Entretien } from '../entretien';
import { Visite } from '../visite';
import { ExperienceCandidat } from "../experience-candidat";

export class CandidatureAggregate {
  constructor(
    public candidature?: Candidature,
    public projet?: Projet,
    public documents: Document[] = [],
    public entretiens: Entretien[] = [],
    public visites: Visite[] = [],
    public exps: ExperienceCandidat[] = []
  ){}
}
