import { Component, OnInit, Input }   from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ParametrageService } from '../parametrage/parametrage.service';
import { Principal } from '../../shared';
import { CandWzFormDataService }       from './cand-wz-form-data.service';
import { Router } from '@angular/router';
import { Candidature } from '.';
import { Candidat } from '../candidat';
import { CandWzFormData } from './cand-wz-form-data';
import { ExperienceCandidat } from '../experience-candidat';

@Component({
  selector: 'candidature-wz-candidature',
  templateUrl: './candidature-wz-candidature.component.html',
  styles: []
})
export class CandidatureWzCandidatureComponent implements OnInit {
  @Input() formData: CandWzFormData;
  candidature: Candidature;
  candidat : Candidat;

 
  constructor(private router: Router,
    private formDataService: CandWzFormDataService,
    private parametrageService: ParametrageService,
    private principal: Principal) { }

  ngOnInit() {
  
    this.formData = this.formDataService.getData();
    this.candidat=this.formData.candidat;
    this.candidature=this.formData.candidature;

  }
 
  save(form: any): boolean {
    this.formDataService.setCandidature(this.candidature);
    return true;
  }
  goToNext(form: any) {
    if (this.save(form)) {
        this.router.navigate(['/candidature-wz-document']);
    }
}

}
