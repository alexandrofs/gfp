import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

type EntityResponseType = HttpResponse<Map<String, String>>;

@Injectable({ providedIn: 'root' })
export class FileImporterService {
    public resourceUrl = SERVER_API_URL + 'api/importer';

    constructor(protected http: HttpClient) {}

    import(fileToUpload: File, fileImporterType: String): Observable<HttpResponse<any>> {
        const formData: FormData = new FormData();
        formData.append('file', fileToUpload, fileToUpload.name);
        return this.http.post<any>(`${this.resourceUrl}/${fileImporterType}`, formData, { observe: 'response' });
    }
}
