import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    InvestimentoComponent,
    InvestimentoDetailComponent,
    InvestimentoUpdateComponent,
    InvestimentoDeletePopupComponent,
    InvestimentoDeleteDialogComponent,
    investimentoRoute,
    investimentoPopupRoute
} from './';

const ENTITY_STATES = [...investimentoRoute, ...investimentoPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InvestimentoComponent,
        InvestimentoDetailComponent,
        InvestimentoUpdateComponent,
        InvestimentoDeleteDialogComponent,
        InvestimentoDeletePopupComponent
    ],
    entryComponents: [
        InvestimentoComponent,
        InvestimentoUpdateComponent,
        InvestimentoDeleteDialogComponent,
        InvestimentoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpInvestimentoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
