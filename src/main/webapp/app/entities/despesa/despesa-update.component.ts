import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDespesa } from 'app/shared/model/despesa.model';
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

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected despesaService: DespesaService,
        protected contaPagamentoService: ContaPagamentoService,
        protected categoriaDespesaService: CategoriaDespesaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ despesa }) => {
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.despesa.id !== undefined) {
            this.subscribeToSaveResponse(this.despesaService.update(this.despesa));
        } else {
            this.subscribeToSaveResponse(this.despesaService.create(this.despesa));
        }
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
