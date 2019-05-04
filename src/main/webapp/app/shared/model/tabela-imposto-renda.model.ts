import { ITipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';

export interface ITabelaImpostoRenda {
    id?: number;
    numDias?: number;
    pctAliquota?: number;
    tipoImpostoRenda?: ITipoImpostoRenda;
}

export class TabelaImpostoRenda implements ITabelaImpostoRenda {
    constructor(public id?: number, public numDias?: number, public pctAliquota?: number, public tipoImpostoRenda?: ITipoImpostoRenda) {}
}
