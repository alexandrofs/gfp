import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IInvestimento } from 'app/shared/model/investimento.model';
import { InvestimentoService } from './investimento.service';
import { ICarteira } from 'app/shared/model/carteira.model';
import { CarteiraService } from 'app/entities/carteira';
import { ITipoInvestimento } from 'app/shared/model/tipo-investimento.model';
import { TipoInvestimentoService } from 'app/entities/tipo-investimento';
import { IInstituicao } from 'app/shared/model/instituicao.model';
import { InstituicaoService } from 'app/entities/instituicao';

@Component({
    selector: 'jhi-investimento-update',
    templateUrl: './investimento-update.component.html'
})
export class InvestimentoUpdateComponent implements OnInit {
    investimento: IInvestimento;
    isSaving: boolean;

    carteiras: ICarteira[];

    tipoinvestimentos: ITipoInvestimento[];

    instituicaos: IInstituicao[];
    dataAplicacaoDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected investimentoService: InvestimentoService,
        protected carteiraService: CarteiraService,
        protected tipoInvestimentoService: TipoInvestimentoService,
        protected instituicaoService: InstituicaoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ investimento }) => {
            this.investimento = investimento;
        });
        this.carteiraService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICarteira[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICarteira[]>) => response.body)
            )
            .subscribe((res: ICarteira[]) => (this.carteiras = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.tipoInvestimentoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITipoInvestimento[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITipoInvestimento[]>) => response.body)
            )
            .subscribe((res: ITipoInvestimento[]) => (this.tipoinvestimentos = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.instituicaoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInstituicao[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInstituicao[]>) => response.body)
            )
            .subscribe((res: IInstituicao[]) => (this.instituicaos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.investimento.id !== undefined) {
            this.subscribeToSaveResponse(this.investimentoService.update(this.investimento));
        } else {
            this.subscribeToSaveResponse(this.investimentoService.create(this.investimento));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvestimento>>) {
        result.subscribe((res: HttpResponse<IInvestimento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCarteiraById(index: number, item: ICarteira) {
        return item.id;
    }

    trackTipoInvestimentoById(index: number, item: ITipoInvestimento) {
        return item.id;
    }

    trackInstituicaoById(index: number, item: IInstituicao) {
        return item.id;
    }
}
