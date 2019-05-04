import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHistoricoCotas } from 'app/shared/model/historico-cotas.model';
import { HistoricoCotasService } from './historico-cotas.service';

@Component({
    selector: 'jhi-historico-cotas-delete-dialog',
    templateUrl: './historico-cotas-delete-dialog.component.html'
})
export class HistoricoCotasDeleteDialogComponent {
    historicoCotas: IHistoricoCotas;

    constructor(
        protected historicoCotasService: HistoricoCotasService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.historicoCotasService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'historicoCotasListModification',
                content: 'Deleted an historicoCotas'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-historico-cotas-delete-popup',
    template: ''
})
export class HistoricoCotasDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ historicoCotas }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HistoricoCotasDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.historicoCotas = historicoCotas;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/historico-cotas', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/historico-cotas', { outlets: { popup: null } }]);
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
