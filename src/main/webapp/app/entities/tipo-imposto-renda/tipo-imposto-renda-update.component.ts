import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';
import { TipoImpostoRendaService } from './tipo-imposto-renda.service';

@Component({
    selector: 'jhi-tipo-imposto-renda-update',
    templateUrl: './tipo-imposto-renda-update.component.html'
})
export class TipoImpostoRendaUpdateComponent implements OnInit {
    tipoImpostoRenda: ITipoImpostoRenda;
    isSaving: boolean;

    constructor(protected tipoImpostoRendaService: TipoImpostoRendaService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoImpostoRenda }) => {
            this.tipoImpostoRenda = tipoImpostoRenda;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoImpostoRenda.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoImpostoRendaService.update(this.tipoImpostoRenda));
        } else {
            this.subscribeToSaveResponse(this.tipoImpostoRendaService.create(this.tipoImpostoRenda));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoImpostoRenda>>) {
        result.subscribe((res: HttpResponse<ITipoImpostoRenda>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
