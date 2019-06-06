import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ILancamentoCartao } from 'app/shared/model/lancamento-cartao.model';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { LancamentoCartaoService } from './lancamento-cartao.service';

@Component({
    selector: 'jhi-lancamento-cartao',
    templateUrl: './lancamento-cartao.component.html'
})
export class LancamentoCartaoComponent implements OnInit, OnDestroy {
    contaPagamento: IContaPagamento;
    lancamentoCartaos: ILancamentoCartao[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;

    constructor(
        protected lancamentoCartaoService: LancamentoCartaoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute
    ) {
        this.lancamentoCartaos = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.lancamentoCartaoService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort(),
                'contaPagamentoId.equals': this.contaPagamento.id
            })
            .subscribe(
                (res: HttpResponse<ILancamentoCartao[]>) => this.paginateLancamentoCartaos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reset() {
        this.page = 0;
        this.lancamentoCartaos = [];
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
        this.registerChangeInLancamentoCartaos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILancamentoCartao) {
        return item.id;
    }

    registerChangeInLancamentoCartaos() {
        this.eventSubscriber = this.eventManager.subscribe('lancamentoCartaoListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateLancamentoCartaos(data: ILancamentoCartao[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.lancamentoCartaos.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
