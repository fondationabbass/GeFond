import { Pret } from "./pret.model";
import { Echeance, PeriodType} from "../echeance";
import { Garantie} from "../garantie";
import { ElementFinancement } from "../element-financement"

export class PretWzFormData {
    pret: Pret = {};
    periodType: PeriodType;
    echeances : Echeance[]=[];
    garanties: Garantie[]=[];
    elementFinancements: ElementFinancement[]=[];
    unblockPret: boolean = false;
    validatePret: boolean = false;
}
