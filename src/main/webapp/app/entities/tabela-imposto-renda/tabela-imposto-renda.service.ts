import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITabelaImpostoRenda } from 'app/shared/model/tabela-imposto-renda.model';

type EntityResponseType = HttpResponse<ITabelaImpostoRenda>;
type EntityArrayResponseType = HttpResponse<ITabelaImpostoRenda[]>;

@Injectable({ providedIn: 'root' })
export class TabelaImpostoRendaService {
    public resourceUrl = SERVER_API_URL + 'api/tabela-imposto-rendas';

    constructor(protected http: HttpClient) {}

    create(tabelaImpostoRenda: ITabelaImpostoRenda): Observable<EntityResponseType> {
        return this.http.post<ITabelaImpostoRenda>(this.resourceUrl, tabelaImpostoRenda, { observe: 'response' });
    }

    update(tabelaImpostoRenda: ITabelaImpostoRenda): Observable<EntityResponseType> {
        return this.http.put<ITabelaImpostoRenda>(this.resourceUrl, tabelaImpostoRenda, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITabelaImpostoRenda>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITabelaImpostoRenda[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
