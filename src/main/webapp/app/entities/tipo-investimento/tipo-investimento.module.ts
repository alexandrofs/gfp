import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    TipoInvestimentoComponent,
    TipoInvestimentoDetailComponent,
    TipoInvestimentoUpdateComponent,
    TipoInvestimentoDeletePopupComponent,
    TipoInvestimentoDeleteDialogComponent,
    tipoInvestimentoRoute,
    tipoInvestimentoPopupRoute
} from './';

const ENTITY_STATES = [...tipoInvestimentoRoute, ...tipoInvestimentoPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoInvestimentoComponent,
        TipoInvestimentoDetailComponent,
        TipoInvestimentoUpdateComponent,
        TipoInvestimentoDeleteDialogComponent,
        TipoInvestimentoDeletePopupComponent
    ],
    entryComponents: [
        TipoInvestimentoComponent,
        TipoInvestimentoUpdateComponent,
        TipoInvestimentoDeleteDialogComponent,
        TipoInvestimentoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpTipoInvestimentoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
