import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHistoricoCotas } from 'app/shared/model/historico-cotas.model';
import { AccountService } from 'app/core';
import { HistoricoCotasService } from './historico-cotas.service';

@Component({
    selector: 'jhi-historico-cotas',
    templateUrl: './historico-cotas.component.html'
})
export class HistoricoCotasComponent implements OnInit, OnDestroy {
    historicoCotas: IHistoricoCotas[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected historicoCotasService: HistoricoCotasService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.historicoCotasService
            .query()
            .pipe(
                filter((res: HttpResponse<IHistoricoCotas[]>) => res.ok),
                map((res: HttpResponse<IHistoricoCotas[]>) => res.body)
            )
            .subscribe(
                (res: IHistoricoCotas[]) => {
                    this.historicoCotas = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHistoricoCotas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHistoricoCotas) {
        return item.id;
    }

    registerChangeInHistoricoCotas() {
        this.eventSubscriber = this.eventManager.subscribe('historicoCotasListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
