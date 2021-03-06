import { BaseEntity } from '../../shared';
import {Candidat} from '../candidat';

export class Client implements BaseEntity {
    constructor(
        public id?: number,
        public code?:string,
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
        public candidat?: Candidat,
    ) {
    }
}
