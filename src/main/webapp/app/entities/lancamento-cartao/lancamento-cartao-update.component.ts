import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILancamentoCartao, LancamentoCartao } from 'app/shared/model/lancamento-cartao.model';
import { LancamentoCartaoService } from './lancamento-cartao.service';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { ContaPagamentoService } from 'app/entities/conta-pagamento';

@Component({
    selector: 'jhi-lancamento-cartao-update',
    templateUrl: './lancamento-cartao-update.component.html'
})
export class LancamentoCartaoUpdateComponent implements OnInit {
    lancamentoCartao: ILancamentoCartao;
    isSaving: boolean;

    contapagamentos: IContaPagamento[];
    dataCompraDp: any;
    mesFaturaDp: any;

    editForm = this.fb.group({
        id: [],
        dataCompra: [null, [Validators.required]],
        mesFatura: [null, [Validators.required]],
        descricao: [null, [Validators.required]],
        valor: [null, [Validators.required]],
        usuario: [null, [Validators.required]],
        contaPagamento: [null, Validators.required]
    });

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected lancamentoCartaoService: LancamentoCartaoService,
        protected contaPagamentoService: ContaPagamentoService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lancamentoCartao }) => {
            this.updateForm(lancamentoCartao);
            this.lancamentoCartao = lancamentoCartao;
        });
        this.contaPagamentoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IContaPagamento[]>) => mayBeOk.ok),
                map((response: HttpResponse<IContaPagamento[]>) => response.body)
            )
            .subscribe((res: IContaPagamento[]) => (this.contapagamentos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    updateForm(lancamentoCartao: ILancamentoCartao) {
        this.editForm.patchValue({
            id: lancamentoCartao.id,
            dataCompra: lancamentoCartao.dataCompra,
            mesFatura: lancamentoCartao.mesFatura,
            descricao: lancamentoCartao.descricao,
            valor: lancamentoCartao.valor,
            usuario: lancamentoCartao.usuario,
            contaPagamento: lancamentoCartao.contaPagamento
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const lancamentoCartao = this.createFromForm();
        if (lancamentoCartao.id !== undefined) {
            this.subscribeToSaveResponse(this.lancamentoCartaoService.update(lancamentoCartao));
        } else {
            this.subscribeToSaveResponse(this.lancamentoCartaoService.create(lancamentoCartao));
        }
    }

    private createFromForm(): ILancamentoCartao {
        const entity = {
            ...new LancamentoCartao(),
            id: this.editForm.get(['id']).value,
            dataCompra: this.editForm.get(['dataCompra']).value,
            mesFatura: this.editForm.get(['mesFatura']).value,
            descricao: this.editForm.get(['descricao']).value,
            valor: this.editForm.get(['valor']).value,
            usuario: this.editForm.get(['usuario']).value,
            contaPagamento: this.editForm.get(['contaPagamento']).value
        };
        return entity;
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILancamentoCartao>>) {
        result.subscribe((res: HttpResponse<ILancamentoCartao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
