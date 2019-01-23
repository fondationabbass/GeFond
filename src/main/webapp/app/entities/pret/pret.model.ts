import { BaseEntity } from '../../shared';
import { Client } from '../client';
import { ngbToDate, dateToNgb } from '../../shared/model/format-utils';

export class Pret implements BaseEntity {
    constructor(
        public id?: number,
        public typPret?: string,
        public montAaccord?: number,
        public montDebloq?: number,
        public nbrEcheance?: number,
        public periodicite?: string,
        public dateMisePlace?: any,
        public datePremiereEcheance?: any,
        public dateDerniereEcheance?: any,
        public dateDernierDebloq?: any,
        public etat?: string,
        public encours?: number,
        public userInitial?: string,
        public userDecideur?: string,
        public userDebloq?: string,
        public client?: Client,
    ) {
    }
    asDate() {
        this.dateMisePlace = ngbToDate(this.dateMisePlace);
        this.datePremiereEcheance = ngbToDate(this.datePremiereEcheance);
        this.dateDerniereEcheance = ngbToDate(this.dateDerniereEcheance);
        this.dateDernierDebloq = ngbToDate(this.dateDernierDebloq);
    }
    asNgb() {
        this.dateMisePlace = dateToNgb(this.dateMisePlace);
        this.datePremiereEcheance = dateToNgb(this.datePremiereEcheance);
        this.dateDerniereEcheance = dateToNgb(this.dateDerniereEcheance);
        this.dateDernierDebloq = dateToNgb(this.dateDernierDebloq);
    }
}
