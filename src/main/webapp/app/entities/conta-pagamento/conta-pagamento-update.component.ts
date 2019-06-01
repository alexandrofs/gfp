import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IContaPagamento, ContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { ContaPagamentoService } from './conta-pagamento.service';

@Component({
    selector: 'jhi-conta-pagamento-update',
    templateUrl: './conta-pagamento-update.component.html'
})
export class ContaPagamentoUpdateComponent implements OnInit {
    contaPagamento: IContaPagamento;
    isSaving: boolean;

    editForm = this.fb.group({
        id: [],
        nome: [null, [Validators.required]],
        tipoConta: [null, [Validators.required]],
        usuario: [null, [Validators.required]]
    });

    constructor(
        protected contaPagamentoService: ContaPagamentoService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contaPagamento }) => {
            this.updateForm(contaPagamento);
            this.contaPagamento = contaPagamento;
        });
    }

    updateForm(contaPagamento: IContaPagamento) {
        this.editForm.patchValue({
            id: contaPagamento.id,
            nome: contaPagamento.nome,
            tipoConta: contaPagamento.tipoConta,
            usuario: contaPagamento.usuario
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const contaPagamento = this.createFromForm();
        if (contaPagamento.id !== undefined) {
            this.subscribeToSaveResponse(this.contaPagamentoService.update(contaPagamento));
        } else {
            this.subscribeToSaveResponse(this.contaPagamentoService.create(contaPagamento));
        }
    }

    private createFromForm(): IContaPagamento {
        const entity = {
            ...new ContaPagamento(),
            id: this.editForm.get(['id']).value,
            nome: this.editForm.get(['nome']).value,
            tipoConta: this.editForm.get(['tipoConta']).value,
            usuario: this.editForm.get(['usuario']).value
        };
        return entity;
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContaPagamento>>) {
        result.subscribe((res: HttpResponse<IContaPagamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
