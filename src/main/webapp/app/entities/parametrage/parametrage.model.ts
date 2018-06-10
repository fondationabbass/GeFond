import { BaseEntity } from './../../shared';

export class Parametrage implements BaseEntity {
    constructor(
        public id?: number,
        public codeTypeParam?: string,
        public codeParam?: string,
        public libelle?: string,
        public lib1?: string,
        public lib2?: string,
        public lib3?: string,
        public mnt1?: number,
        public mnt2?: number,
        public mnt3?: number,
    ) {
    }
}
