import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    LancamentoComponent,
    LancamentoDetailComponent,
    LancamentoUpdateComponent,
    LancamentoDeletePopupComponent,
    LancamentoDeleteDialogComponent,
    lancamentoRoute,
    lancamentoPopupRoute
} from './';

const ENTITY_STATES = [...lancamentoRoute, ...lancamentoPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LancamentoComponent,
        LancamentoDetailComponent,
        LancamentoUpdateComponent,
        LancamentoDeleteDialogComponent,
        LancamentoDeletePopupComponent
    ],
    entryComponents: [LancamentoComponent, LancamentoUpdateComponent, LancamentoDeleteDialogComponent, LancamentoDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpLancamentoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
