import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDespesa } from 'app/shared/model/despesa.model';

@Component({
    selector: 'jhi-despesa-detail',
    templateUrl: './despesa-detail.component.html'
})
export class DespesaDetailComponent implements OnInit {
    despesa: IDespesa;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ despesa }) => {
            this.despesa = despesa;
        });
    }

    previousState() {
        window.history.back();
    }
}
