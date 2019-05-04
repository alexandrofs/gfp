import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInvestimento } from 'app/shared/model/investimento.model';

type EntityResponseType = HttpResponse<IInvestimento>;
type EntityArrayResponseType = HttpResponse<IInvestimento[]>;

@Injectable({ providedIn: 'root' })
export class InvestimentoService {
    public resourceUrl = SERVER_API_URL + 'api/investimentos';

    constructor(protected http: HttpClient) {}

    create(investimento: IInvestimento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(investimento);
        return this.http
            .post<IInvestimento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(investimento: IInvestimento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(investimento);
        return this.http
            .put<IInvestimento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IInvestimento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IInvestimento[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(investimento: IInvestimento): IInvestimento {
        const copy: IInvestimento = Object.assign({}, investimento, {
            dataAplicacao:
                investimento.dataAplicacao != null && investimento.dataAplicacao.isValid()
                    ? investimento.dataAplicacao.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataAplicacao = res.body.dataAplicacao != null ? moment(res.body.dataAplicacao) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((investimento: IInvestimento) => {
                investimento.dataAplicacao = investimento.dataAplicacao != null ? moment(investimento.dataAplicacao) : null;
            });
        }
        return res;
    }
}
