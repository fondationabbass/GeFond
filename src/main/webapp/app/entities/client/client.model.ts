import { BaseEntity } from './../../shared';

export class Client implements BaseEntity {
    constructor(
        public id?: number,
        public dateCreat?: any,
        public lieuResid?: string,
        public typeResid?: string,
        public arrondResid?: string,
        public nomPersonneContact?: string,
        public telPersonneContact?: string,
        public adressPersonneContact?: string,
        public typeClient?: string,
        public pointsFidel?: number,
        public dateMaj?: any,
        public candidat?: BaseEntity,
    ) {
    }
}
