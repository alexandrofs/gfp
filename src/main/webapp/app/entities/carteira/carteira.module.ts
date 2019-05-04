import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    CarteiraComponent,
    CarteiraDetailComponent,
    CarteiraUpdateComponent,
    CarteiraDeletePopupComponent,
    CarteiraDeleteDialogComponent,
    carteiraRoute,
    carteiraPopupRoute
} from './';

const ENTITY_STATES = [...carteiraRoute, ...carteiraPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CarteiraComponent,
        CarteiraDetailComponent,
        CarteiraUpdateComponent,
        CarteiraDeleteDialogComponent,
        CarteiraDeletePopupComponent
    ],
    entryComponents: [CarteiraComponent, CarteiraUpdateComponent, CarteiraDeleteDialogComponent, CarteiraDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpCarteiraModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
