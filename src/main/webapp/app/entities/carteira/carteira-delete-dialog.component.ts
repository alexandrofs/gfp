import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarteira } from 'app/shared/model/carteira.model';
import { CarteiraService } from './carteira.service';

@Component({
    selector: 'jhi-carteira-delete-dialog',
    templateUrl: './carteira-delete-dialog.component.html'
})
export class CarteiraDeleteDialogComponent {
    carteira: ICarteira;

    constructor(protected carteiraService: CarteiraService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.carteiraService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'carteiraListModification',
                content: 'Deleted an carteira'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-carteira-delete-popup',
    template: ''
})
export class CarteiraDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ carteira }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CarteiraDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.carteira = carteira;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/carteira', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/carteira', { outlets: { popup: null } }]);
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
