import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICategoriaDespesa } from 'app/shared/model/categoria-despesa.model';
import { CategoriaDespesaService } from './categoria-despesa.service';

@Component({
    selector: 'jhi-categoria-despesa-update',
    templateUrl: './categoria-despesa-update.component.html'
})
export class CategoriaDespesaUpdateComponent implements OnInit {
    categoriaDespesa: ICategoriaDespesa;
    isSaving: boolean;

    constructor(protected categoriaDespesaService: CategoriaDespesaService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ categoriaDespesa }) => {
            this.categoriaDespesa = categoriaDespesa;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.categoriaDespesa.id !== undefined) {
            this.subscribeToSaveResponse(this.categoriaDespesaService.update(this.categoriaDespesa));
        } else {
            this.subscribeToSaveResponse(this.categoriaDespesaService.create(this.categoriaDespesa));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaDespesa>>) {
        result.subscribe((res: HttpResponse<ICategoriaDespesa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
