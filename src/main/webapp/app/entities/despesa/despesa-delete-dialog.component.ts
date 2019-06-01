import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDespesa } from 'app/shared/model/despesa.model';
import { DespesaService } from './despesa.service';

@Component({
    selector: 'jhi-despesa-delete-dialog',
    templateUrl: './despesa-delete-dialog.component.html'
})
export class DespesaDeleteDialogComponent {
    despesa: IDespesa;

    constructor(protected despesaService: DespesaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.despesaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'despesaListModification',
                content: 'Deleted an despesa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-despesa-delete-popup',
    template: ''
})
export class DespesaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ despesa }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DespesaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.despesa = despesa;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/despesa', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/despesa', { outlets: { popup: null } }]);
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
