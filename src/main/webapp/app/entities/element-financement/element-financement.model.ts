import { BaseEntity } from './../../shared';

export class ElementFinancement implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public montant?: number,
        public dateFinancement?: any,
        public pret?: BaseEntity,
    ) {
    }
}
