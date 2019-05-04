import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInstituicao } from 'app/shared/model/instituicao.model';
import { AccountService } from 'app/core';
import { InstituicaoService } from './instituicao.service';

@Component({
    selector: 'jhi-instituicao',
    templateUrl: './instituicao.component.html'
})
export class InstituicaoComponent implements OnInit, OnDestroy {
    instituicaos: IInstituicao[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected instituicaoService: InstituicaoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.instituicaoService
            .query()
            .pipe(
                filter((res: HttpResponse<IInstituicao[]>) => res.ok),
                map((res: HttpResponse<IInstituicao[]>) => res.body)
            )
            .subscribe(
                (res: IInstituicao[]) => {
                    this.instituicaos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInInstituicaos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IInstituicao) {
        return item.id;
    }

    registerChangeInInstituicaos() {
        this.eventSubscriber = this.eventManager.subscribe('instituicaoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
