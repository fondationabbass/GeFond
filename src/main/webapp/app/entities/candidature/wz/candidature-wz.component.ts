import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Candidature } from '..';
import { CandidatureWzService } from '../../../shared/candidature-wz.service';
import { WizardWorkflowService } from '../../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../../shared/wizard-helper.service';
import { CandidatService } from '../../candidat';
import { Candidat } from '../../candidat/candidat.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-candidature-wz',
  templateUrl: './candidature-wz.component.html'
})
export class CandidatureWzComponent implements OnInit {

  candidature: Candidature;
  clientError: boolean = false;

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

  findCandidat(id: number) {
    this.candidatService.find(id).subscribe(
        (res: HttpResponse<Candidat>) => {
          this.candidature.candidat = res.body;
          this.clientError=false;
         }
         , (subRes: HttpErrorResponse) => {
          this.candidature.candidat = new Candidat();
          this.clientError=true;
         }
    );
    if(id==0) this.clientError=false;
}

  save() {
    this.service.setCandidature(this.candidature);
    this.wizardHelperService.candidatureWorkflow = this.workflowService.validateStep('candidature-wz', this.wizardHelperService.candidatureWorkflow);
  }
  goToNext() {
    this.save();
    this.router.navigate(['/candidature-wz-projet']);
  }
}