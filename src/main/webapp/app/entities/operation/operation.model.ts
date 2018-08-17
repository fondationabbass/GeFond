import { BaseEntity } from './../../shared';

export class Operation implements BaseEntity {
    constructor(
        public id?: number,
        public dateOperation?: any,
        public typeOperation?: string,
        public montant?: number,
        public etat?: string,
        public moyenPaiement?: string,
        public commentaire?: string,
        public description?: string,
        public compteOrigin?: BaseEntity,
        public compteDestinataire?: BaseEntity,
        public pret?: BaseEntity,
        public caisse?: BaseEntity,
        public echeances?: BaseEntity[],
    ) {
    }
}
