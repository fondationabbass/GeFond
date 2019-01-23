import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Echeance, PeriodType } from '../echeance';
import { Pret } from './pret.model';
import { JhiAlertService } from 'ng-jhipster';
import { PretAggregateService } from './pret-wz-aggregate.service';
import { dateToNgb, ngbToDate } from '../../shared/model/format-utils';

@Component({
    selector: 'pret-wz-echeance'
    , templateUrl: './pret-wz-echeance.component.html'
})

export class PretWzEcheanceComponent implements OnInit {
    title = 'Echéancier du prêt:';
    echeances: Echeance[];
    echeance: Echeance;
    pret: Pret;
    periodType: PeriodType;

    constructor(private router: Router,
        private jhiAlertService: JhiAlertService,
        private formDataService: PretAggregateService) {
    }

    ngOnInit() {
        this.pret = this.formDataService.getPret();
        this.echeances = [];
        this.echeance = {};
        this.periodType = this.formDataService.getData().periodType;
        const startDate: Date = ngbToDate(this.pret.datePremiereEcheance);
        if (!this.pret.montDebloq) {
            this.pret.montDebloq = 0;
        }
        for (let i = 0; i < this.pret.nbrEcheance; i++) {
            const e = new Echeance();
            e.montant = this.pret.montDebloq / this.pret.nbrEcheance;
            e.montant = Math.round(e.montant * 100) / 100;
            e.etatEcheance = 'En cours';
            e.id = i;
            e.pret = this.pret;
            e.montantPaye = 0;
            const dateTombe: Date = new Date(startDate.getTime());
            if (this.periodType.type === 'month') {
                dateTombe.setMonth(startDate.getMonth() + this.periodType.coeff * i);
            }
            if (this.periodType.type === 'year') {
                dateTombe.setFullYear(startDate.getFullYear() + this.periodType.coeff * i);
            }
            e.dateTombe = dateTombe;
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
            this.jhiAlertService.error('La somme des montants de l\'échéancier est supérieur au montant débloqué', null, null);
            return;
        }
        e.dateTombe = ngbToDate(e.dateTombe);
        e.datePayement = ngbToDate(e.datePayement);
        e.dateRetrait = ngbToDate(e.dateRetrait);
        this.echeances.push(this.echeance);
        this.echeance = {};

    }
    private montantGlobal() {
        let montant = 0;
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
        let montant = 0;
        this.echeances.forEach(function(item) {
            montant += item.montant;
        });
        if (Math.abs(montant - this.pret.montDebloq) > 100) {
            this.jhiAlertService.error('La somme des montants des échéances est inférieur au montant du prêt', null, null);
            return false;
        }

        this.formDataService.setEcheances(echeances);
        return true;
    }

    goToPrevious(echeances: Echeance[]) {
        if (this.save(echeances)) {
            console.log(this.formDataService.getFormData());
            this.router.navigate(['/pret-wz-element-financement']);
        }
    }

    goToNext(echeances: Echeance[]) {
        if (this.save(echeances)) {
            this.router.navigate(['/pret-wz-result']);
        }
    }
}