import { Injectable } from '@angular/core';
import { WizardStep } from './model/wizard-step.model';

@Injectable()
export class WizardHelperService {

    public candidatWorkflow = [
        new WizardStep('candidat-wz', false),
        new WizardStep('candidat-wz-exp', false),
        new WizardStep('candidat-wz-result', false)
    ];
}
