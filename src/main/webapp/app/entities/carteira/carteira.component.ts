import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICarteira } from 'app/shared/model/carteira.model';
import { AccountService } from 'app/core';
import { CarteiraService } from './carteira.service';

@Component({
    selector: 'jhi-carteira',
    templateUrl: './carteira.component.html'
})
export class CarteiraComponent implements OnInit, OnDestroy {
    carteiras: ICarteira[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected carteiraService: CarteiraService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.carteiraService
            .query()
            .pipe(
                filter((res: HttpResponse<ICarteira[]>) => res.ok),
                map((res: HttpResponse<ICarteira[]>) => res.body)
            )
            .subscribe(
                (res: ICarteira[]) => {
                    this.carteiras = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCarteiras();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICarteira) {
        return item.id;
    }

    registerChangeInCarteiras() {
        this.eventSubscriber = this.eventManager.subscribe('carteiraListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
