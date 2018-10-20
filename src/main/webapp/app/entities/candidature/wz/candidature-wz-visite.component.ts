import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { Visite } from '../../visite';
import { CandidatureWzService } from '../../../shared/candidature-wz.service';
import { WizardWorkflowService } from '../../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../../shared/wizard-helper.service';
import { ngbToDate } from '../../../shared/model/format-utils';

@Component({
  selector: 'candidature-wz-visite',
  templateUrl: './candidature-wz-visite.component.html'
})
export class CandidatureWzVisiteComponent implements OnInit {
  visite: Visite = new Visite();
  visites: Visite[];
  editFormInvalid: boolean;

  constructor(
      private router: Router,
      private service: CandidatureWzService,
      private workflowService: WizardWorkflowService, 
      private wizardHelperService: WizardHelperService
  ) {
  }
  
  ngOnInit() {
      this.visites = this.service.aggregate.visites;
  }

  valid(e) {
      this.editFormInvalid = !e;
  }

  add() {
      this.visites.push(this.convert(this.visite));
  }

  save() {
      this.service.setVisites(this.visites);
      this.wizardHelperService.candidatureWorkflow = this.workflowService.validateStep('candidature-wz-visite', this.wizardHelperService.candidatureWorkflow);
  }

  goToNext() {
      this.save();
      this.router.navigate(['/candidature-wz-result']);
  }

  goToPrevious() {
      this.save();
      this.router.navigate(['/candidature-wz-entretien']);
  }
  
  private convert(visite: Visite): Visite {
      const copy: Visite = Object.assign({}, visite);
      copy.dateVisite = ngbToDate(visite.dateVisite);
      return copy;
  }
}