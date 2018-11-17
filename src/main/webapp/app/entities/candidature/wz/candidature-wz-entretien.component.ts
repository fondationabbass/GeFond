import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { Entretien } from '../../entretien';
import { CandidatureWzService } from '../../../shared/candidature-wz.service';
import { WizardWorkflowService } from '../../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../../shared/wizard-helper.service';
import { ngbToDate } from '../../../shared/model/format-utils';

@Component({
  selector: 'jhi-candidature-wz-entretien',
  templateUrl: './candidature-wz-entretien.component.html'
})
export class CandidatureWzEntretienComponent implements OnInit {
  entretien: Entretien = new Entretien();
  entretiens: Entretien[];
  editFormInvalid: boolean;

  constructor(
      private router: Router,
      private service: CandidatureWzService,
      private workflowService: WizardWorkflowService, 
      private wizardHelperService: WizardHelperService
  ) {
  }
  
  ngOnInit() {
      this.entretiens = this.service.aggregate.entretiens;
  }

  valid(e) {
      this.editFormInvalid = !e;
  }

  add() {
      this.entretiens.push(this.convert(this.entretien));
  }

  save() {
      this.service.setEntretiens(this.entretiens);
      this.wizardHelperService.candidatureWorkflow = this.workflowService.validateStep('candidature-wz-entretien', this.wizardHelperService.candidatureWorkflow);
  }

  goToNext() {
      this.save();
      this.router.navigate(['/candidature-wz-result']);
  }

  goToPrevious() {
      this.save();
      this.router.navigate(['/candidature-wz-projet']);
  }
  
  private convert(entretien: Entretien): Entretien {
      const copy: Entretien = Object.assign({}, entretien);
      copy.dateEntretien = ngbToDate(entretien.dateEntretien);
      return copy;
  }
}