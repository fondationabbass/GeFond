import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Echeance, PeriodType } from '../echeance';
import { Pret } from './pret.model';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import { PretWzFormDataService } from './pret-wz-form-data.service';
import { dateToNgb, ngbToDate } from '../../shared/model/format-utils';


@Component({
    selector: 'pret-wz-echeance'
    , templateUrl: './pret-wz-echeance.component.html'
})

export class PretWzEcheanceComponent implements OnInit {
    title = "L'échéancier du prêt:";
    echeances: Echeance[];
    echeance: Echeance;
    validatePret: boolean;
    pret: Pret;
    periodType:PeriodType;

    constructor(private router: Router,
        private jhiAlertService: JhiAlertService,
        private formDataService: PretWzFormDataService) {
    }

    private coeff(period: string): number {
        if (period === "Trimestriel")
            return 3;
        if (period === "Bimensuel")
            return 2;
        return 1
    }
    ngOnInit() {
        this.pret = this.formDataService.getPret();
        this.echeances = [];
        this.validatePret = this.formDataService.getData().validatePret;
        this.echeance = {};
        this.periodType = this.formDataService.getData().periodType;
        
        const startDate: Date = ngbToDate(this.pret.datePremiereEcheance);
        for (var i: number = 0; i < this.pret.nbrEcheance; i++) {
            let e = new Echeance();
            e.montant = this.pret.montDebloq / this.pret.nbrEcheance;
            e.etatEcheance = "En cours";
            e.id = i;
            e.pret = this.pret;
            e.montantPaye=0;
            const copy: Date = new Date(startDate.getTime());
            if(this.periodType.type==="month")
                copy.setMonth(startDate.getMonth() + this.periodType.coeff * i);
            if(this.periodType.type==="year")
                copy.setFullYear(startDate.getFullYear() + this.periodType.coeff * i);
            e.dateTombe = copy;
            e.mouvements = [];
            this.echeances.push(e);
        }
        this.formDataService.setEcheances(this.echeances);
    }
    trackId(index: number, item: Echeance) {
        return item.id;
    }
    add() {
        const e = this.echeance;
        if (e.montant + this.montantGlobal() > this.formDataService.getPret().montDebloq) {
            this.jhiAlertService.error("La somme des montants de l'échéancier est supérieur au montant débloqué", null, null);
            return;
        }
        e.dateTombe = ngbToDate(e.dateTombe);
        e.datePayement = ngbToDate(e.datePayement);
        e.dateRetrait = ngbToDate(e.dateRetrait);
        this.echeances.push(this.echeance);
        this.echeance = {};

    }
    private montantGlobal() {
        let montant: number = 0;
        this.echeances.forEach(function (item) {
            montant += item.montant;
        });
        return montant;
    }
    removeEcheance(item: Echeance) {
        this.echeances.splice(this.echeances.indexOf(item), 1);
    }
    updateEcheance(item: Echeance) {
        this.echeance = item;
        this.echeance.dateTombe = dateToNgb(item.dateTombe);
        this.echeance.dateRetrait = dateToNgb(item.dateRetrait);
        this.echeance.datePayement = dateToNgb(item.datePayement);
        this.echeances.splice(this.echeances.indexOf(item), 1);
    }
    save(echeances: Echeance[]): boolean {
        let montant: number = 0;
        this.echeances.forEach(function (item) {
            montant += item.montant;
        });
        if (montant < this.pret.montDebloq) {
            this.jhiAlertService.error("La somme des montants des échéances est inférieur au montant du prêt", null, null);
            return false;
        }

        this.formDataService.setEcheances(echeances);
        return true;
    }

    goToPrevious(echeances: Echeance[]) {
        if (this.save(echeances)) {
            console.log(this.formDataService.getFormData());
            this.router.navigate(['/pret-wz-garantie']);
        }
    }

    goToNext(echeances: Echeance[]) {
        if (this.save(echeances)) {
            this.router.navigate(['/pret-wz-result']);
        }
    }
}