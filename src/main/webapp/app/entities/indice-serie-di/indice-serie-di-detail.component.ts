import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndiceSerieDi } from 'app/shared/model/indice-serie-di.model';

@Component({
    selector: 'jhi-indice-serie-di-detail',
    templateUrl: './indice-serie-di-detail.component.html'
})
export class IndiceSerieDiDetailComponent implements OnInit {
    indiceSerieDi: IIndiceSerieDi;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ indiceSerieDi }) => {
            this.indiceSerieDi = indiceSerieDi;
        });
    }

    previousState() {
        window.history.back();
    }
}
