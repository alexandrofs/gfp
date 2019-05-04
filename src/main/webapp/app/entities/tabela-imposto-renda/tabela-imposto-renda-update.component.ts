import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITabelaImpostoRenda } from 'app/shared/model/tabela-imposto-renda.model';
import { TabelaImpostoRendaService } from './tabela-imposto-renda.service';
import { ITipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';
import { TipoImpostoRendaService } from 'app/entities/tipo-imposto-renda';

@Component({
    selector: 'jhi-tabela-imposto-renda-update',
    templateUrl: './tabela-imposto-renda-update.component.html'
})
export class TabelaImpostoRendaUpdateComponent implements OnInit {
    tabelaImpostoRenda: ITabelaImpostoRenda;
    isSaving: boolean;

    tipoimpostorendas: ITipoImpostoRenda[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected tabelaImpostoRendaService: TabelaImpostoRendaService,
        protected tipoImpostoRendaService: TipoImpostoRendaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tabelaImpostoRenda }) => {
            this.tabelaImpostoRenda = tabelaImpostoRenda;
        });
        this.tipoImpostoRendaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITipoImpostoRenda[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITipoImpostoRenda[]>) => response.body)
            )
            .subscribe((res: ITipoImpostoRenda[]) => (this.tipoimpostorendas = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tabelaImpostoRenda.id !== undefined) {
            this.subscribeToSaveResponse(this.tabelaImpostoRendaService.update(this.tabelaImpostoRenda));
        } else {
            this.subscribeToSaveResponse(this.tabelaImpostoRendaService.create(this.tabelaImpostoRenda));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITabelaImpostoRenda>>) {
        result.subscribe((res: HttpResponse<ITabelaImpostoRenda>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTipoImpostoRendaById(index: number, item: ITipoImpostoRenda) {
        return item.id;
    }
}
