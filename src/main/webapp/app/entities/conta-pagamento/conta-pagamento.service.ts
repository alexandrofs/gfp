import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';

type EntityResponseType = HttpResponse<IContaPagamento>;
type EntityArrayResponseType = HttpResponse<IContaPagamento[]>;

@Injectable({ providedIn: 'root' })
export class ContaPagamentoService {
    public resourceUrl = SERVER_API_URL + 'api/conta-pagamentos';

    constructor(protected http: HttpClient) {}

    create(contaPagamento: IContaPagamento): Observable<EntityResponseType> {
        return this.http.post<IContaPagamento>(this.resourceUrl, contaPagamento, { observe: 'response' });
    }

    update(contaPagamento: IContaPagamento): Observable<EntityResponseType> {
        return this.http.put<IContaPagamento>(this.resourceUrl, contaPagamento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IContaPagamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IContaPagamento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
