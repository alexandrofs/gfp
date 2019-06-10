import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    IndiceSerieDiComponent,
    IndiceSerieDiDetailComponent,
    IndiceSerieDiUpdateComponent,
    IndiceSerieDiDeletePopupComponent,
    IndiceSerieDiDeleteDialogComponent,
    indiceSerieDiRoute,
    indiceSerieDiPopupRoute
} from './';

const ENTITY_STATES = [...indiceSerieDiRoute, ...indiceSerieDiPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IndiceSerieDiComponent,
        IndiceSerieDiDetailComponent,
        IndiceSerieDiUpdateComponent,
        IndiceSerieDiDeleteDialogComponent,
        IndiceSerieDiDeletePopupComponent
    ],
    entryComponents: [
        IndiceSerieDiComponent,
        IndiceSerieDiUpdateComponent,
        IndiceSerieDiDeleteDialogComponent,
        IndiceSerieDiDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpIndiceSerieDiModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
