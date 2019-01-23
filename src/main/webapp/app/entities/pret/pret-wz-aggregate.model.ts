import { Pret } from './pret.model';
import { Echeance, PeriodType} from '../echeance';
import { Garantie} from '../garantie';
import { ElementFinancement } from '../element-financement'

export class PretAggregate {
    pret: Pret = new Pret();
    periodType: PeriodType;
    echeances: Echeance[] = [];
    garanties: Garantie[] = [];
    elementFinancements: ElementFinancement[] = [];
}
