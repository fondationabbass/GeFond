import { BaseEntity } from './../../shared';

export class Candidature implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public status?: string,
        public session?: BaseEntity,
        public candidat?: BaseEntity,
    ) {
    }
}
