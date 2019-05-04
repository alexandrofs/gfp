import { ITabelaImpostoRenda } from 'app/shared/model/tabela-imposto-renda.model';

export interface ITipoImpostoRenda {
    id?: number;
    codigo?: string;
    descricao?: string;
    tabelaImpostoRendas?: ITabelaImpostoRenda[];
}

export class TipoImpostoRenda implements ITipoImpostoRenda {
    constructor(
        public id?: number,
        public codigo?: string,
        public descricao?: string,
        public tabelaImpostoRendas?: ITabelaImpostoRenda[]
    ) {}
}
