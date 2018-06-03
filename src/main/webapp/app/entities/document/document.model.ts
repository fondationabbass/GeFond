import { BaseEntity } from './../../shared';

export class Document implements BaseEntity {
    constructor(
        public id?: number,
        public dateEnreg?: any,
        public lib?: string,
        public typeDocument?: string,
        public module?: string,
        public etat?: string,
        public fichier?: string,
        public tail?: string,
        public candidature?: BaseEntity,
    ) {
    }
}
