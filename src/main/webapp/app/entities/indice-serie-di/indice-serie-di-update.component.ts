import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IIndiceSerieDi } from 'app/shared/model/indice-serie-di.model';
import { IndiceSerieDiService } from './indice-serie-di.service';

@Component({
    selector: 'jhi-indice-serie-di-update',
    templateUrl: './indice-serie-di-update.component.html'
})
export class IndiceSerieDiUpdateComponent implements OnInit {
    indiceSerieDi: IIndiceSerieDi;
    isSaving: boolean;
    dataDp: any;

    constructor(protected indiceSerieDiService: IndiceSerieDiService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ indiceSerieDi }) => {
            this.indiceSerieDi = indiceSerieDi;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.indiceSerieDi.id !== undefined) {
            this.subscribeToSaveResponse(this.indiceSerieDiService.update(this.indiceSerieDi));
        } else {
            this.subscribeToSaveResponse(this.indiceSerieDiService.create(this.indiceSerieDi));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndiceSerieDi>>) {
        result.subscribe((res: HttpResponse<IIndiceSerieDi>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
