import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Echeance } from '../echeance';
import { Pret } from './pret.model';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import { PretWzFormDataService } from './pret-wz-form-data.service';
import { dateToNgb, ngbToDate } from '../../shared/model/format-utils';


@Component({
    selector: 'pret-wz-echeance'
    , templateUrl: './pret-wz-echeance.component.html'
})

export class PretWzEcheanceComponent implements OnInit {
    title = 'What do you do?';
    echeances: Echeance[];
    pret: Pret;

    constructor(private router: Router,
        private jhiAlertService: JhiAlertService,
        private formDataService: PretWzFormDataService) {
    }

    ngOnInit() {
        this.pret = this.formDataService.getPret();
        this.echeances = this.formDataService.getEcheances();
        if (this.echeances.length === 0) {
            for (var i: number = 0; i < this.pret.nbrEcheance; i++) {
                let echeance = new Echeance();
                echeance.montant = this.pret.montAaccord / this.pret.nbrEcheance;
                echeance.etatEcheance = "En cours";
                echeance.id = i;
                echeance.pret = this.pret;
                echeance.dateTombe = ngbToDate(this.pret.dateDerniereEcheance);
                echeance.dateRetrait = ngbToDate(this.pret.dateDerniereEcheance);
                echeance.mouvements = [];
                this.echeances.push(echeance);
            }
            this.formDataService.setEcheances(this.echeances);
        }
        console.log(this.echeances);
    }
    trackId(index: number, item: Echeance) {
        return item.id;
    }
    removeEcheance(item: Echeance) {
        this.echeances.splice(this.echeances.indexOf(item), 1);
        console.log(this.echeances);
    }
    save(echeances: Echeance[]): boolean {
        let montant: number = 0;
        this.echeances.forEach(function (item) {
            montant += item.montant;
        });
        console.log(montant);
        if (montant < this.pret.montAaccord) {
            this.jhiAlertService.error("La somme des montants des échéances est inférieur au montant du pret", null, null);
            return false;
        }

        this.formDataService.setEcheances(echeances);
        return true;
    }

    goToPrevious(echeances: Echeance[]) {
        if (this.save(echeances)) {
            // Navigate to the personal page
            console.log(this.formDataService.getFormData());
            this.router.navigate(['/pret-wz']);
        }
    }

    goToNext(echeances: Echeance[]) {
        if (this.save(echeances)) {
            this.router.navigate(['/pret-wz-element-financement']);
        }
    }
}