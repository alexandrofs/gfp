import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICategoriaDespesa } from 'app/shared/model/categoria-despesa.model';

type EntityResponseType = HttpResponse<ICategoriaDespesa>;
type EntityArrayResponseType = HttpResponse<ICategoriaDespesa[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaDespesaService {
    public resourceUrl = SERVER_API_URL + 'api/categoria-despesas';

    constructor(protected http: HttpClient) {}

    create(categoriaDespesa: ICategoriaDespesa): Observable<EntityResponseType> {
        return this.http.post<ICategoriaDespesa>(this.resourceUrl, categoriaDespesa, { observe: 'response' });
    }

    update(categoriaDespesa: ICategoriaDespesa): Observable<EntityResponseType> {
        return this.http.put<ICategoriaDespesa>(this.resourceUrl, categoriaDespesa, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICategoriaDespesa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICategoriaDespesa[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
