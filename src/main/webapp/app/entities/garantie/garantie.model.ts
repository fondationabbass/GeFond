import { BaseEntity } from './../../shared';

export class Garantie implements BaseEntity {
    constructor(
        public id?: number,
        public typeGar?: string,
        public montantEvalue?: number,
        public montantAfect?: number,
        public dateDepot?: any,
        public numDocument?: string,
        public etat?: string,
        public dateRetrait?: any,
        public pret?: BaseEntity,
    ) {
    }
}
