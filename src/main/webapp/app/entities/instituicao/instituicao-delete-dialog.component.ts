import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstituicao } from 'app/shared/model/instituicao.model';
import { InstituicaoService } from './instituicao.service';

@Component({
    selector: 'jhi-instituicao-delete-dialog',
    templateUrl: './instituicao-delete-dialog.component.html'
})
export class InstituicaoDeleteDialogComponent {
    instituicao: IInstituicao;

    constructor(
        protected instituicaoService: InstituicaoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.instituicaoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'instituicaoListModification',
                content: 'Deleted an instituicao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-instituicao-delete-popup',
    template: ''
})
export class InstituicaoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ instituicao }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InstituicaoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.instituicao = instituicao;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/instituicao', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/instituicao', { outlets: { popup: null } }]);
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
