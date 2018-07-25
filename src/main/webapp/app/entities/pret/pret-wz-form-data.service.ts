import { Injectable }                        from '@angular/core';

import { PretWzFormData }       from './pret-wz-form-data.model';
import { PretWzWorkflowService }                   from './pret-wz-workflow.service';
import { PRET_WZ_STEPS }                             from './pret-wz-workflow.model';
import { Pret } from './pret.model';
import { Echeance } from '../echeance';
import { ElementFinancement } from '../element-financement';
import { Garantie } from '../garantie';

@Injectable()
export class PretWzFormDataService {

    private formData: PretWzFormData = new PretWzFormData();
    private isPretFormValid: boolean = false;
    private isEcheanceFormValid: boolean = false;
    private isElementFinancementFormValid: boolean = false;
    private isGarantieFormValid: boolean = false;


    constructor(private workflowService: PretWzWorkflowService) { 
    }

    getPret(): Pret {
        return this.formData.pret;
    }
    getData():PretWzFormData {
        return this.formData;
    }
    setPret(data: Pret) {
        this.isPretFormValid = true;
        this.formData.pret = data;
        this.workflowService.validateStep(PRET_WZ_STEPS.pret);
    }

    getElementFinancements() : ElementFinancement[] {
        return this.formData.elementFinancements;
    }
    
    setElementFinancements(data: ElementFinancement[]) {
        this.isElementFinancementFormValid = true;
        this.formData.elementFinancements = data;
        this.workflowService.validateStep(PRET_WZ_STEPS.elementFinancement);
    }
    getGaranties() : Garantie[] {
        return this.formData.garanties;
    }
    
    setGaranties(data: Garantie[]) {
        this.isGarantieFormValid = true;
        this.formData.garanties = data;
        this.workflowService.validateStep(PRET_WZ_STEPS.garantie);
    }
    getEcheances() : Echeance[] {
        return this.formData.echeances;
    }
    
    setEcheances(data: Echeance[]) {
        this.isEcheanceFormValid = true;
        this.formData.echeances = data;
        this.workflowService.validateStep(PRET_WZ_STEPS.echeance);
    }

    getFormData(): PretWzFormData {
        // Return the entire Form Data
        return this.formData;
    }

    resetFormData(): PretWzFormData {
        this.workflowService.resetSteps();
        this.formData=new PretWzFormData();
        this.isPretFormValid = this.isEcheanceFormValid = this.isElementFinancementFormValid = this.isGarantieFormValid = false;
        return this.formData;
    }

    isFormValid() {
        // Return true if all forms had been validated successfully; otherwise, return false
        return this.isPretFormValid &&
                this.isEcheanceFormValid && 
                this.isElementFinancementFormValid &&
                this.isGarantieFormValid;
    }
}