import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IndiceSerieDi } from 'app/shared/model/indice-serie-di.model';
import { ImportPopupComponent } from 'app/shared/file-upload/import-dialog.component';

export const importPopupRoute: Routes = [
    {
        path: ':fileImporterType/import/:routeReturn',
        component: ImportPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.indiceSerieDi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
