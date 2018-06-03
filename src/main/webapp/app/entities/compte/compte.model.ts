import { BaseEntity } from './../../shared';

export class Compte implements BaseEntity {
    constructor(
        public id?: number,
        public intituleCompte?: string,
        public dateOuverture?: any,
        public solde?: number,
        public dateDernierCredit?: string,
        public dateDernierDebit?: any,
        public client?: BaseEntity,
        public chapitre?: BaseEntity,
    ) {
    }
}
