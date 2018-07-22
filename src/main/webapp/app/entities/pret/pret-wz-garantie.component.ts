import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Echeance } from '../echeance';
import { Pret } from './pret.model';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { PretWzFormDataService } from './pret-wz-form-data.service';
import { Garantie } from '../garantie';


@Component({
    selector: 'pret-wz-echeance'
    , templateUrl: './pret-wz-garantie.component.html'
})

export class PretWzGarantieComponent implements OnInit {
    formDataService: PretWzFormDataService;
    garanties: Garantie[];
    pret: Pret;

    constructor(private router: Router,
        private jhiAlertService: JhiAlertService,
        formDataService: PretWzFormDataService) {
        this.formDataService = formDataService;
    }

    ngOnInit() {
        this.pret = this.formDataService.getPret();
        this.garanties = this.formDataService.getGaranties();
        if (this.garanties.length === 0) {
            let garantie = new Garantie();
            garantie.pret = this.pret;
            garantie.dateDepot = new Date();
            garantie.etat = "Vaildée";
            garantie.montantEvalue = this.pret.montAaccord;
            garantie.typeGar = "Immobilier";
            garantie.dateRetrait = this.pret.dateDerniereEcheance;
            this.garanties.push(garantie);
            this.formDataService.setGaranties(this.garanties);
        }
        console.log(this.garanties);
    }
    trackId(index: number, item: Garantie) {
        return item.id;
    }
    removeGarantie(item: Garantie) {
        this.garanties.splice(this.garanties.indexOf(item), 1);
    }
    save(garanties: Garantie[]): boolean {
        let montant: number = 0;
        this.garanties.forEach(function (item) {
            montant += item.montantAfect;
        });
        console.log(montant);
        if (montant < this.pret.montAaccord) {
            this.jhiAlertService.error("La somme des montants des garanties est inférieur au montant du pret", null, null);
            return false;
        }

        this.formDataService.setGaranties(garanties);
        return true;
    }

    goToPrevious(garanties: Garantie[]) {
        if (this.save(garanties)) {
            console.log(this.formDataService.getFormData());
            this.router.navigate(['/pret-wz-element-financement']);
        }
    }

    goToNext(garanties: Garantie[]) {
        if (this.save(garanties)) {
            this.router.navigate(['/pret-wz-result']);
        }
    }
}