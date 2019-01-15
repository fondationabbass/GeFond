import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ParametrageService } from '../parametrage/parametrage.service';
import { Pret } from './pret.model';
import { JhiAlertService } from 'ng-jhipster';
import { PretAggregateService } from './pret-wz-aggregate.service';
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
    title = 'Saisie des garanties du pret';
    garantieTypes: string[];

    constructor(private router: Router,
        private jhiAlertService: JhiAlertService,
        private formDataService: PretAggregateService,
        private parametrageService : ParametrageService) {
    }

    ngOnInit() {
        this.pret = this.formDataService.getPret();
        this.garanties = this.formDataService.getGaranties();
        this.garantie = {};
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
        e.dateDepot = ngbToDate(e.dateDepot);
        e.dateRetrait = ngbToDate(e.dateRetrait);
        this.garanties.push(e);
        this.garantie = {};
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
        this.garanties.forEach ( function(item) {
            item.etat = 'validé';
        });
        this.formDataService.setGaranties(garanties);
        return true;
    }

    goToPrevious(garanties: Garantie[]) {
        if (this.save(garanties)) {
            this.router.navigate(['/pret-wz']);
        }
    }

    goToNext(garanties: Garantie[]) {
        if (this.save(garanties)) {
            this.router.navigate(['/pret-wz-element-financement']);
        }
    }
}
