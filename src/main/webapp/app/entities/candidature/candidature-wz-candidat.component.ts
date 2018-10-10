import { Component, OnInit } from '@angular/core';
import { ParametrageService } from '../parametrage/parametrage.service';
import { Principal } from '../../shared';
import { CandWzFormDataService }       from './cand-wz-form-data.service';
import { Router } from '@angular/router';
import { Candidat } from '../candidat';


@Component({
  selector: 'candidature-wz-candidat',
  templateUrl: './candidature-wz-candidat.component.html',
  styles: []
})
export class CandidatureWzCandidatComponent implements OnInit {
  title = 'Please tell us about yourself.';
  candidat : Candidat;
 
  constructor(private router: Router,
    private formDataService: CandWzFormDataService,
    private parametrageService: ParametrageService,
    private principal: Principal) { }

  ngOnInit() {
    this.candidat = this.formDataService.getCandidat();
  }
  save(form: any): boolean {
    this.formDataService.setCandidat(this.candidat);
    return true;
  }
  goToNext(form: any) {
    if (this.save(form)) {
        this.router.navigate(['/candidature-wz-exp']);
    }    
  }
  clear() {}
}
