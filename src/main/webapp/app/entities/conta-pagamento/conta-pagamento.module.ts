import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    ContaPagamentoComponent,
    ContaPagamentoDetailComponent,
    ContaPagamentoUpdateComponent,
    ContaPagamentoDeletePopupComponent,
    ContaPagamentoDeleteDialogComponent,
    contaPagamentoRoute,
    contaPagamentoPopupRoute
} from './';

const ENTITY_STATES = [...contaPagamentoRoute, ...contaPagamentoPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContaPagamentoComponent,
        ContaPagamentoDetailComponent,
        ContaPagamentoUpdateComponent,
        ContaPagamentoDeleteDialogComponent,
        ContaPagamentoDeletePopupComponent
    ],
    entryComponents: [
        ContaPagamentoComponent,
        ContaPagamentoUpdateComponent,
        ContaPagamentoDeleteDialogComponent,
        ContaPagamentoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpContaPagamentoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
