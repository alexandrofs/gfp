import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GfpSharedModule } from 'app/shared';
import {
    InstituicaoComponent,
    InstituicaoDetailComponent,
    InstituicaoUpdateComponent,
    InstituicaoDeletePopupComponent,
    InstituicaoDeleteDialogComponent,
    instituicaoRoute,
    instituicaoPopupRoute
} from './';

const ENTITY_STATES = [...instituicaoRoute, ...instituicaoPopupRoute];

@NgModule({
    imports: [GfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InstituicaoComponent,
        InstituicaoDetailComponent,
        InstituicaoUpdateComponent,
        InstituicaoDeleteDialogComponent,
        InstituicaoDeletePopupComponent
    ],
    entryComponents: [InstituicaoComponent, InstituicaoUpdateComponent, InstituicaoDeleteDialogComponent, InstituicaoDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpInstituicaoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
