import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    LancamentoCartaoComponent,
    LancamentoCartaoDetailComponent,
    LancamentoCartaoUpdateComponent,
    LancamentoCartaoDeletePopupComponent,
    LancamentoCartaoDeleteDialogComponent,
    lancamentoCartaoRoute,
    lancamentoCartaoPopupRoute
} from './';

const ENTITY_STATES = [...lancamentoCartaoRoute, ...lancamentoCartaoPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LancamentoCartaoComponent,
        LancamentoCartaoDetailComponent,
        LancamentoCartaoUpdateComponent,
        LancamentoCartaoDeleteDialogComponent,
        LancamentoCartaoDeletePopupComponent
    ],
    entryComponents: [
        LancamentoCartaoComponent,
        LancamentoCartaoUpdateComponent,
        LancamentoCartaoDeleteDialogComponent,
        LancamentoCartaoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpLancamentoCartaoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
