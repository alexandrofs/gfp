import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { FileImporterService } from 'app/shared/services/file-importer.service';
import { map } from 'rxjs/operators';

@Component({
    selector: 'jhi-import-dialog',
    templateUrl: './import-dialog.component.html'
})
export class ImportDialogComponent implements OnInit {
    fileToUpload: File = null;
    isSaving: boolean;
    fileImporterType: string = null;

    constructor(
        protected fileImporterService: FileImporterService,
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
        this.subscribeToSaveResponse(this.fileImporterService.import(this.fileToUpload, this.fileImporterType));
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<any>>) {
        result.subscribe((res: HttpResponse<any>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.eventManager.broadcast({
            name: 'fileImport',
            content: 'Imported a new file'
        });
        this.isSaving = false;
        this.activeModal.dismiss(true);
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-import-popup',
    template: ''
})
export class ImportPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;
    protected fileImporterType: String = null;
    protected routeReturn: String = null;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.params.subscribe(p => {
            this.fileImporterType = p.fileImporterType;
            this.routeReturn = p.routeReturn;
        });
        this.activatedRoute.data.subscribe(() => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ImportDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.fileImporterType = this.fileImporterType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/' + this.routeReturn, { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/' + this.routeReturn, { outlets: { popup: null } }]);
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
