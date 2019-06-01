import { IDespesa } from 'app/shared/model/despesa.model';

export interface ICategoriaDespesa {
    id?: number;
    nome?: string;
    usuario?: string;
    despesas?: IDespesa[];
}

export class CategoriaDespesa implements ICategoriaDespesa {
    constructor(public id?: number, public nome?: string, public usuario?: string, public despesas?: IDespesa[]) {}
}
