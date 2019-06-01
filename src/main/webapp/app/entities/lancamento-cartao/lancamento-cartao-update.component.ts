import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILancamentoCartao } from 'app/shared/model/lancamento-cartao.model';
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

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected lancamentoCartaoService: LancamentoCartaoService,
        protected contaPagamentoService: ContaPagamentoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lancamentoCartao }) => {
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lancamentoCartao.id !== undefined) {
            this.subscribeToSaveResponse(this.lancamentoCartaoService.update(this.lancamentoCartao));
        } else {
            this.subscribeToSaveResponse(this.lancamentoCartaoService.create(this.lancamentoCartao));
        }
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
