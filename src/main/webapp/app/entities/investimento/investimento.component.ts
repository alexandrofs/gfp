import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInvestimento } from 'app/shared/model/investimento.model';
import { AccountService } from 'app/core';
import { InvestimentoService } from './investimento.service';

@Component({
    selector: 'jhi-investimento',
    templateUrl: './investimento.component.html'
})
export class InvestimentoComponent implements OnInit, OnDestroy {
    investimentos: IInvestimento[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected investimentoService: InvestimentoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.investimentoService
            .query()
            .pipe(
                filter((res: HttpResponse<IInvestimento[]>) => res.ok),
                map((res: HttpResponse<IInvestimento[]>) => res.body)
            )
            .subscribe(
                (res: IInvestimento[]) => {
                    this.investimentos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInInvestimentos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IInvestimento) {
        return item.id;
    }

    registerChangeInInvestimentos() {
        this.eventSubscriber = this.eventManager.subscribe('investimentoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
