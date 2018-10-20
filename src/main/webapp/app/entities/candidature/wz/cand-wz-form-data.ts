import { Candidature } from "../candidature.model";
import { Candidat} from "../../candidat";
import { ExperienceCandidat} from "../../experience-candidat";
import { Document} from "../../document";
import { Projet} from "../../projet";

export class CandWzFormData {
   candidat : Candidat={};
   candidature : Candidature={};
   experienceCandidat : ExperienceCandidat={};
   document : Document={};
   projet: Projet={};
   unblockCand: boolean = false;
   validateCand: boolean = false;

}
