import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITabelaImpostoRenda } from 'app/shared/model/tabela-imposto-renda.model';

@Component({
    selector: 'jhi-tabela-imposto-renda-detail',
    templateUrl: './tabela-imposto-renda-detail.component.html'
})
export class TabelaImpostoRendaDetailComponent implements OnInit {
    tabelaImpostoRenda: ITabelaImpostoRenda;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tabelaImpostoRenda }) => {
            this.tabelaImpostoRenda = tabelaImpostoRenda;
        });
    }

    previousState() {
        window.history.back();
    }
}
