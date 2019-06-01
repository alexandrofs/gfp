import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILancamentoCartao } from 'app/shared/model/lancamento-cartao.model';

@Component({
    selector: 'jhi-lancamento-cartao-detail',
    templateUrl: './lancamento-cartao-detail.component.html'
})
export class LancamentoCartaoDetailComponent implements OnInit {
    lancamentoCartao: ILancamentoCartao;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lancamentoCartao }) => {
            this.lancamentoCartao = lancamentoCartao;
        });
    }

    previousState() {
        window.history.back();
    }
}
