import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistoricoCotas } from 'app/shared/model/historico-cotas.model';

@Component({
    selector: 'jhi-historico-cotas-detail',
    templateUrl: './historico-cotas-detail.component.html'
})
export class HistoricoCotasDetailComponent implements OnInit {
    historicoCotas: IHistoricoCotas;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ historicoCotas }) => {
            this.historicoCotas = historicoCotas;
        });
    }

    previousState() {
        window.history.back();
    }
}
