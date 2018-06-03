import { BaseEntity } from './../../shared';

export class SessionProjet implements BaseEntity {
    constructor(
        public id?: number,
        public dateOuvert?: any,
        public dateFermeture?: any,
        public plafondFinance?: number,
        public nombreClient?: number,
        public plafondClient?: number,
        public dateCreat?: string,
        public dateMaj?: string,
        public etat?: string,
    ) {
    }
}
