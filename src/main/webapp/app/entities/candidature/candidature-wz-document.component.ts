import { Component, OnInit ,Input} from '@angular/core';
import { ParametrageService } from '../parametrage/parametrage.service';
import { Principal } from '../../shared';
import { CandWzFormDataService }       from './cand-wz-form-data.service';
import { Router } from '@angular/router';
import { Document } from '../document';
import { Candidature } from '.';
import { CandWzFormData } from './cand-wz-form-data';

@Component({
  selector: 'candidature-wz-document',
  templateUrl: './candidature-wz-document.component.html',
  styles: []
})
export class CandidatureWzDocumentComponent implements OnInit {
  @Input() formData: CandWzFormData;
  document: Document;
  candidature : Candidature;

  constructor(private router: Router,
    private formDataService: CandWzFormDataService,
    private parametrageService: ParametrageService,
    private principal: Principal) { }

  ngOnInit() {
    this.formData = this.formDataService.getData();
    this.document = this.formData.document;
    this.candidature = this.formData.candidature;
  }
  save(form: any): boolean {
    this.formDataService.setDocument(this.document);
    return true;
  }
  goToNext(form: any) {
    if (this.save(form)) {
        this.router.navigate(['/candidature-wz-projet']);
    }
}
clear() {}

}
