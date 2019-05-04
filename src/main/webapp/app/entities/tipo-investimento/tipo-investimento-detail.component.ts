import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoInvestimento } from 'app/shared/model/tipo-investimento.model';

@Component({
    selector: 'jhi-tipo-investimento-detail',
    templateUrl: './tipo-investimento-detail.component.html'
})
export class TipoInvestimentoDetailComponent implements OnInit {
    tipoInvestimento: ITipoInvestimento;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoInvestimento }) => {
            this.tipoInvestimento = tipoInvestimento;
        });
    }

    previousState() {
        window.history.back();
    }
}
