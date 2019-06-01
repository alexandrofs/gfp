import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILancamentoCartao } from 'app/shared/model/lancamento-cartao.model';
import { LancamentoCartaoService } from './lancamento-cartao.service';

@Component({
    selector: 'jhi-lancamento-cartao-delete-dialog',
    templateUrl: './lancamento-cartao-delete-dialog.component.html'
})
export class LancamentoCartaoDeleteDialogComponent {
    lancamentoCartao: ILancamentoCartao;

    constructor(
        protected lancamentoCartaoService: LancamentoCartaoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lancamentoCartaoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lancamentoCartaoListModification',
                content: 'Deleted an lancamentoCartao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lancamento-cartao-delete-popup',
    template: ''
})
export class LancamentoCartaoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lancamentoCartao }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LancamentoCartaoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.lancamentoCartao = lancamentoCartao;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/lancamento-cartao', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/lancamento-cartao', { outlets: { popup: null } }]);
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
