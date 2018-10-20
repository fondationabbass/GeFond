import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Document } from '../../document';
import { CandidatureWzService } from '../../../shared/candidature-wz.service';
import { WizardWorkflowService } from '../../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../../shared/wizard-helper.service';
import { ngbToDate } from '../../../shared/model/format-utils';

@Component({
  selector: 'candidature-wz-document',
  templateUrl: './candidature-wz-document.component.html'
})
export class CandidatureWzDocumentComponent implements OnInit {
  document: Document = new Document();
  documents: Document[];
  editFormInvalid: boolean;

  constructor(
      private router: Router,
      private service: CandidatureWzService,
      private workflowService: WizardWorkflowService, 
      private wizardHelperService: WizardHelperService
  ) {
  }
  
  ngOnInit() {
      this.documents = this.service.aggregate.documents;
  }

  valid(e) {
      this.editFormInvalid = !e;
  }

  add() {
      this.documents.push(this.convert(this.document));
  }

  save() {
      this.service.setDocuments(this.documents);
      this.wizardHelperService.candidatureWorkflow = this.workflowService.validateStep('candidature-wz-document', this.wizardHelperService.candidatureWorkflow);
  }

  goToNext() {
      this.save();
      this.router.navigate(['/candidature-wz-entretien']);
  }

  goToPrevious() {
      this.save();
      this.router.navigate(['/candidature-wz-projet']);
  }
  
  private convert(Document: Document): Document {
      const copy: Document = Object.assign({}, Document);
      copy.dateEnreg = ngbToDate(Document.dateEnreg);
      return copy;
  }
}