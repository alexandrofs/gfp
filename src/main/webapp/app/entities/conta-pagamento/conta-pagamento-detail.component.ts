import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';

@Component({
    selector: 'jhi-conta-pagamento-detail',
    templateUrl: './conta-pagamento-detail.component.html'
})
export class ContaPagamentoDetailComponent implements OnInit {
    contaPagamento: IContaPagamento;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contaPagamento }) => {
            this.contaPagamento = contaPagamento;
        });
    }

    previousState() {
        window.history.back();
    }
}
