import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';
import { TipoImpostoRendaService } from './tipo-imposto-renda.service';

@Component({
    selector: 'jhi-tipo-imposto-renda-delete-dialog',
    templateUrl: './tipo-imposto-renda-delete-dialog.component.html'
})
export class TipoImpostoRendaDeleteDialogComponent {
    tipoImpostoRenda: ITipoImpostoRenda;

    constructor(
        protected tipoImpostoRendaService: TipoImpostoRendaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoImpostoRendaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoImpostoRendaListModification',
                content: 'Deleted an tipoImpostoRenda'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-imposto-renda-delete-popup',
    template: ''
})
export class TipoImpostoRendaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoImpostoRenda }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoImpostoRendaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tipoImpostoRenda = tipoImpostoRenda;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/tipo-imposto-renda', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/tipo-imposto-renda', { outlets: { popup: null } }]);
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
