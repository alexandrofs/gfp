import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';
import { AccountService } from 'app/core';
import { TipoImpostoRendaService } from './tipo-imposto-renda.service';

@Component({
    selector: 'jhi-tipo-imposto-renda',
    templateUrl: './tipo-imposto-renda.component.html'
})
export class TipoImpostoRendaComponent implements OnInit, OnDestroy {
    tipoImpostoRendas: ITipoImpostoRenda[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected tipoImpostoRendaService: TipoImpostoRendaService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.tipoImpostoRendaService
            .query()
            .pipe(
                filter((res: HttpResponse<ITipoImpostoRenda[]>) => res.ok),
                map((res: HttpResponse<ITipoImpostoRenda[]>) => res.body)
            )
            .subscribe(
                (res: ITipoImpostoRenda[]) => {
                    this.tipoImpostoRendas = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTipoImpostoRendas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITipoImpostoRenda) {
        return item.id;
    }

    registerChangeInTipoImpostoRendas() {
        this.eventSubscriber = this.eventManager.subscribe('tipoImpostoRendaListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
