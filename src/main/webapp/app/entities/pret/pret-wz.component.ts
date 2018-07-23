import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { dateToNgb, ngbToDate } from '../../shared/model/format-utils';
import { Client, ClientService } from '../client';
import { PretWzFormDataService } from './pret-wz-form-data.service';
import { Pret } from './pret.model';
import { ParametrageService } from '../parametrage/parametrage.service';

@Component({
    selector: 'pret-wz'
    , templateUrl: './pret-wz.component.html'
})

export class PretWzComponent implements OnInit {
    title = 'Please tell us about yourself.';
    pret: Pret;
    pretTypes: string[];
    form: any;

    constructor(private router: Router,
        private formDataService: PretWzFormDataService,
        private parametrageService: ParametrageService,
        private clientService: ClientService) {
    }

    ngOnInit() {
        this.pret = this.formDataService.getPret();
        if(!this.pret.client) {        
            this.pret.client = {};
            this.pret.client.candidat = {};
        }
        this.parametrageService.pretTypes().subscribe(
            (res: HttpResponse<string[]>) => {
                this.pretTypes = res.body;
            }
        );
    }
    findClient(code: string) {
        this.clientService.query({ "code.equals": code }).subscribe(
            (res: HttpResponse<Client[]>) => {
                if (res.body.length > 0) {
                    this.pret.client = res.body.pop();
                }
                else {
                    this.pret.client = {};
                    this.pret.client.candidat = {};
                }
            }
        );
    }
    save(form: any): boolean {
        this.formDataService.setPret(this.pret);
        return true;
    }

    goToNext(form: any) {
        if (this.save(form)) {
            this.router.navigate(['/pret-wz-element-financement']);
        }
    }
    computeEndDate(event: any) {
        if (!this.pret.periodicite || !this.pret.datePremiereEcheance || !this.pret.nbrEcheance)
            return;
        let period: string = this.pret.periodicite;
        let coeff: number = this.coeff(period);
        let startDate: Date = ngbToDate(this.pret.datePremiereEcheance);
        let nbrPeriod: number = this.pret.nbrEcheance;
        startDate.setMonth(startDate.getMonth() + coeff * nbrPeriod);
        this.pret.dateDerniereEcheance = dateToNgb(startDate);
    }
    private coeff(period: string): number {
        if (period === "Trimestriel")
            return 3;
        if (period === "Bimensuel")
            return 2;
        return 1
    }
}
