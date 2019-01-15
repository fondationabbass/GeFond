import { Injectable } from '@angular/core';
import { PRET_WZ_STEPS } from './pret-wz-workflow.model';

@Injectable()
export class PretWzWorkflowService {
    private workflow = [
        { step: PRET_WZ_STEPS.pret, valid: false },
        { step: PRET_WZ_STEPS.garantie, valid: false },
        { step: PRET_WZ_STEPS.elementFinancement, valid: false },
        { step: PRET_WZ_STEPS.echeance, valid: false },
        { step: PRET_WZ_STEPS.result, valid: false }
    ];
    validateStep(step: string) {
        let found = false;
        for (let i = 0; i < this.workflow.length && !found; i++) {
            if (this.workflow[i].step === step) {
                found = this.workflow[i].valid = true;
            }
        }
    }

    resetSteps() {
        this.workflow.forEach( (element) => {
            element.valid = false;
        });
    }

    getFirstInvalidStep(step: string): string {
        let found = false;
        let valid = true;
        let redirectToStep = '';
        for (let i = 0; i < this.workflow.length && !found && valid; i++) {
            const item = this.workflow[i];
            if (item.step === step) {
                found = true;
                redirectToStep = '';
            } else {
                valid = item.valid;
                redirectToStep = item.step;
            }
        }
        return redirectToStep;
    }
}
