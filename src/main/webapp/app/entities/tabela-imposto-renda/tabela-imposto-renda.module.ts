import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    TabelaImpostoRendaComponent,
    TabelaImpostoRendaDetailComponent,
    TabelaImpostoRendaUpdateComponent,
    TabelaImpostoRendaDeletePopupComponent,
    TabelaImpostoRendaDeleteDialogComponent,
    tabelaImpostoRendaRoute,
    tabelaImpostoRendaPopupRoute
} from './';

const ENTITY_STATES = [...tabelaImpostoRendaRoute, ...tabelaImpostoRendaPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TabelaImpostoRendaComponent,
        TabelaImpostoRendaDetailComponent,
        TabelaImpostoRendaUpdateComponent,
        TabelaImpostoRendaDeleteDialogComponent,
        TabelaImpostoRendaDeletePopupComponent
    ],
    entryComponents: [
        TabelaImpostoRendaComponent,
        TabelaImpostoRendaUpdateComponent,
        TabelaImpostoRendaDeleteDialogComponent,
        TabelaImpostoRendaDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpTabelaImpostoRendaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
