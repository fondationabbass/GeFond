import { BaseEntity } from './../../shared';

export class Projet implements BaseEntity {
    constructor(
        public id?: number,
        public intitule?: string,
        public montEstime?: number,
        public montApp?: number,
        public domaine?: string,
        public type?: string,
        public description?: string,
        public dateCreation?: any,
        public etat?: string,
        public lieu?: string,
        public candidature?: BaseEntity,
    ) {
    }
}
