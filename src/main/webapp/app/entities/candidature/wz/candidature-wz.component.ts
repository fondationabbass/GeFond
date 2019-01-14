import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Candidature } from '..';
import { CandidatureWzService } from '../../../shared/candidature-wz.service';
import { WizardWorkflowService } from '../../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../../shared/wizard-helper.service';
import { CandidatService } from '../../candidat';
import { Candidat } from '../../candidat/candidat.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { CandidatAggregate } from '../../candidat/candidat-wz.model';
import { ExperienceCandidat } from '../../experience-candidat';

@Component({
  selector: 'jhi-candidature-wz',
  templateUrl: './candidature-wz.component.html'
})
export class CandidatureWzComponent implements OnInit {

  candidature: Candidature;
  exps: ExperienceCandidat[];
  clientError = false;

  constructor(private router: Router,
    private service: CandidatureWzService,
    private candidatService: CandidatService,
    private workflowService: WizardWorkflowService,
    private wizardHelperService: WizardHelperService
  ) {
  }

  ngOnInit() {
    this.candidature = this.service.aggregate.candidature;
  }

  findCandidat(nni: number) {
    this.candidatService.findAggregate(nni).subscribe(
        (res: HttpResponse<CandidatAggregate>) => {
          this.candidature.candidat = res.body.candidat;
          this.exps = res.body.exps;
          this.clientError = false;
         }
         , (subRes: HttpErrorResponse) => {
          this.candidature.candidat = new Candidat();
          this.clientError = true;
         }
    );
    if (nni === 0) {
      this.clientError = false;
    }
}

  save() {
    this.service.setCandidature(this.candidature);
    this.service.setExps(this.exps);
    this.wizardHelperService.candidatureWorkflow = this.workflowService.validateStep('candidature-wz', this.wizardHelperService.candidatureWorkflow);
    this.wizardHelperService.agrWorkflow = this.workflowService.validateStep('candidature-wz', this.wizardHelperService.agrWorkflow);
    this.wizardHelperService.projetWorkflow = this.workflowService.validateStep('candidature-wz', this.wizardHelperService.projetWorkflow);
  }
  goToNext() {
    this.save();
    this.router.navigate(['/candidature-wz-projet']);
  }
}
