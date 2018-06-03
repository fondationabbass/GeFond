import { BaseEntity } from './../../shared';

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
        public client?: BaseEntity,
    ) {
    }
}
