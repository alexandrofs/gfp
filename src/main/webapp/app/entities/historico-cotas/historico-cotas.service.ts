import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHistoricoCotas } from 'app/shared/model/historico-cotas.model';

type EntityResponseType = HttpResponse<IHistoricoCotas>;
type EntityArrayResponseType = HttpResponse<IHistoricoCotas[]>;

@Injectable({ providedIn: 'root' })
export class HistoricoCotasService {
    public resourceUrl = SERVER_API_URL + 'api/historico-cotas';

    constructor(protected http: HttpClient) {}

    create(historicoCotas: IHistoricoCotas): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(historicoCotas);
        return this.http
            .post<IHistoricoCotas>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(historicoCotas: IHistoricoCotas): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(historicoCotas);
        return this.http
            .put<IHistoricoCotas>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHistoricoCotas>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHistoricoCotas[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(historicoCotas: IHistoricoCotas): IHistoricoCotas {
        const copy: IHistoricoCotas = Object.assign({}, historicoCotas, {
            dataCota:
                historicoCotas.dataCota != null && historicoCotas.dataCota.isValid() ? historicoCotas.dataCota.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataCota = res.body.dataCota != null ? moment(res.body.dataCota) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((historicoCotas: IHistoricoCotas) => {
                historicoCotas.dataCota = historicoCotas.dataCota != null ? moment(historicoCotas.dataCota) : null;
            });
        }
        return res;
    }
}
