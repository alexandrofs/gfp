import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { AccountService } from 'app/core';
import { ContaPagamentoService } from './conta-pagamento.service';

@Component({
    selector: 'jhi-conta-pagamento',
    templateUrl: './conta-pagamento.component.html'
})
export class ContaPagamentoComponent implements OnInit, OnDestroy {
    contaPagamentos: IContaPagamento[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected contaPagamentoService: ContaPagamentoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.contaPagamentoService
            .query()
            .pipe(
                filter((res: HttpResponse<IContaPagamento[]>) => res.ok),
                map((res: HttpResponse<IContaPagamento[]>) => res.body)
            )
            .subscribe(
                (res: IContaPagamento[]) => {
                    this.contaPagamentos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInContaPagamentos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IContaPagamento) {
        return item.id;
    }

    registerChangeInContaPagamentos() {
        this.eventSubscriber = this.eventManager.subscribe('contaPagamentoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
