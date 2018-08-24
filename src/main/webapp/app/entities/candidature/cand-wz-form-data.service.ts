import { Injectable } from '@angular/core';
import { CandWzFormData }       from './cand-wz-form-data';
import { Candidat} from "../candidat";
import { Candidature} from "../candidature";
import { ExperienceCandidat} from "../experience-candidat";
import { Document} from "../document";
import { Projet} from "../projet";

@Injectable()
export class CandWzFormDataService {
  private formData: CandWzFormData = new CandWzFormData();
  private isCandidatFormValid: boolean = false;
  private isCandidatureFormValid: boolean = false;
  private isExperienceCandidatFormValid: boolean = false;
  private isDocumentFormValid: boolean = false;
  private isProjetFormValid: boolean = false;
  constructor() { }

getCandidat(): Candidat {
    return this.formData.candidat;
}
getCandidature(): ExperienceCandidat {
  return this.formData.experiencecandidat;
}
getExperianceCandidat(): Candidature {
  return this.formData.candidature;
}
getDocument(): Document {
  return this.formData.document;
}
getProjet(): Projet {
  return this.formData.projet;
}
getData():CandWzFormData {
    return this.formData;
}

setCandidat(data: Candidat) {
  this.isCandidatFormValid = true;
  this.formData.candidat = data;
}

setCandidature(data: Candidature) {
  this.isCandidatureFormValid= true;
  this.formData.candidature = data;
}

setExperienceCandidat(data: ExperienceCandidat) {
  this.isExperienceCandidatFormValid= true;
  this.formData.experiencecandidat = data;
}

setDocument(data: Document) {
  this.isDocumentFormValid= true;
  this.formData.document = data;
}

setProjet(data: Projet) {
  this.isProjetFormValid= true;
  this.formData.projet = data;
}


resetFormData(): CandWzFormData {
  this.formData=new CandWzFormData();
  this.isCandidatFormValid = this.isCandidatureFormValid = this.isExperienceCandidatFormValid = this.isDocumentFormValid = this.isProjetFormValid= false;
  return this.formData;
}

isFormValid() {
  // Return true if all forms had been validated successfully; otherwise, return false
  return this.isCandidatFormValid &&
          this.isCandidatureFormValid &&
          this.isDocumentFormValid &&
          this.isExperienceCandidatFormValid &&
          this.isProjetFormValid;
        
}

}
