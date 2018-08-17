import { BaseEntity, User } from './../../shared';

export class Caisse implements BaseEntity {
    constructor(
        public id?: number,
        public intituleCaisse?: string,
        public dateOuverture?: any,
        public user?: User,
    ) {
    }
}
