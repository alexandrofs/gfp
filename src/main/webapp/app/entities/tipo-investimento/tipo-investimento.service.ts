import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoInvestimento } from 'app/shared/model/tipo-investimento.model';

type EntityResponseType = HttpResponse<ITipoInvestimento>;
type EntityArrayResponseType = HttpResponse<ITipoInvestimento[]>;

@Injectable({ providedIn: 'root' })
export class TipoInvestimentoService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-investimentos';

    constructor(protected http: HttpClient) {}

    create(tipoInvestimento: ITipoInvestimento): Observable<EntityResponseType> {
        return this.http.post<ITipoInvestimento>(this.resourceUrl, tipoInvestimento, { observe: 'response' });
    }

    update(tipoInvestimento: ITipoInvestimento): Observable<EntityResponseType> {
        return this.http.put<ITipoInvestimento>(this.resourceUrl, tipoInvestimento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoInvestimento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoInvestimento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
