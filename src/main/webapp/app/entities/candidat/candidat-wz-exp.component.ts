import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ExperienceCandidat } from '../experience-candidat';
import { CandidatWzService } from './candidat-wz.service';
import { ngbToDate } from '../../shared/model/format-utils';
import { WizardWorkflowService } from '../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../shared/wizard-helper.service';

@Component({
    selector: 'jhi-candidat-wz-exp',
    templateUrl: './candidat-wz-exp.component.html'
})
export class CandidatWzExpComponent implements OnInit {

    exp: ExperienceCandidat = new ExperienceCandidat();
    exps: ExperienceCandidat[];
    editFormInvalid: boolean;

    constructor(
        private router: Router,
        private service: CandidatWzService,
        private workflowService: WizardWorkflowService, 
        private wizardHelperService: WizardHelperService
    ) {
    }
    
    ngOnInit() {
        this.exps = this.service.aggregate.exps;
    }

    valid(e) {
        this.editFormInvalid = !e;
    }

    add() {
        this.exps.push(this.convert(this.exp));
    }

    save() {
        this.service.setExpCandidat(this.exps);
        this.wizardHelperService.candidatWorkflow = this.workflowService.validateStep('candidat-wz-exp', this.wizardHelperService.candidatWorkflow);
    }

    goToNext() {
        this.save();
        this.router.navigate(['/candidat-wz-result']);
    }

    goToPrevious() {
        this.save();
        this.router.navigate(['/candidat-wz']);
    }
    
    private convert(experienceCandidat: ExperienceCandidat): ExperienceCandidat {
        const copy: ExperienceCandidat = Object.assign({}, experienceCandidat);
        copy.dateDeb = ngbToDate(experienceCandidat.dateDeb);
        copy.dateFin = ngbToDate(experienceCandidat.dateFin);
        return copy;
    }
}
