import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Pret } from './pret.model';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { PretWzFormDataService } from './pret-wz-form-data.service';
import { ElementFinancement } from '../element-financement';


@Component({
    selector: 'pret-wz-element-financement'
    , templateUrl: './pret-wz-element-financement.component.html'
})

export class PretWzElementFinancementComponent implements OnInit {
    formDataService: PretWzFormDataService;
    elementFinancements: ElementFinancement[];
    pret: Pret;

    constructor(private router: Router, 
        private jhiAlertService: JhiAlertService,
        formDataService: PretWzFormDataService) {
        this.formDataService = formDataService;
    }

    ngOnInit() {
        this.pret = this.formDataService.getPret();
        this.elementFinancements = this.formDataService.getElementFinancements();
        if(this.elementFinancements.length===0){
            let ele: ElementFinancement = new ElementFinancement();
            ele.pret = this.pret;
            ele.type = "Cash";
            ele.dateFinancement = this.pret.dateMisePlace;
            ele.montant = this.pret.montAaccord;
            ele.id=0;
            this.elementFinancements.push(ele);
            this.formDataService.setElementFinancements(this.elementFinancements);
        }
        console.log(this.elementFinancements);
    }
    trackId(index: number, item: ElementFinancement) {
        return item.id;
    }
    removeElementFinancement(item: ElementFinancement) {
        this.elementFinancements.splice(this.elementFinancements.indexOf(item), 1);
    }
    save(elements: ElementFinancement[]): boolean {
        let montant: number = 0;
        this.elementFinancements.forEach(function (item) {
            montant += item.montant;
        });
        console.log(montant);
        if (montant < this.pret.montAaccord){
            this.jhiAlertService.error("La somme des montants des elements de financement est inférieur au montant du pret",null,null);
            return false;
        }
            
        this.formDataService.setElementFinancements(elements);
        return true;
    }

    goToPrevious(elements: ElementFinancement[]) {
        if (this.save(elements)) {
            // Navigate to the personal page
            console.log(this.formDataService.getFormData());
            this.router.navigate(['/pret-wz-echeance']);
        }
    }

    goToNext(elements: ElementFinancement[]) {
        if (this.save(elements)) {
            this.router.navigate(['/pret-wz-garantie']);
        }
    }
}