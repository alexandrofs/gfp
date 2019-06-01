import { ILancamento } from 'app/shared/model/lancamento.model';
import { ILancamentoCartao } from 'app/shared/model/lancamento-cartao.model';
import { IDespesa } from 'app/shared/model/despesa.model';

export const enum TipoConta {
    CARTAO_CREDITO = 'CARTAO_CREDITO',
    BANCO = 'BANCO',
    VIRTUAL = 'VIRTUAL'
}

export interface IContaPagamento {
    id?: number;
    nome?: string;
    tipoConta?: TipoConta;
    usuario?: string;
    lancamentos?: ILancamento[];
    lancamentoCartaos?: ILancamentoCartao[];
    despesas?: IDespesa[];
}

export class ContaPagamento implements IContaPagamento {
    constructor(
        public id?: number,
        public nome?: string,
        public tipoConta?: TipoConta,
        public usuario?: string,
        public lancamentos?: ILancamento[],
        public lancamentoCartaos?: ILancamentoCartao[],
        public despesas?: IDespesa[]
    ) {}
}
