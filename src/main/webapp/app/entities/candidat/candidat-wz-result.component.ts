import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { CandidatWzService } from './candidat-wz.service';
import { CandidatAggregate } from './candidat-wz.model';
import { WizardWorkflowService } from '../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../shared/wizard-helper.service';
import { CandidatService } from '.';
import { Candidat } from './candidat.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'jhi-candidat-wz-result',
    templateUrl: './candidat-wz-result.component.html'
})
export class CandidatWzResultComponent implements OnInit {

    aggregate: CandidatAggregate;
    
    constructor(
        private router: Router,
        private service: CandidatWzService,
        private candidatService: CandidatService,
        private workflowService: WizardWorkflowService, 
        private wizardHelperService: WizardHelperService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }
    
    ngOnInit() {
        this.aggregate = this.service.aggregate;
    }

    create() {
        this.candidatService.createAggregate(this.aggregate).subscribe(
            (res: HttpResponse<Candidat>) => {
                if (res.body.id > 0) {
                    this.eventManager.broadcast({ name: 'candidatListModification', content: 'OK'});
                    this.wizardHelperService.candidatWorkflow = this.workflowService.resetSteps(this.wizardHelperService.candidatWorkflow);
                    this.router.navigate(['/candidat']);            
                }
                
            },
            (res: HttpErrorResponse) => {this.onError(res)}
        );
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    cancel() {
        this.service.reset();
        this.wizardHelperService.candidatWorkflow = this.workflowService.resetSteps(this.wizardHelperService.candidatWorkflow);
        this.router.navigate(['/candidat']);
    }

    goToPrevious() {
        this.wizardHelperService.candidatWorkflow = this.workflowService.validateStep('candidat-wz-result', this.wizardHelperService.candidatWorkflow);
        this.router.navigate(['/candidat-wz-exp']);
    }
}
