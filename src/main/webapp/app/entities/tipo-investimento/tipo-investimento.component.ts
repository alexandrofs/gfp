import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITipoInvestimento } from 'app/shared/model/tipo-investimento.model';
import { AccountService } from 'app/core';
import { TipoInvestimentoService } from './tipo-investimento.service';

@Component({
    selector: 'jhi-tipo-investimento',
    templateUrl: './tipo-investimento.component.html'
})
export class TipoInvestimentoComponent implements OnInit, OnDestroy {
    tipoInvestimentos: ITipoInvestimento[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected tipoInvestimentoService: TipoInvestimentoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.tipoInvestimentoService
            .query()
            .pipe(
                filter((res: HttpResponse<ITipoInvestimento[]>) => res.ok),
                map((res: HttpResponse<ITipoInvestimento[]>) => res.body)
            )
            .subscribe(
                (res: ITipoInvestimento[]) => {
                    this.tipoInvestimentos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTipoInvestimentos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITipoInvestimento) {
        return item.id;
    }

    registerChangeInTipoInvestimentos() {
        this.eventSubscriber = this.eventManager.subscribe('tipoInvestimentoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
