import { Component, OnInit, Input } from '@angular/core';
import { PretWzFormDataService }            from './pret-wz-form-data.service';


@Component({
    selector: 'jhi-pretcreate',
    templateUrl: './pret-create.component.html'
})
export class PretCreateComponent implements OnInit {

    title = 'Multi-Step Wizard';
    @Input() formData;
    
    constructor(private formDataService: PretWzFormDataService) {
    }

    ngOnInit() {
        this.formData = this.formDataService.getFormData();
        console.log(this.title + ' loaded!');
    }
}
