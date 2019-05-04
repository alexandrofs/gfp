import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvestimento } from 'app/shared/model/investimento.model';

@Component({
    selector: 'jhi-investimento-detail',
    templateUrl: './investimento-detail.component.html'
})
export class InvestimentoDetailComponent implements OnInit {
    investimento: IInvestimento;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ investimento }) => {
            this.investimento = investimento;
        });
    }

    previousState() {
        window.history.back();
    }
}
