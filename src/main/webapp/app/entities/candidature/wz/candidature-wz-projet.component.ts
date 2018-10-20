import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Projet } from '../../projet';
//import { CandidatureWzService } from '../candidature-wz.b.service';
import { CandidatureWzService } from '../../../shared/candidature-wz.service';
import { WizardWorkflowService } from '../../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../../shared/wizard-helper.service';

@Component({
  selector: 'jhi-candidature-wz-projet',
  templateUrl: './candidature-wz-projet.component.html'
})
export class CandidatureWzProjetComponent implements OnInit {
  
  projet: Projet;
  editFormInvalid: boolean;

  constructor(
      private router: Router,
      private service: CandidatureWzService,
      private workflowService: WizardWorkflowService, 
      private wizardHelperService: WizardHelperService
  ) {
  }
  
  ngOnInit() {
      this.projet = this.service.aggregate.projet;
  }

  valid(e) {
      this.editFormInvalid = !e;
  }

  save() {
      this.service.setProjet(this.projet);
      this.wizardHelperService.candidatureWorkflow = this.workflowService.validateStep('candidature-wz-projet', this.wizardHelperService.candidatureWorkflow);
  }

  goToNext() {
      this.save();
      this.router.navigate(['/candidature-wz-document']);
  }

  goToPrevious() {
      this.save();
      this.router.navigate(['/candidature-wz']);
  }

}
