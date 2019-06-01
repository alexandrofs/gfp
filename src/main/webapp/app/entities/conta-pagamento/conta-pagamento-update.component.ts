import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { ContaPagamentoService } from './conta-pagamento.service';

@Component({
    selector: 'jhi-conta-pagamento-update',
    templateUrl: './conta-pagamento-update.component.html'
})
export class ContaPagamentoUpdateComponent implements OnInit {
    contaPagamento: IContaPagamento;
    isSaving: boolean;

    constructor(protected contaPagamentoService: ContaPagamentoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contaPagamento }) => {
            this.contaPagamento = contaPagamento;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.contaPagamento.id !== undefined) {
            this.subscribeToSaveResponse(this.contaPagamentoService.update(this.contaPagamento));
        } else {
            this.subscribeToSaveResponse(this.contaPagamentoService.create(this.contaPagamento));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContaPagamento>>) {
        result.subscribe((res: HttpResponse<IContaPagamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
