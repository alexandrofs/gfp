import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoriaDespesa } from 'app/shared/model/categoria-despesa.model';

@Component({
    selector: 'jhi-categoria-despesa-detail',
    templateUrl: './categoria-despesa-detail.component.html'
})
export class CategoriaDespesaDetailComponent implements OnInit {
    categoriaDespesa: ICategoriaDespesa;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ categoriaDespesa }) => {
            this.categoriaDespesa = categoriaDespesa;
        });
    }

    previousState() {
        window.history.back();
    }
}
