import { Component, OnInit, Input }   from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import {CandidatureService} from '../candidature.service'
import { CandidatureAggregate } from '../candidature-wz.model';
import { CandidatureWzService } from '../../../shared/candidature-wz.service';
import { WizardWorkflowService } from '../../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../../shared/wizard-helper.service';
import { Candidature } from '../candidature.model';
@Component({
  selector: 'candidature-wz-result',
  templateUrl: './candidature-wz-result.component.html',
  styles: []
})
export class CandidatureWzResultComponent implements OnInit {

  aggregate: CandidatureAggregate;
  constructor(
      private router: Router,
      private service: CandidatureWzService,
      private candidatureService: CandidatureService,
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
      console.log('creating...');
      this.candidatureService.createAggregate(this.aggregate).subscribe(
          (res: HttpResponse<Candidature>) => {
              if (res.body.id > 0) {
                  this.eventManager.broadcast({ name: 'candidatureListModification', content: 'OK'});
                  this.wizardHelperService.candidatWorkflow = this.workflowService.resetSteps(this.wizardHelperService.candidatWorkflow);
                  this.router.navigate(['/candidature']);
              }
          },
          (res: HttpErrorResponse) => {this.onError(res); }
      );
  }

  private onError(error: any) {
      this.jhiAlertService.error(error.message, null, null);
  }

  cancel() {
      this.service.reset();
      this.wizardHelperService.candidatWorkflow = this.workflowService.resetSteps(this.wizardHelperService.candidatWorkflow);
      this.router.navigate(['/candidature']);
  }

  isProjet(): boolean {
      return this.service.isProjet();
  }

  goToPrevious() {
      this.wizardHelperService.candidatureWorkflow = this.workflowService.validateStep('candidature-wz-result', this.wizardHelperService.candidatureWorkflow);
      if (this.isProjet()) {
        this.router.navigate(['/candidature-wz-entretien']);
      } else {
      this.router.navigate(['/candidature-wz-visite']);
      }
    }
}
