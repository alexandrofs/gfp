import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    TipoImpostoRendaComponent,
    TipoImpostoRendaDetailComponent,
    TipoImpostoRendaUpdateComponent,
    TipoImpostoRendaDeletePopupComponent,
    TipoImpostoRendaDeleteDialogComponent,
    tipoImpostoRendaRoute,
    tipoImpostoRendaPopupRoute
} from './';

const ENTITY_STATES = [...tipoImpostoRendaRoute, ...tipoImpostoRendaPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoImpostoRendaComponent,
        TipoImpostoRendaDetailComponent,
        TipoImpostoRendaUpdateComponent,
        TipoImpostoRendaDeleteDialogComponent,
        TipoImpostoRendaDeletePopupComponent
    ],
    entryComponents: [
        TipoImpostoRendaComponent,
        TipoImpostoRendaUpdateComponent,
        TipoImpostoRendaDeleteDialogComponent,
        TipoImpostoRendaDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpTipoImpostoRendaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
