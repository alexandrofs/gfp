import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITipoInvestimento } from 'app/shared/model/tipo-investimento.model';
import { TipoInvestimentoService } from './tipo-investimento.service';
import { ModalidadeService } from './modalidade.service';
import { ITipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';
import { TipoImpostoRendaService } from 'app/entities/tipo-imposto-renda';

@Component({
    selector: 'jhi-tipo-investimento-update',
    templateUrl: './tipo-investimento-update.component.html'
})
export class TipoInvestimentoUpdateComponent implements OnInit {
    tipoInvestimento: ITipoInvestimento;
    isSaving: boolean;
    tipoimpostorendas: ITipoImpostoRenda[];
    modalidades: Map<string, string>;

    MODALIDADE_CDB: String = 'CDB';
    MODALIDADE_LCI: String = 'LCI';

    INDEXADOR_POS: String = 'POS';
    INDEXADOR_PRE: String = 'PRE';

    tipoIndexadores = [this.INDEXADOR_PRE, this.INDEXADOR_POS];
    indices = ['DI', 'IPCA'];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected tipoInvestimentoService: TipoInvestimentoService,
        protected modalidadeService: ModalidadeService,
        protected tipoImpostoRendaService: TipoImpostoRendaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoInvestimento }) => {
            this.tipoInvestimento = tipoInvestimento;
        });

        this.modalidadeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<Map<string, string>>) => mayBeOk.ok),
                map((response: HttpResponse<Map<string, string>>) => response.body)
            )
            .subscribe((res: Map<string, string>) => (this.modalidades = res), (res: HttpErrorResponse) => this.onError(res.message));

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
        if (this.tipoInvestimento.modalidade !== this.MODALIDADE_CDB && this.tipoInvestimento.modalidade !== this.MODALIDADE_LCI) {
            this.tipoInvestimento.tipoIndexador = null;
        }
        if (this.tipoInvestimento.tipoIndexador !== this.INDEXADOR_POS) {
            this.tipoInvestimento.indice = null;
        }
        if (this.tipoInvestimento.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoInvestimentoService.update(this.tipoInvestimento));
        } else {
            this.subscribeToSaveResponse(this.tipoInvestimentoService.create(this.tipoInvestimento));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoInvestimento>>) {
        result.subscribe((res: HttpResponse<ITipoInvestimento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
