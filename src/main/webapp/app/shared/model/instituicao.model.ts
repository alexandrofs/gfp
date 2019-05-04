export interface IInstituicao {
    id?: number;
    nome?: string;
}

export class Instituicao implements IInstituicao {
    constructor(public id?: number, public nome?: string) {}
}
