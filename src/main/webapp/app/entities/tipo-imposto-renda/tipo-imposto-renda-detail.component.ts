import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';

@Component({
    selector: 'jhi-tipo-imposto-renda-detail',
    templateUrl: './tipo-imposto-renda-detail.component.html'
})
export class TipoImpostoRendaDetailComponent implements OnInit {
    tipoImpostoRenda: ITipoImpostoRenda;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoImpostoRenda }) => {
            this.tipoImpostoRenda = tipoImpostoRenda;
        });
    }

    previousState() {
        window.history.back();
    }
}
