import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoriaDespesa } from 'app/shared/model/categoria-despesa.model';
import { CategoriaDespesaService } from './categoria-despesa.service';

@Component({
    selector: 'jhi-categoria-despesa-delete-dialog',
    templateUrl: './categoria-despesa-delete-dialog.component.html'
})
export class CategoriaDespesaDeleteDialogComponent {
    categoriaDespesa: ICategoriaDespesa;

    constructor(
        protected categoriaDespesaService: CategoriaDespesaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.categoriaDespesaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'categoriaDespesaListModification',
                content: 'Deleted an categoriaDespesa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-categoria-despesa-delete-popup',
    template: ''
})
export class CategoriaDespesaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ categoriaDespesa }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CategoriaDespesaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.categoriaDespesa = categoriaDespesa;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/categoria-despesa', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/categoria-despesa', { outlets: { popup: null } }]);
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
