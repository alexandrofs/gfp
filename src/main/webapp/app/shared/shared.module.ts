import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import {
    GfpSharedLibsModule,
    GfpSharedCommonModule,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    ImportDialogComponent,
    ImportPopupComponent,
    importPopupRoute
} from './';

const ENTITY_STATES = [...importPopupRoute];

@NgModule({
    imports: [GfpSharedLibsModule, GfpSharedCommonModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, ImportDialogComponent, ImportPopupComponent],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent, ImportDialogComponent, ImportPopupComponent],
    exports: [GfpSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpSharedModule {
    static forRoot() {
        return {
            ngModule: GfpSharedModule
        };
    }
}
