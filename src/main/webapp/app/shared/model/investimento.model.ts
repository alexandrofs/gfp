import { Moment } from 'moment';
import { ICarteira } from 'app/shared/model/carteira.model';
import { ITipoInvestimento } from 'app/shared/model/tipo-investimento.model';
import { IHistoricoCotas } from 'app/shared/model/historico-cotas.model';
import { IInstituicao } from 'app/shared/model/instituicao.model';

export interface IInvestimento {
    id?: number;
    dataAplicacao?: Moment;
    qtdeCota?: number;
    vlrCota?: number;
    pctPrePosFixado?: number;
    carteira?: ICarteira;
    tipoInvestimento?: ITipoInvestimento;
    historicoCotas?: IHistoricoCotas[];
    instituicao?: IInstituicao;
    vlrSaldoLiquido?: number;
    vlrSaldoBruto?: number;
    vlrRendLiquido?: number;
}

export class Investimento implements IInvestimento {
    constructor(
        public id?: number,
        public dataAplicacao?: Moment,
        public qtdeCota?: number,
        public vlrCota?: number,
        public pctPrePosFixado?: number,
        public carteira?: ICarteira,
        public tipoInvestimento?: ITipoInvestimento,
        public historicoCotas?: IHistoricoCotas[],
        public instituicao?: IInstituicao,
        public vlrSaldoLiquido?: number,
        public vlrSaldoBruto?: number,
        public vlrRendLiquido?: number
    ) {}
}
