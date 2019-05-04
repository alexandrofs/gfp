import { ITipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';

export interface ITipoInvestimento {
    id?: number;
    nome?: string;
    descricao?: string;
    modalidade?: string;
    tipoIndexador?: string;
    indice?: string;
    tipoImpostoRenda?: ITipoImpostoRenda;
}

export class TipoInvestimento implements ITipoInvestimento {
    constructor(
        public id?: number,
        public nome?: string,
        public descricao?: string,
        public modalidade?: string,
        public tipoIndexador?: string,
        public indice?: string,
        public tipoImpostoRenda?: ITipoImpostoRenda
    ) {}
}
