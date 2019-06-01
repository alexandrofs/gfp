import { Moment } from 'moment';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';

export interface ILancamento {
    id?: number;
    data?: Moment;
    descricao?: string;
    valor?: number;
    usuario?: string;
    contaPagamento?: IContaPagamento;
}

export class Lancamento implements ILancamento {
    constructor(
        public id?: number,
        public data?: Moment,
        public descricao?: string,
        public valor?: number,
        public usuario?: string,
        public contaPagamento?: IContaPagamento
    ) {}
}
