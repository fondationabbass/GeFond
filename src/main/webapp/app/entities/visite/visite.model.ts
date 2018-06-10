import { BaseEntity } from './../../shared';

export class Visite implements BaseEntity {
    constructor(
        public id?: number,
        public lieuVisite?: string,
        public dateVisite?: any,
        public persRencontre?: number,
        public cadreVisite?: string,
        public etatLieu?: string,
        public visiteur?: string,
        public etat?: string,
        public recomendation?: string,
        public rapport?: string,
        public candidature?: BaseEntity,
    ) {
    }
}
