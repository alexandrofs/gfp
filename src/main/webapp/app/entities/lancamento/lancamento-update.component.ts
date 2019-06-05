import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILancamento } from 'app/shared/model/lancamento.model';
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

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected lancamentoService: LancamentoService,
        protected contaPagamentoService: ContaPagamentoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lancamento, contaPagamento }) => {
            this.lancamento = lancamento;
            this.lancamento.contaPagamento = this.lancamento.contaPagamento ? this.lancamento.contaPagamento : contaPagamento;
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
        if (this.lancamento.id !== undefined) {
            this.subscribeToSaveResponse(this.lancamentoService.update(this.lancamento));
        } else {
            this.subscribeToSaveResponse(this.lancamentoService.create(this.lancamento));
        }
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
