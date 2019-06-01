import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    CategoriaDespesaComponent,
    CategoriaDespesaDetailComponent,
    CategoriaDespesaUpdateComponent,
    CategoriaDespesaDeletePopupComponent,
    CategoriaDespesaDeleteDialogComponent,
    categoriaDespesaRoute,
    categoriaDespesaPopupRoute
} from './';

const ENTITY_STATES = [...categoriaDespesaRoute, ...categoriaDespesaPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CategoriaDespesaComponent,
        CategoriaDespesaDetailComponent,
        CategoriaDespesaUpdateComponent,
        CategoriaDespesaDeleteDialogComponent,
        CategoriaDespesaDeletePopupComponent
    ],
    entryComponents: [
        CategoriaDespesaComponent,
        CategoriaDespesaUpdateComponent,
        CategoriaDespesaDeleteDialogComponent,
        CategoriaDespesaDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpCategoriaDespesaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
