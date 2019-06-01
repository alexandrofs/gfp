import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICategoriaDespesa } from 'app/shared/model/categoria-despesa.model';
import { AccountService } from 'app/core';
import { CategoriaDespesaService } from './categoria-despesa.service';

@Component({
    selector: 'jhi-categoria-despesa',
    templateUrl: './categoria-despesa.component.html'
})
export class CategoriaDespesaComponent implements OnInit, OnDestroy {
    categoriaDespesas: ICategoriaDespesa[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected categoriaDespesaService: CategoriaDespesaService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.categoriaDespesaService
            .query()
            .pipe(
                filter((res: HttpResponse<ICategoriaDespesa[]>) => res.ok),
                map((res: HttpResponse<ICategoriaDespesa[]>) => res.body)
            )
            .subscribe(
                (res: ICategoriaDespesa[]) => {
                    this.categoriaDespesas = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCategoriaDespesas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICategoriaDespesa) {
        return item.id;
    }

    registerChangeInCategoriaDespesas() {
        this.eventSubscriber = this.eventManager.subscribe('categoriaDespesaListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
