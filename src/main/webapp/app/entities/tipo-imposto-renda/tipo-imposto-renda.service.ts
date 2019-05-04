import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';

type EntityResponseType = HttpResponse<ITipoImpostoRenda>;
type EntityArrayResponseType = HttpResponse<ITipoImpostoRenda[]>;

@Injectable({ providedIn: 'root' })
export class TipoImpostoRendaService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-imposto-rendas';

    constructor(protected http: HttpClient) {}

    create(tipoImpostoRenda: ITipoImpostoRenda): Observable<EntityResponseType> {
        return this.http.post<ITipoImpostoRenda>(this.resourceUrl, tipoImpostoRenda, { observe: 'response' });
    }

    update(tipoImpostoRenda: ITipoImpostoRenda): Observable<EntityResponseType> {
        return this.http.put<ITipoImpostoRenda>(this.resourceUrl, tipoImpostoRenda, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoImpostoRenda>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoImpostoRenda[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
