import { BaseEntity } from '../../shared';
import { Candidat } from '../candidat/candidat.model';

export class Candidature implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public status?: string,
        public session?: BaseEntity,
        public candidat?: Candidat,
    ) {
    }
}
