import { Component, OnInit,Input } from '@angular/core';
import { ParametrageService } from '../parametrage/parametrage.service';
import { Principal } from '../../shared';
import { CandWzFormDataService }       from './cand-wz-form-data.service';
import { Router } from '@angular/router';
import { Candidat } from '../candidat';
import { Candidature } from '.';
import { ExperienceCandidat } from '../experience-candidat';
import { CandWzFormData } from './cand-wz-form-data';

@Component({
  selector: 'candidature-wz-exp',
  templateUrl: './candidature-wz-exp.component.html',
  styles: []
})
export class CandidatureWzExpComponent implements OnInit {
  @Input() formData: CandWzFormData;
  experienceCandidat : ExperienceCandidat;
  candidat : Candidat;

  constructor(private router: Router,
    private formDataService: CandWzFormDataService,
    private parametrageService: ParametrageService,
    private principal: Principal) { }

  ngOnInit() {
    this.formData = this.formDataService.getData();
    this.experienceCandidat=this.formData.experienceCandidat;
    this.candidat=this.formData.candidat;
   
  }
  save(form: any): boolean {
    this.formDataService.setExperienceCandidat(this.experienceCandidat);
    return true;
  }
  goToNext(form: any) {
    if (this.save(form)) {
        this.router.navigate(['/candidature-wz-candidature']);
    }
}

}
