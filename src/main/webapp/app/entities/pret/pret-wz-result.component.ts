import { Component, OnInit, Input }   from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { PretWzFormData } from './pret-wz-form-data.model';
import { PretWzFormDataService } from './pret-wz-form-data.service';
import { PretService } from './pret.service';
import { Pret } from './pret.model';


@Component ({
    selector:     'pret-wz-result'
    ,templateUrl: './pret-wz-result.component.html'
})

export class PretWzResultComponent implements OnInit {
    title = 'Thanks for staying tuned!';
    @Input() formData: PretWzFormData;
    isFormValid: boolean = false;
    
    constructor(private formDataService: PretWzFormDataService,
        private jhiAlertService: JhiAlertService,
        private pretService: PretService,
        private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.formData = this.formDataService.getFormData();
        this.isFormValid = this.formDataService.isFormValid();
        console.log('Result feature loaded!');
    }

    create() {
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
    }
    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
