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
      if (this.service.aggregate.candidature.type === 'Projet') {
        this.wizardHelperService.projetWorkflow = this.workflowService.validateStep('candidature-wz-projet', this.wizardHelperService.projetWorkflow);
        this.wizardHelperService.candidatureWorkflow = this.wizardHelperService.projetWorkflow;
        } else {
        this.wizardHelperService.agrWorkflow = this.workflowService.validateStep('candidature-wz-projet', this.wizardHelperService.agrWorkflow);
        this.wizardHelperService.candidatureWorkflow = this.wizardHelperService.agrWorkflow;
        }
  }

  isProjet(): boolean {
      return this.service.isProjet();
  }

  goToNext() {
      this.save();
      if (this.isProjet()) {
        this.router.navigate(['/candidature-wz-entretien']);
      } else {
        this.router.navigate(['/candidature-wz-visite']);
      }
  }

  goToPrevious() {
      this.save();
      this.router.navigate(['/candidature-wz']);
  }

}
