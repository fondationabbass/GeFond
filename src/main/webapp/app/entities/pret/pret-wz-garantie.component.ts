import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ParametrageService } from '../parametrage/parametrage.service';
import { Pret } from './pret.model';
import { JhiAlertService } from 'ng-jhipster';
import { PretWzFormDataService } from './pret-wz-form-data.service';
import { Garantie } from '../garantie';
import { ngbToDate } from '../../shared/model/format-utils';


@Component({
    selector: 'pret-wz-echeance'
    , templateUrl: './pret-wz-garantie.component.html'
})

export class PretWzGarantieComponent implements OnInit {
    garanties: Garantie[];
    garantie: Garantie;
    pret: Pret;
    title: string = "Saisie des garanties du pret";
    garantieTypes: string[];

    constructor(private router: Router,
        private jhiAlertService: JhiAlertService,
        private formDataService: PretWzFormDataService,
        private parametrageService : ParametrageService) {
    }

    ngOnInit() {
        this.pret = this.formDataService.getPret();
        this.garanties = this.formDataService.getGaranties();
        this.garantie={};
        this.parametrageService.garantiesTypes().subscribe(
            (res: HttpResponse<string[]>) => {
                this.garantieTypes = res.body;
            }
        );
    }
    empty(): boolean {
        return this.garanties.length === 0;
    }
    add() {
        const e = this.garantie;
        if(e.montantAfect + this.montantDebloque() > this.formDataService.getPret().montAaccord){
            this.jhiAlertService.warning("La somme des montants débloqués est supérieur au plafond du pret", null, null);
            return;
        }
        e.dateDepot = ngbToDate(e.dateDepot);
        e.dateRetrait = ngbToDate(e.dateRetrait);
        this.garanties.push(e);
        this.garantie = {};
    }
    private montantDebloque() {
        let montant: number = 0;
        this.garanties.forEach(function (item) {
            montant += item.dateDepot;
        });
        return montant;
    }
    updateGarantie(item: Garantie) {
        this.garantie = item;
        this.garanties.splice(this.garanties.indexOf(item), 1);

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
        if (montant < this.pret.montDebloq) {
            this.jhiAlertService.error("La somme des montants des garanties est inférieur au montant du pret", null, null);
            return false;
        }
        this.garanties.forEach(function (item) {
            item.etat= 'validé';
        });
        this.formDataService.setGaranties(garanties);
        return true;
    }

    goToPrevious(garanties: Garantie[]) {
        if (this.save(garanties)) {
            this.router.navigate(['/pret-wz-element-financement']);
        }
    }

    goToNext(garanties: Garantie[]) {
        if (this.save(garanties)) {
            this.router.navigate(['/pret-wz-echeance']);
        }
    }
}
