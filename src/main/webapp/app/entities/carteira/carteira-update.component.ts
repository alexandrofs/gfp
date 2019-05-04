import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICarteira } from 'app/shared/model/carteira.model';
import { CarteiraService } from './carteira.service';

@Component({
    selector: 'jhi-carteira-update',
    templateUrl: './carteira-update.component.html'
})
export class CarteiraUpdateComponent implements OnInit {
    carteira: ICarteira;
    isSaving: boolean;

    constructor(protected carteiraService: CarteiraService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ carteira }) => {
            this.carteira = carteira;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.carteira.id !== undefined) {
            this.subscribeToSaveResponse(this.carteiraService.update(this.carteira));
        } else {
            this.subscribeToSaveResponse(this.carteiraService.create(this.carteira));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarteira>>) {
        result.subscribe((res: HttpResponse<ICarteira>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
