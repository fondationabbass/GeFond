import { Pret } from "./pret.model";
import { Echeance} from "../echeance";
import { Garantie} from "../garantie";
import { ElementFinancement } from "../element-financement"

export class PretWzFormData {
    pret: Pret = {};
    echeances : Echeance[]=[];
    garanties: Garantie[]=[];
    elementFinancements: ElementFinancement[]=[];
}
