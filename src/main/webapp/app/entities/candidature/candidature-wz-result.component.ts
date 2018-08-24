import { Component, OnInit, Input }   from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Candidature } from "./candidature.model";
import { Candidat} from "../candidat";
import { ExperienceCandidat} from "../experience-candidat";
import { Document} from "../document";
import { Projet} from "../projet";
import {CandidatureService} from './candidature.service'
import {CandWzFormDataService} from './cand-wz-form-data.service';
import {CandWzFormData} from './cand-wz-form-data';
@Component({
  selector: 'candidature-wz-result',
  templateUrl: './candidature-wz-result.component.html',
  styles: []
})
export class CandidatureWzResultComponent implements OnInit {

  title = 'Vous arrivez à la fin!';
  @Input() formData: CandWzFormData;
  isFormValid: boolean = false;

  candidat: Candidat;
  candidature:Candidature;
  experienceCandidat: ExperienceCandidat;
  document:Document;
  projet:Projet;
  
  constructor(private formDataService: CandWzFormDataService,
      private jhiAlertService: JhiAlertService,
      private candService: CandidatureService,
      private router: Router,
      private eventManager: JhiEventManager) {
  }


ngOnInit() {
    this.formData = this.formDataService.getData();
    this.isFormValid = this.formDataService.isFormValid();
    this.candidat=this.formData.candidat;
    this.experienceCandidat=this.formData.experiencecandidat;
    this.document=this.formData.document;
    this.projet=this.formData.projet;
}

clear(){
  this.formData = this.formDataService.resetFormData();
  this.isFormValid = false;
  this.router.navigate(['/candidature-wz-candidat']);
}

goToNext() {
  this.candService.createWz(this.formData).subscribe(
      (res: HttpResponse<Candidature>) => {
          if (res.body.id > 0) {
              this.eventManager.broadcast({ name: 'ListModification', content: 'OK'});
          }
          
      },
      (res: HttpErrorResponse) => {this.onError(res)}
  );
  this.formData = this.formDataService.resetFormData();
  this.isFormValid = false;
  this.router.navigate(['/candidature']);
}
private onError(error: any) {
  this.jhiAlertService.error(error.message, null, null);
}

}
