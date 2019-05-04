import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarteira } from 'app/shared/model/carteira.model';

@Component({
    selector: 'jhi-carteira-detail',
    templateUrl: './carteira-detail.component.html'
})
export class CarteiraDetailComponent implements OnInit {
    carteira: ICarteira;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ carteira }) => {
            this.carteira = carteira;
        });
    }

    previousState() {
        window.history.back();
    }
}
