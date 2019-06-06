import { Moment } from 'moment';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { ICategoriaDespesa } from 'app/shared/model/categoria-despesa.model';

export interface IDespesa {
    id?: number;
    dataDespesa?: Moment;
    mesReferencia?: Moment;
    descricao?: string;
    parcela?: number;
    quantidadeParcelas?: number;
    valor?: number;
    usuario?: string;
    contaPagamento?: IContaPagamento;
    categoriaDespesa?: ICategoriaDespesa;
}

export class Despesa implements IDespesa {
    constructor(
        public id?: number,
        public dataDespesa?: Moment,
        public mesReferencia?: Moment,
        public descricao?: string,
        public parcela?: number,
        public quantidadeParcelas?: number,
        public valor?: number,
        public usuario?: string,
        public contaPagamento?: IContaPagamento,
        public categoriaDespesa?: ICategoriaDespesa
    ) {}
}
