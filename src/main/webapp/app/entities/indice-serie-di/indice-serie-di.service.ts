import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIndiceSerieDi } from 'app/shared/model/indice-serie-di.model';

type EntityResponseType = HttpResponse<IIndiceSerieDi>;
type EntityArrayResponseType = HttpResponse<IIndiceSerieDi[]>;

@Injectable({ providedIn: 'root' })
export class IndiceSerieDiService {
    public resourceUrl = SERVER_API_URL + 'api/indice-serie-dis';

    constructor(protected http: HttpClient) {}

    create(indiceSerieDi: IIndiceSerieDi): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(indiceSerieDi);
        return this.http
            .post<IIndiceSerieDi>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(indiceSerieDi: IIndiceSerieDi): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(indiceSerieDi);
        return this.http
            .put<IIndiceSerieDi>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IIndiceSerieDi>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IIndiceSerieDi[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(indiceSerieDi: IIndiceSerieDi): IIndiceSerieDi {
        const copy: IIndiceSerieDi = Object.assign({}, indiceSerieDi, {
            data: indiceSerieDi.data != null && indiceSerieDi.data.isValid() ? indiceSerieDi.data.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.data = res.body.data != null ? moment(res.body.data) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((indiceSerieDi: IIndiceSerieDi) => {
                indiceSerieDi.data = indiceSerieDi.data != null ? moment(indiceSerieDi.data) : null;
            });
        }
        return res;
    }
}
