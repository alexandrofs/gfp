import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICategoriaDespesa, CategoriaDespesa } from 'app/shared/model/categoria-despesa.model';
import { CategoriaDespesaService } from './categoria-despesa.service';

@Component({
    selector: 'jhi-categoria-despesa-update',
    templateUrl: './categoria-despesa-update.component.html'
})
export class CategoriaDespesaUpdateComponent implements OnInit {
    categoriaDespesa: ICategoriaDespesa;
    isSaving: boolean;

    editForm = this.fb.group({
        id: [],
        nome: [null, [Validators.required]],
        usuario: [null, [Validators.required]]
    });

    constructor(
        protected categoriaDespesaService: CategoriaDespesaService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ categoriaDespesa }) => {
            this.updateForm(categoriaDespesa);
            this.categoriaDespesa = categoriaDespesa;
        });
    }

    updateForm(categoriaDespesa: ICategoriaDespesa) {
        this.editForm.patchValue({
            id: categoriaDespesa.id,
            nome: categoriaDespesa.nome,
            usuario: categoriaDespesa.usuario
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const categoriaDespesa = this.createFromForm();
        if (categoriaDespesa.id !== undefined) {
            this.subscribeToSaveResponse(this.categoriaDespesaService.update(categoriaDespesa));
        } else {
            this.subscribeToSaveResponse(this.categoriaDespesaService.create(categoriaDespesa));
        }
    }

    private createFromForm(): ICategoriaDespesa {
        const entity = {
            ...new CategoriaDespesa(),
            id: this.editForm.get(['id']).value,
            nome: this.editForm.get(['nome']).value,
            usuario: this.editForm.get(['usuario']).value
        };
        return entity;
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
