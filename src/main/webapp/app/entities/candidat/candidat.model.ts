import { BaseEntity } from './../../shared';

export class Candidat implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public dateNaissance?: any,
        public lieuNaissance?: string,
        public adresse?: string,
        public tel?: string,
        public situation?: string,
    ) {
    }
}
