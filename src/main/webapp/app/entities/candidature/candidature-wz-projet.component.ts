import { Component, OnInit ,Input} from '@angular/core';
import { ParametrageService } from '../parametrage/parametrage.service';
import { Principal } from '../../shared';
import { CandWzFormDataService }       from './cand-wz-form-data.service';
import { Router } from '@angular/router';
import { Projet } from '../projet';
import { Candidature } from '../candidature';
import { CandWzFormData } from './cand-wz-form-data';

@Component({
  selector: 'candidature-wz-projet',
  templateUrl: './candidature-wz-projet.component.html',
  styles: []
})
export class CandidatureWzProjetComponent implements OnInit {
  @Input() formData: CandWzFormData;
  title = 'Please tell us about yourself.';
  projet : Projet;
  candidature : Candidature;
 
  constructor(private router: Router,
    private formDataService: CandWzFormDataService,
    private parametrageService: ParametrageService,
    private principal: Principal) { }

  ngOnInit() {
    this.formData = this.formDataService.getData();
    this.projet = this.formData.projet;
    this.candidature = this.formData.candidature;
  }
  
  save(form: any): boolean {
    this.formDataService.setProjet(this.projet);
    return true;
  }
  goToNext(form: any) {
    if (this.save(form)) {
        this.router.navigate(['/candidature-wz-result']);
    }
}
clear() {}
}
