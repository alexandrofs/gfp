import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICarteira } from 'app/shared/model/carteira.model';

type EntityResponseType = HttpResponse<ICarteira>;
type EntityArrayResponseType = HttpResponse<ICarteira[]>;

@Injectable({ providedIn: 'root' })
export class CarteiraService {
    public resourceUrl = SERVER_API_URL + 'api/carteiras';

    constructor(protected http: HttpClient) {}

    create(carteira: ICarteira): Observable<EntityResponseType> {
        return this.http.post<ICarteira>(this.resourceUrl, carteira, { observe: 'response' });
    }

    update(carteira: ICarteira): Observable<EntityResponseType> {
        return this.http.put<ICarteira>(this.resourceUrl, carteira, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICarteira>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICarteira[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
