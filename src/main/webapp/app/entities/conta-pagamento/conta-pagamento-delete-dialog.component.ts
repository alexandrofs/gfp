import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { ContaPagamentoService } from './conta-pagamento.service';

@Component({
    selector: 'jhi-conta-pagamento-delete-dialog',
    templateUrl: './conta-pagamento-delete-dialog.component.html'
})
export class ContaPagamentoDeleteDialogComponent {
    contaPagamento: IContaPagamento;

    constructor(
        protected contaPagamentoService: ContaPagamentoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contaPagamentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contaPagamentoListModification',
                content: 'Deleted an contaPagamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-conta-pagamento-delete-popup',
    template: ''
})
export class ContaPagamentoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contaPagamento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContaPagamentoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.contaPagamento = contaPagamento;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/conta-pagamento', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/conta-pagamento', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
