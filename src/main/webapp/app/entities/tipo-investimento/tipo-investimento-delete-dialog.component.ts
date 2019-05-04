import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoInvestimento } from 'app/shared/model/tipo-investimento.model';
import { TipoInvestimentoService } from './tipo-investimento.service';

@Component({
    selector: 'jhi-tipo-investimento-delete-dialog',
    templateUrl: './tipo-investimento-delete-dialog.component.html'
})
export class TipoInvestimentoDeleteDialogComponent {
    tipoInvestimento: ITipoInvestimento;

    constructor(
        protected tipoInvestimentoService: TipoInvestimentoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoInvestimentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoInvestimentoListModification',
                content: 'Deleted an tipoInvestimento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-investimento-delete-popup',
    template: ''
})
export class TipoInvestimentoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoInvestimento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoInvestimentoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tipoInvestimento = tipoInvestimento;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/tipo-investimento', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/tipo-investimento', { outlets: { popup: null } }]);
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
