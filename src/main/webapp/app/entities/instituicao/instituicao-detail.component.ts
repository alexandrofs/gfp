import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInstituicao } from 'app/shared/model/instituicao.model';

@Component({
    selector: 'jhi-instituicao-detail',
    templateUrl: './instituicao-detail.component.html'
})
export class InstituicaoDetailComponent implements OnInit {
    instituicao: IInstituicao;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ instituicao }) => {
            this.instituicao = instituicao;
        });
    }

    previousState() {
        window.history.back();
    }
}
