import { Injectable } from '@angular/core';
import { WizardStep } from './model/wizard-step.model';

@Injectable()
export class WizardWorkflowService {

    validateStep(step: string, workflow: WizardStep[]): WizardStep[] {
        var found = false;
        for (var i = 0; i < workflow.length && !found; i++) {
            if (workflow[i].step === step) {
                found = workflow[i].valid = true;
            }
        }
        return workflow;
    }

    resetSteps(workflow: WizardStep[]): WizardStep[] {
        workflow.forEach(element => {
            element.valid = false;
        });
        return workflow;
    }

    getFirstInvalidStep(step: string, workflow: WizardStep[]) : string {
        var found = false;
        var valid = true;
        var redirectToStep = '';
        for (var i = 0; i < workflow.length && !found && valid; i++) {
            let item = workflow[i];
            if (item.step === step) {
                found = true;
                redirectToStep = '';
            }
            else {
                valid = item.valid;
                redirectToStep = item.step
            }
        }
        return redirectToStep;
    }
}
