import { Component, OnInit, Input }   from '@angular/core';
import { PretWzFormData } from './pret-wz-form-data.model';
import { PretWzFormDataService } from './pret-wz-form-data.service';


@Component ({
    selector:     'pret-wz-result'
    ,templateUrl: './pret-wz-result.component.html'
})

export class PretWzResultComponent implements OnInit {
    title = 'Thanks for staying tuned!';
    @Input() formData: PretWzFormData;
    isFormValid: boolean = false;
    
    constructor(private formDataService: PretWzFormDataService) {
    }

    ngOnInit() {
        this.formData = this.formDataService.getFormData();
        this.isFormValid = this.formDataService.isFormValid();
        console.log('Result feature loaded!');
    }

    submit() {
        alert('Excellent Job!');
        this.formData = this.formDataService.resetFormData();
        this.isFormValid = false;
    }
}
