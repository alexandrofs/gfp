import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDespesa } from 'app/shared/model/despesa.model';

type EntityResponseType = HttpResponse<IDespesa>;
type EntityArrayResponseType = HttpResponse<IDespesa[]>;

@Injectable({ providedIn: 'root' })
export class DespesaService {
    public resourceUrl = SERVER_API_URL + 'api/despesas';

    constructor(protected http: HttpClient) {}

    create(despesa: IDespesa): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(despesa);
        return this.http
            .post<IDespesa>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(despesa: IDespesa): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(despesa);
        return this.http
            .put<IDespesa>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDespesa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDespesa[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(despesa: IDespesa): IDespesa {
        const copy: IDespesa = Object.assign({}, despesa, {
            dataDespesa: despesa.dataDespesa != null && despesa.dataDespesa.isValid() ? despesa.dataDespesa.format(DATE_FORMAT) : null,
            mesReferencia:
                despesa.mesReferencia != null && despesa.mesReferencia.isValid() ? despesa.mesReferencia.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataDespesa = res.body.dataDespesa != null ? moment(res.body.dataDespesa) : null;
            res.body.mesReferencia = res.body.mesReferencia != null ? moment(res.body.mesReferencia) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((despesa: IDespesa) => {
                despesa.dataDespesa = despesa.dataDespesa != null ? moment(despesa.dataDespesa) : null;
                despesa.mesReferencia = despesa.mesReferencia != null ? moment(despesa.mesReferencia) : null;
            });
        }
        return res;
    }
}
