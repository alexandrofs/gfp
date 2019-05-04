import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInstituicao } from 'app/shared/model/instituicao.model';

type EntityResponseType = HttpResponse<IInstituicao>;
type EntityArrayResponseType = HttpResponse<IInstituicao[]>;

@Injectable({ providedIn: 'root' })
export class InstituicaoService {
    public resourceUrl = SERVER_API_URL + 'api/instituicaos';

    constructor(protected http: HttpClient) {}

    create(instituicao: IInstituicao): Observable<EntityResponseType> {
        return this.http.post<IInstituicao>(this.resourceUrl, instituicao, { observe: 'response' });
    }

    update(instituicao: IInstituicao): Observable<EntityResponseType> {
        return this.http.put<IInstituicao>(this.resourceUrl, instituicao, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IInstituicao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInstituicao[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
