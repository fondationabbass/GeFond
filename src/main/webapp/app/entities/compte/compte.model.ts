import { BaseEntity } from './../../shared';

export class Compte implements BaseEntity {
    constructor(
        public id?: number,
        public intituleCompte?: string,
        public numCompte?: string,
        public typeCompte?: string,
        public dateOuverture?: any,
        public solde?: number,
        public dateDernierCredit?: any,
        public dateDernierDebit?: any,
        public client?: BaseEntity,
        public pret?: BaseEntity,
        public caisse?: BaseEntity,
        public chapitre?: BaseEntity,
    ) {
    }
}
