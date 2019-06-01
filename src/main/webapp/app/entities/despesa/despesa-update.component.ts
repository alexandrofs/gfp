import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDespesa, Despesa } from 'app/shared/model/despesa.model';
import { DespesaService } from './despesa.service';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { ContaPagamentoService } from 'app/entities/conta-pagamento';
import { ICategoriaDespesa } from 'app/shared/model/categoria-despesa.model';
import { CategoriaDespesaService } from 'app/entities/categoria-despesa';

@Component({
    selector: 'jhi-despesa-update',
    templateUrl: './despesa-update.component.html'
})
export class DespesaUpdateComponent implements OnInit {
    despesa: IDespesa;
    isSaving: boolean;

    contapagamentos: IContaPagamento[];

    categoriadespesas: ICategoriaDespesa[];
    dataDespesaDp: any;
    mesReferenciaDp: any;

    editForm = this.fb.group({
        id: [],
        dataDespesa: [null, [Validators.required]],
        mesReferencia: [null, [Validators.required]],
        descricao: [null, [Validators.required]],
        valor: [null, [Validators.required]],
        usuario: [null, [Validators.required]],
        contaPagamento: [null, Validators.required],
        categoriaDespesa: [null, Validators.required]
    });

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected despesaService: DespesaService,
        protected contaPagamentoService: ContaPagamentoService,
        protected categoriaDespesaService: CategoriaDespesaService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ despesa }) => {
            this.updateForm(despesa);
            this.despesa = despesa;
        });
        this.contaPagamentoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IContaPagamento[]>) => mayBeOk.ok),
                map((response: HttpResponse<IContaPagamento[]>) => response.body)
            )
            .subscribe((res: IContaPagamento[]) => (this.contapagamentos = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.categoriaDespesaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICategoriaDespesa[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategoriaDespesa[]>) => response.body)
            )
            .subscribe((res: ICategoriaDespesa[]) => (this.categoriadespesas = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    updateForm(despesa: IDespesa) {
        this.editForm.patchValue({
            id: despesa.id,
            dataDespesa: despesa.dataDespesa,
            mesReferencia: despesa.mesReferencia,
            descricao: despesa.descricao,
            valor: despesa.valor,
            usuario: despesa.usuario,
            contaPagamento: despesa.contaPagamento,
            categoriaDespesa: despesa.categoriaDespesa
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const despesa = this.createFromForm();
        if (despesa.id !== undefined) {
            this.subscribeToSaveResponse(this.despesaService.update(despesa));
        } else {
            this.subscribeToSaveResponse(this.despesaService.create(despesa));
        }
    }

    private createFromForm(): IDespesa {
        const entity = {
            ...new Despesa(),
            id: this.editForm.get(['id']).value,
            dataDespesa: this.editForm.get(['dataDespesa']).value,
            mesReferencia: this.editForm.get(['mesReferencia']).value,
            descricao: this.editForm.get(['descricao']).value,
            valor: this.editForm.get(['valor']).value,
            usuario: this.editForm.get(['usuario']).value,
            contaPagamento: this.editForm.get(['contaPagamento']).value,
            categoriaDespesa: this.editForm.get(['categoriaDespesa']).value
        };
        return entity;
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDespesa>>) {
        result.subscribe((res: HttpResponse<IDespesa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackContaPagamentoById(index: number, item: IContaPagamento) {
        return item.id;
    }

    trackCategoriaDespesaById(index: number, item: ICategoriaDespesa) {
        return item.id;
    }
}
