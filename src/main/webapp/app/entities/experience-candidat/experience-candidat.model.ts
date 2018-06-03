import { BaseEntity } from './../../shared';

export class ExperienceCandidat implements BaseEntity {
    constructor(
        public id?: number,
        public typeInfo?: string,
        public titre?: string,
        public etab?: string,
        public adressEtab?: string,
        public dateDeb?: any,
        public dateFin?: any,
        public candidat?: BaseEntity,
    ) {
    }
}
