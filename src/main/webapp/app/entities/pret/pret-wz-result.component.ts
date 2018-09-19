import { Component, OnInit, Input }   from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { PretAggregate } from './pret-wz-aggregate.model';
import { PretAggregateService } from './pret-wz-aggregate.service';
import { PretService } from './pret.service';
import { Pret } from './pret.model';
import { Garantie } from '../garantie';
import { Echeance } from '../echeance';
import { ElementFinancement } from '../element-financement';


@Component ({
    selector:     'pret-wz-result'
    ,templateUrl: './pret-wz-result.component.html'
})

export class PretWzResultComponent implements OnInit {
    title = 'Vous arrivez à la fin!';
    @Input() formData: PretAggregate;
    isFormValid: boolean = false;
    pret: Pret;
    garanties:Garantie[];
    elementFinancements: ElementFinancement[];
    echeances:Echeance[];
    
    constructor(private formDataService: PretAggregateService,
        private jhiAlertService: JhiAlertService,
        private pretService: PretService,
        private router: Router,
        private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.formData = this.formDataService.getFormData();
        this.isFormValid = this.formDataService.isFormValid();
        this.pret=this.formData.pret;
        this.garanties=this.formData.garanties;
        this.elementFinancements=this.formData.elementFinancements;
        this.echeances=this.formData.echeances;
    }
    emptyEf(): boolean {
        return this.elementFinancements.length === 0;
    }
    emptyG(): boolean {
        return this.garanties.length === 0;
    }
    emptyE(): boolean {
        return this.echeances.length === 0;
    }
    goToPrevious() {
        this.router.navigate(['/pret-wz-echeance']);
    }
    clear(){
        this.formData = this.formDataService.resetFormData();
        this.isFormValid = false;
        this.router.navigate(['/pret-wz']);
    }
    goToNext() {
        this.pretService.createWz(this.formData).subscribe(
            (res: HttpResponse<Pret>) => {
                if (res.body.id > 0) {
                    this.eventManager.broadcast({ name: 'pretListModification', content: 'OK'});
                }
                
            },
            (res: HttpErrorResponse) => {this.onError(res)}
        );
        this.formData = this.formDataService.resetFormData();
        this.isFormValid = false;
        this.router.navigate(['/pret']);
    }
    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
