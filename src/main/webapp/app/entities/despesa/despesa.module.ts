import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    DespesaComponent,
    DespesaDetailComponent,
    DespesaUpdateComponent,
    DespesaDeletePopupComponent,
    DespesaDeleteDialogComponent,
    despesaRoute,
    despesaPopupRoute
} from './';

const ENTITY_STATES = [...despesaRoute, ...despesaPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DespesaComponent,
        DespesaDetailComponent,
        DespesaUpdateComponent,
        DespesaDeleteDialogComponent,
        DespesaDeletePopupComponent
    ],
    entryComponents: [DespesaComponent, DespesaUpdateComponent, DespesaDeleteDialogComponent, DespesaDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpDespesaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
