import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IndiceSerieDiService } from './indice-serie-di.service';

@Component({
    selector: 'jhi-indice-serie-di-import-dialog',
    templateUrl: './indice-serie-di-import-dialog.component.html'
})
export class IndiceSerieDiImportDialogComponent implements OnInit {
    fileToUpload: File = null;
    isSaving: boolean;

    constructor(
        protected indiceSerieDiService: IndiceSerieDiService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
    }

    handleFileInput(files: FileList) {
        this.fileToUpload = files.item(0);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    importFile() {
        this.isSaving = true;
        this.subscribeToSaveResponse(this.indiceSerieDiService.import(this.fileToUpload));
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<any>>) {
        result.subscribe((res: HttpResponse<any>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.eventManager.broadcast({
            name: 'indiceSerieDiFileImport',
            content: 'Imported a new file indiceSerieDi'
        });
        this.isSaving = false;
        this.activeModal.dismiss(true);
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-indice-serie-di-import-popup',
    template: ''
})
export class IndiceSerieDiImportPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(() => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IndiceSerieDiImportDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                // this.ngbModalRef.componentInstance.indiceSerieDi = indiceSerieDi;
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
