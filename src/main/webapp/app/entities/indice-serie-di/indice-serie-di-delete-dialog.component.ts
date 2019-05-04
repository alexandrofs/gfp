import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIndiceSerieDi } from 'app/shared/model/indice-serie-di.model';
import { IndiceSerieDiService } from './indice-serie-di.service';

@Component({
    selector: 'jhi-indice-serie-di-delete-dialog',
    templateUrl: './indice-serie-di-delete-dialog.component.html'
})
export class IndiceSerieDiDeleteDialogComponent {
    indiceSerieDi: IIndiceSerieDi;

    constructor(
        protected indiceSerieDiService: IndiceSerieDiService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.indiceSerieDiService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'indiceSerieDiListModification',
                content: 'Deleted an indiceSerieDi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-indice-serie-di-delete-popup',
    template: ''
})
export class IndiceSerieDiDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ indiceSerieDi }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IndiceSerieDiDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.indiceSerieDi = indiceSerieDi;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/indice-serie-di', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/indice-serie-di', { outlets: { popup: null } }]);
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
