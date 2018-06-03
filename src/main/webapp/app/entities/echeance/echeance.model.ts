import { BaseEntity } from './../../shared';

export class Echeance implements BaseEntity {
    constructor(
        public id?: number,
        public dateTombe?: any,
        public montant?: number,
        public etatEcheance?: string,
        public datePayement?: any,
        public dateRetrait?: any,
        public pret?: BaseEntity,
    ) {
    }
}
