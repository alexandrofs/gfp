import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILancamento, Lancamento } from 'app/shared/model/lancamento.model';
import { LancamentoService } from './lancamento.service';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { ContaPagamentoService } from 'app/entities/conta-pagamento';

@Component({
    selector: 'jhi-lancamento-update',
    templateUrl: './lancamento-update.component.html'
})
export class LancamentoUpdateComponent implements OnInit {
    lancamento: ILancamento;
    isSaving: boolean;

    contapagamentos: IContaPagamento[];
    dataDp: any;

    editForm = this.fb.group({
        id: [],
        data: [null, [Validators.required]],
        descricao: [null, [Validators.required]],
        valor: [null, [Validators.required]],
        usuario: [null, [Validators.required]],
        contaPagamento: [null, Validators.required]
    });

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected lancamentoService: LancamentoService,
        protected contaPagamentoService: ContaPagamentoService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lancamento }) => {
            this.updateForm(lancamento);
            this.lancamento = lancamento;
        });
        this.contaPagamentoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IContaPagamento[]>) => mayBeOk.ok),
                map((response: HttpResponse<IContaPagamento[]>) => response.body)
            )
            .subscribe((res: IContaPagamento[]) => (this.contapagamentos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    updateForm(lancamento: ILancamento) {
        this.editForm.patchValue({
            id: lancamento.id,
            data: lancamento.data,
            descricao: lancamento.descricao,
            valor: lancamento.valor,
            usuario: lancamento.usuario,
            contaPagamento: lancamento.contaPagamento
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const lancamento = this.createFromForm();
        if (lancamento.id !== undefined) {
            this.subscribeToSaveResponse(this.lancamentoService.update(lancamento));
        } else {
            this.subscribeToSaveResponse(this.lancamentoService.create(lancamento));
        }
    }

    private createFromForm(): ILancamento {
        const entity = {
            ...new Lancamento(),
            id: this.editForm.get(['id']).value,
            data: this.editForm.get(['data']).value,
            descricao: this.editForm.get(['descricao']).value,
            valor: this.editForm.get(['valor']).value,
            usuario: this.editForm.get(['usuario']).value,
            contaPagamento: this.editForm.get(['contaPagamento']).value
        };
        return entity;
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILancamento>>) {
        result.subscribe((res: HttpResponse<ILancamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
