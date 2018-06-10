import { BaseEntity } from './../../shared';

export class Chapitre implements BaseEntity {
    constructor(
        public id?: number,
        public libChapitre?: string,
        public categorieCompte?: string,
    ) {
    }
}
