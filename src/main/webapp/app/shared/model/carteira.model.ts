import { IInvestimento } from 'app/shared/model/investimento.model';

export interface ICarteira {
    id?: number;
    nome?: string;
    descricao?: string;
    investimentos?: IInvestimento[];
}

export class Carteira implements ICarteira {
    constructor(public id?: number, public nome?: string, public descricao?: string, public investimentos?: IInvestimento[]) {}
}
