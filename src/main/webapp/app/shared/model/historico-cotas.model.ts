import { Moment } from 'moment';
import { IInvestimento } from 'app/shared/model/investimento.model';

export interface IHistoricoCotas {
    id?: number;
    dataCota?: Moment;
    vlrCota?: number;
    investimento?: IInvestimento;
}

export class HistoricoCotas implements IHistoricoCotas {
    constructor(public id?: number, public dataCota?: Moment, public vlrCota?: number, public investimento?: IInvestimento) {}
}
