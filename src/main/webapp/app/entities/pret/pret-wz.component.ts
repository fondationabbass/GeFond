import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { dateToNgb, ngbToDate } from '../../shared/model/format-utils';
import { Client, ClientService } from '../client';
import { PretWzFormDataService } from './pret-wz-form-data.service';
import { Pret } from './pret.model';
import { ParametrageService } from '../parametrage/parametrage.service';
import { Principal } from '../../shared';
import { PeriodType } from '../echeance';

@Component({
    selector: 'pret-wz'
    , templateUrl: './pret-wz.component.html'
})

export class PretWzComponent implements OnInit {
    title = 'Please tell us about yourself.';
    pret: Pret;
    pretTypes: string[];
    periodTypes: PeriodType[];
    periodType: PeriodType;
    form: any;
    clientError:boolean = false;

    constructor(private router: Router,
        private formDataService: PretWzFormDataService,
        private parametrageService: ParametrageService,
        private principal: Principal,
        private clientService: ClientService) {
    }

    ngOnInit() {
        this.pret = this.formDataService.getPret();
        if(!this.pret.client) {        
            this.pret.client = {};
            this.pret.client.candidat = {};
        }
        this.periodType=this.formDataService.getData().periodType;
        this.parametrageService.pretTypes().subscribe(
            (res: HttpResponse<string[]>) => {
                this.pretTypes = res.body;
            }
        ); 
        this.parametrageService.periodTypes().subscribe(
            (res: HttpResponse<PeriodType[]>) => {
                this.periodTypes = res.body;
            }
        );
    }
    findClient(code: string) {
        this.clientService.query({ "code.equals": code }).subscribe(
            (res: HttpResponse<Client[]>) => {
                if (res.body.length > 0) {
                    this.pret.client = res.body.pop();
                    this.clientError=false;
                }
                else {
                    this.clientError=true;
                    this.pret.client = {};
                    this.pret.client.candidat = {};
                }
            }
        );
        if(code==="") this.clientError=false;
    }
    save(form: any): boolean {
        this.pret.dateMisePlace = dateToNgb(new Date());
        this.pret.userInitial = this.principal.getLogin();
        this.formDataService.setPret(this.pret);
        this.formDataService.getData().periodType=this.periodType;
        return true;
    }

    goToNext(form: any) {
        if (this.save(form)) {
            this.router.navigate(['/pret-wz-element-financement']);
        }
    }
    computeEndDate(event: any) {
        if (!this.periodType || !this.periodType.label || !this.pret.datePremiereEcheance || !this.pret.nbrEcheance)
            return;
        this.pret.periodicite = this.periodType.label;
        
        let type: string = this.periodType.type;
        let coeff: number = this.periodType.coeff;
        let startDate: Date = ngbToDate(this.pret.datePremiereEcheance);
        let nbrPeriod: number = this.pret.nbrEcheance;
        if(type==="year")
            startDate.setFullYear(startDate.getFullYear() + coeff * nbrPeriod);
        if(type==="month")
            startDate.setMonth(startDate.getMonth() + coeff * nbrPeriod);
        this.pret.dateDerniereEcheance = dateToNgb(startDate);
    }
}
