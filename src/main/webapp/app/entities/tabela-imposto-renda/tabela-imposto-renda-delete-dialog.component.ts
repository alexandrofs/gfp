import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITabelaImpostoRenda } from 'app/shared/model/tabela-imposto-renda.model';
import { TabelaImpostoRendaService } from './tabela-imposto-renda.service';

@Component({
    selector: 'jhi-tabela-imposto-renda-delete-dialog',
    templateUrl: './tabela-imposto-renda-delete-dialog.component.html'
})
export class TabelaImpostoRendaDeleteDialogComponent {
    tabelaImpostoRenda: ITabelaImpostoRenda;

    constructor(
        protected tabelaImpostoRendaService: TabelaImpostoRendaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tabelaImpostoRendaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tabelaImpostoRendaListModification',
                content: 'Deleted an tabelaImpostoRenda'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tabela-imposto-renda-delete-popup',
    template: ''
})
export class TabelaImpostoRendaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tabelaImpostoRenda }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TabelaImpostoRendaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tabelaImpostoRenda = tabelaImpostoRenda;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/tabela-imposto-renda', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/tabela-imposto-renda', { outlets: { popup: null } }]);
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
