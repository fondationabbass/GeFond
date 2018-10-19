import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Candidat } from './candidat.model';
import { CandidatWzService } from './candidat-wz.service';
import { WizardWorkflowService } from '../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../shared/wizard-helper.service';

@Component({
    selector: 'jhi-candidat-wz',
    templateUrl: './candidat-wz.component.html'
})
export class CandidatWzComponent implements OnInit {

    candidat: Candidat;
    editFormInvalid: boolean;

    constructor(
        private router: Router,
        private service: CandidatWzService,
        private workflowService: WizardWorkflowService, 
        private wizardHelperService: WizardHelperService
    ) {
    }
    
    ngOnInit() {
        this.candidat = this.service.aggregate.candidat;
    }

    valid(e) {
        this.editFormInvalid = !e;
    }

    goToNext() {
        this.service.setCandidat(this.candidat);
        this.wizardHelperService.candidatWorkflow = this.workflowService.validateStep('candidat-wz', this.wizardHelperService.candidatWorkflow);
        this.router.navigate(['/candidat-wz-exp']);
    }
    
}
