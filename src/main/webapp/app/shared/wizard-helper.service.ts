import { Injectable } from '@angular/core';
import { WizardStep } from './model/wizard-step.model';

@Injectable()
export class WizardHelperService {

    public candidatWorkflow = [
        new WizardStep('candidat-wz', false),
        new WizardStep('candidat-wz-exp', false),
        new WizardStep('candidat-wz-result', false)
    ];
    public agrWorkflow = [
        new WizardStep('candidature-wz', false),
        new WizardStep('candidature-wz-projet', false),
        new WizardStep('candidature-wz-visite', false),
        new WizardStep('candidature-wz-result', false)
    ];
    public projetWorkflow = [
        new WizardStep('candidature-wz', false),
        new WizardStep('candidature-wz-projet', false),
        new WizardStep('candidature-wz-entretien', false),
        new WizardStep('candidature-wz-result', false)
    ];
    public candidatureWorkflow = this.agrWorkflow;
}
