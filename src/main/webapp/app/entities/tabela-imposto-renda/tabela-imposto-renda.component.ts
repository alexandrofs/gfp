import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITabelaImpostoRenda } from 'app/shared/model/tabela-imposto-renda.model';
import { AccountService } from 'app/core';
import { TabelaImpostoRendaService } from './tabela-imposto-renda.service';

@Component({
    selector: 'jhi-tabela-imposto-renda',
    templateUrl: './tabela-imposto-renda.component.html'
})
export class TabelaImpostoRendaComponent implements OnInit, OnDestroy {
    tabelaImpostoRendas: ITabelaImpostoRenda[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected tabelaImpostoRendaService: TabelaImpostoRendaService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.tabelaImpostoRendaService
            .query()
            .pipe(
                filter((res: HttpResponse<ITabelaImpostoRenda[]>) => res.ok),
                map((res: HttpResponse<ITabelaImpostoRenda[]>) => res.body)
            )
            .subscribe(
                (res: ITabelaImpostoRenda[]) => {
                    this.tabelaImpostoRendas = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTabelaImpostoRendas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITabelaImpostoRenda) {
        return item.id;
    }

    registerChangeInTabelaImpostoRendas() {
        this.eventSubscriber = this.eventManager.subscribe('tabelaImpostoRendaListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
