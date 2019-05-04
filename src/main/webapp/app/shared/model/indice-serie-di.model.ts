import { Moment } from 'moment';

export interface IIndiceSerieDi {
    id?: number;
    data?: Moment;
    taxaMediaAnual?: number;
    taxaSelic?: number;
    fatorDiario?: number;
}

export class IndiceSerieDi implements IIndiceSerieDi {
    constructor(
        public id?: number,
        public data?: Moment,
        public taxaMediaAnual?: number,
        public taxaSelic?: number,
        public fatorDiario?: number
    ) {}
}
