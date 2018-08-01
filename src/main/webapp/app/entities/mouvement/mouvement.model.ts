import { BaseEntity } from '../../shared';

export class Mouvement implements BaseEntity {
    constructor(
        public id?: number,
        public dateMvt?: any,
        public pret?: any,
        public lib?: string,
        public montant?: number,
        public sens?: string,
        public etat?: string,
        public compte?: BaseEntity,
        public echeances?: BaseEntity[],
    ) {
    }
}
