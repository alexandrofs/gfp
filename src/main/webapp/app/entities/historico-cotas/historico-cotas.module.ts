import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    HistoricoCotasComponent,
    HistoricoCotasDetailComponent,
    HistoricoCotasUpdateComponent,
    HistoricoCotasDeletePopupComponent,
    HistoricoCotasDeleteDialogComponent,
    historicoCotasRoute,
    historicoCotasPopupRoute
} from './';

const ENTITY_STATES = [...historicoCotasRoute, ...historicoCotasPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HistoricoCotasComponent,
        HistoricoCotasDetailComponent,
        HistoricoCotasUpdateComponent,
        HistoricoCotasDeleteDialogComponent,
        HistoricoCotasDeletePopupComponent
    ],
    entryComponents: [
        HistoricoCotasComponent,
        HistoricoCotasUpdateComponent,
        HistoricoCotasDeleteDialogComponent,
        HistoricoCotasDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpHistoricoCotasModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
