import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvestimento } from 'app/shared/model/investimento.model';
import { InvestimentoService } from './investimento.service';

@Component({
    selector: 'jhi-investimento-delete-dialog',
    templateUrl: './investimento-delete-dialog.component.html'
})
export class InvestimentoDeleteDialogComponent {
    investimento: IInvestimento;

    constructor(
        protected investimentoService: InvestimentoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.investimentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'investimentoListModification',
                content: 'Deleted an investimento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-investimento-delete-popup',
    template: ''
})
export class InvestimentoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ investimento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InvestimentoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.investimento = investimento;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/investimento', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/investimento', { outlets: { popup: null } }]);
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
