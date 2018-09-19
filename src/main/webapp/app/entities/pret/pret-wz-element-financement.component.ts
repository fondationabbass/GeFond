import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { PretAggregateService } from './pret-wz-aggregate.service';
import { ElementFinancement } from '../element-financement';
import { ngbToDate } from '../../shared/model/format-utils';
import { ParametrageService } from '../parametrage/parametrage.service';
import { PeriodType } from '../echeance';


@Component({
    selector: 'pret-wz-element-financement'
    , templateUrl: './pret-wz-element-financement.component.html'
})

export class PretWzElementFinancementComponent implements OnInit {
    title: string = "Débloquage des montants";
    elementFinancements: ElementFinancement[];
    elementFinancement: ElementFinancement;
    financementTypes: string[];
    periodType: PeriodType;

    constructor(private router: Router,
        private jhiAlertService: JhiAlertService,
        private formDataService: PretAggregateService,
        private parametrageService : ParametrageService) {
    }

    ngOnInit() {
        this.elementFinancements = this.formDataService.getElementFinancements();
        this.elementFinancement = {};
        this.periodType=this.formDataService.getData().periodType;
        this.parametrageService.financementTypes().subscribe(
            (res: HttpResponse<string[]>) => {
                this.financementTypes = res.body;
            }
        );
    }
    empty(): boolean {
        return this.elementFinancements.length === 0;
    }
    add() {
        const e = this.elementFinancement;
        if(e.montant + this.montantDebloque() > this.formDataService.getPret().montAaccord){
            this.jhiAlertService.error("La somme des montants débloqués est supérieur au plafond du pret", null, null);
            return;
        }
        e.dateFinancement = ngbToDate(e.dateFinancement);
        this.elementFinancements.push(e);
        this.elementFinancement = {};
    }
    trackId(index: number, item: ElementFinancement) {
        return item.id;
    }
    removeElementFinancement(item: ElementFinancement) {
        this.elementFinancements.splice(this.elementFinancements.indexOf(item), 1);
    }
    updateElementFinancement(item: ElementFinancement) {
        this.elementFinancement = item;
        this.elementFinancements.splice(this.elementFinancements.indexOf(item), 1);

    }
    save(elements: ElementFinancement[]): boolean {
        let montant: number = this.montantDebloque();
        if (montant > 0) {
            this.formDataService.getPret().montDebloq = montant;
            this.formDataService.getPret().dateDernierDebloq = new Date();
            this.formDataService.getPret().etat = 'Mis en place';
            if (montant < this.formDataService.getPret().montAaccord) {
                this.jhiAlertService.warning("La somme des montants débloqués est inférieur au plafond du pret", null, null);
            }
        }
        this.formDataService.setElementFinancements(elements);
        return true;
    }

    private montantDebloque() {
        let montant: number = 0;
        this.elementFinancements.forEach(function (item) {
            montant += item.montant;
        });
        return montant;
    }

    goToPrevious(elements: ElementFinancement[]) {
        if (this.save(elements)) {
            this.router.navigate(['/pret-wz']);
        }
    }

    goToNext(elements: ElementFinancement[]) {
        if (this.save(elements)) {
            this.router.navigate(['/pret-wz-garantie']);
        }
    }
}