import { BaseEntity } from './../../shared';

export class Entretien implements BaseEntity {
    constructor(
        public id?: number,
        public cadre?: string,
        public resultat?: string,
        public interlocuteur?: string,
        public etat?: string,
        public dateEntretien?: any,
        public candidature?: BaseEntity,
    ) {
    }
}
