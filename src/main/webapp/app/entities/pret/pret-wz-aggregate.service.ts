import { Injectable } from '@angular/core';

import { PretAggregate } from './pret-wz-aggregate.model';
import { PretWzWorkflowService } from './pret-wz-workflow.service';
import { PRET_WZ_STEPS } from './pret-wz-workflow.model';
import { Pret } from './pret.model';
import { Echeance } from '../echeance';
import { ElementFinancement } from '../element-financement';
import { Garantie } from '../garantie';

@Injectable()
export class PretAggregateService {

    private aggregate: PretAggregate = new PretAggregate();
    private isPretFormValid: boolean = false;
    private isEcheanceFormValid: boolean = false;
    private isElementFinancementFormValid: boolean = false;
    private isGarantieFormValid: boolean = false;


    constructor(private workflowService: PretWzWorkflowService) { 
    }

    getPret(): Pret {
        return this.aggregate.pret;
    }
    getData():PretAggregate {
        return this.aggregate;
    }
    setPret(data: Pret) {
        this.isPretFormValid = true;
        this.aggregate.pret = data;
        this.workflowService.validateStep(PRET_WZ_STEPS.pret);
    }

    getElementFinancements() : ElementFinancement[] {
        return this.aggregate.elementFinancements;
    }
    
    setElementFinancements(data: ElementFinancement[]) {
        this.isElementFinancementFormValid = true;
        this.aggregate.elementFinancements = data;
        this.workflowService.validateStep(PRET_WZ_STEPS.elementFinancement);
    }
    getGaranties() : Garantie[] {
        return this.aggregate.garanties;
    }
    
    setGaranties(data: Garantie[]) {
        this.isGarantieFormValid = true;
        this.aggregate.garanties = data;
        this.workflowService.validateStep(PRET_WZ_STEPS.garantie);
    }
    getEcheances() : Echeance[] {
        return this.aggregate.echeances;
    }
    
    setEcheances(data: Echeance[]) {
        this.isEcheanceFormValid = true;
        this.aggregate.echeances = data;
        this.workflowService.validateStep(PRET_WZ_STEPS.echeance);
    }

    getFormData(): PretAggregate {
        // Return the entire Form Data
        return this.aggregate;
    }

    resetFormData(): PretAggregate {
        this.workflowService.resetSteps();
        this.aggregate=new PretAggregate();
        this.isPretFormValid = this.isEcheanceFormValid = this.isElementFinancementFormValid = this.isGarantieFormValid = false;
        return this.aggregate;
    }

    isFormValid() {
        // Return true if all forms had been validated successfully; otherwise, return false
        return this.isPretFormValid &&
                this.isEcheanceFormValid && 
                this.isElementFinancementFormValid &&
                this.isGarantieFormValid;
    }
}