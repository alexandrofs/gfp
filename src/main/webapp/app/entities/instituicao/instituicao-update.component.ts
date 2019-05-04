import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IInstituicao } from 'app/shared/model/instituicao.model';
import { InstituicaoService } from './instituicao.service';

@Component({
    selector: 'jhi-instituicao-update',
    templateUrl: './instituicao-update.component.html'
})
export class InstituicaoUpdateComponent implements OnInit {
    instituicao: IInstituicao;
    isSaving: boolean;

    constructor(protected instituicaoService: InstituicaoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ instituicao }) => {
            this.instituicao = instituicao;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.instituicao.id !== undefined) {
            this.subscribeToSaveResponse(this.instituicaoService.update(this.instituicao));
        } else {
            this.subscribeToSaveResponse(this.instituicaoService.create(this.instituicao));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstituicao>>) {
        result.subscribe((res: HttpResponse<IInstituicao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
