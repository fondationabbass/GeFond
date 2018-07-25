import { BaseEntity } from '../../shared'

export class Echeance implements BaseEntity {
    constructor(
        public id?: number,
        public dateTombe?: any,
        public montant?: number,
        public etatEcheance?: string,
        public datePayement?: any,
        public dateRetrait?: any,
        public pret?: BaseEntity,
        public mouvements?: BaseEntity[],
    ) {
    }
}
export class PeriodType {
    constructor(
        public coeff?: number,
        public type?: string,
        public label?: string,
    ) {
    }
}