import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoInvestimento } from 'app/shared/model/tipo-investimento.model';

type EntityResponseType = HttpResponse<Map<String, String>>;

@Injectable({ providedIn: 'root' })
export class ModalidadeService {
    public resourceUrl = SERVER_API_URL + 'api/modalidades';

    constructor(protected http: HttpClient) {}

    query(req?: any): Observable<EntityResponseType> {
        const options = createRequestOption(req);
        return this.http.get<Map<string, string>>(this.resourceUrl, { params: options, observe: 'response' });
    }
}
