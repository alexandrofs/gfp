import { Moment } from 'moment';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';

export interface ILancamentoCartao {
    id?: number;
    dataCompra?: Moment;
    mesFatura?: Moment;
    descricao?: string;
    valor?: number;
    usuario?: string;
    contaPagamento?: IContaPagamento;
}

export class LancamentoCartao implements ILancamentoCartao {
    constructor(
        public id?: number,
        public dataCompra?: Moment,
        public mesFatura?: Moment,
        public descricao?: string,
        public valor?: number,
        public usuario?: string,
        public contaPagamento?: IContaPagamento
    ) {}
}
