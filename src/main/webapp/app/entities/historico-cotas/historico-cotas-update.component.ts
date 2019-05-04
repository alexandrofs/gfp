import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IHistoricoCotas } from 'app/shared/model/historico-cotas.model';
import { HistoricoCotasService } from './historico-cotas.service';
import { IInvestimento } from 'app/shared/model/investimento.model';
import { InvestimentoService } from 'app/entities/investimento';

@Component({
    selector: 'jhi-historico-cotas-update',
    templateUrl: './historico-cotas-update.component.html'
})
export class HistoricoCotasUpdateComponent implements OnInit {
    historicoCotas: IHistoricoCotas;
    isSaving: boolean;

    investimentos: IInvestimento[];
    dataCotaDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected historicoCotasService: HistoricoCotasService,
        protected investimentoService: InvestimentoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ historicoCotas }) => {
            this.historicoCotas = historicoCotas;
        });
        this.investimentoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInvestimento[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInvestimento[]>) => response.body)
            )
            .subscribe((res: IInvestimento[]) => (this.investimentos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.historicoCotas.id !== undefined) {
            this.subscribeToSaveResponse(this.historicoCotasService.update(this.historicoCotas));
        } else {
            this.subscribeToSaveResponse(this.historicoCotasService.create(this.historicoCotas));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoricoCotas>>) {
        result.subscribe((res: HttpResponse<IHistoricoCotas>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackInvestimentoById(index: number, item: IInvestimento) {
        return item.id;
    }
}
