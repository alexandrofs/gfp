import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILancamentoCartao } from 'app/shared/model/lancamento-cartao.model';

type EntityResponseType = HttpResponse<ILancamentoCartao>;
type EntityArrayResponseType = HttpResponse<ILancamentoCartao[]>;

@Injectable({ providedIn: 'root' })
export class LancamentoCartaoService {
    public resourceUrl = SERVER_API_URL + 'api/lancamento-cartaos';

    constructor(protected http: HttpClient) {}

    create(lancamentoCartao: ILancamentoCartao): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lancamentoCartao);
        return this.http
            .post<ILancamentoCartao>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(lancamentoCartao: ILancamentoCartao): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lancamentoCartao);
        return this.http
            .put<ILancamentoCartao>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILancamentoCartao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILancamentoCartao[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(lancamentoCartao: ILancamentoCartao): ILancamentoCartao {
        const copy: ILancamentoCartao = Object.assign({}, lancamentoCartao, {
            dataCompra:
                lancamentoCartao.dataCompra != null && lancamentoCartao.dataCompra.isValid()
                    ? lancamentoCartao.dataCompra.format(DATE_FORMAT)
                    : null,
            mesFatura:
                lancamentoCartao.mesFatura != null && lancamentoCartao.mesFatura.isValid()
                    ? lancamentoCartao.mesFatura.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataCompra = res.body.dataCompra != null ? moment(res.body.dataCompra) : null;
            res.body.mesFatura = res.body.mesFatura != null ? moment(res.body.mesFatura) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((lancamentoCartao: ILancamentoCartao) => {
                lancamentoCartao.dataCompra = lancamentoCartao.dataCompra != null ? moment(lancamentoCartao.dataCompra) : null;
                lancamentoCartao.mesFatura = lancamentoCartao.mesFatura != null ? moment(lancamentoCartao.mesFatura) : null;
            });
        }
        return res;
    }
}
