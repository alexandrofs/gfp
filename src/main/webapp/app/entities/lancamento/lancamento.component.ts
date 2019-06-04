import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ILancamento } from 'app/shared/model/lancamento.model';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { LancamentoService } from './lancamento.service';

@Component({
    selector: 'jhi-lancamento',
    templateUrl: './lancamento.component.html'
})
export class LancamentoComponent implements OnInit, OnDestroy {
    contaPagamento: IContaPagamento;
    lancamentos: ILancamento[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;

    constructor(
        protected lancamentoService: LancamentoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute
    ) {
        this.lancamentos = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.lancamentoService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort(),
                'contaPagamentoId.equals': this.contaPagamento.id
            })
            .subscribe(
                (res: HttpResponse<ILancamento[]>) => this.paginateLancamentos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reset() {
        this.page = 0;
        this.lancamentos = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contaPagamento }) => {
            this.contaPagamento = contaPagamento;
        });
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLancamentos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILancamento) {
        return item.id;
    }

    registerChangeInLancamentos() {
        this.eventSubscriber = this.eventManager.subscribe('lancamentoListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateLancamentos(data: ILancamento[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.lancamentos.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
