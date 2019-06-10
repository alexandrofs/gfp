import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IndiceSerieDi } from 'app/shared/model/indice-serie-di.model';
import { IndiceSerieDiService } from './indice-serie-di.service';
import { IndiceSerieDiComponent } from './indice-serie-di.component';
import { IndiceSerieDiDetailComponent } from './indice-serie-di-detail.component';
import { IndiceSerieDiUpdateComponent } from './indice-serie-di-update.component';
import { IndiceSerieDiDeletePopupComponent } from './indice-serie-di-delete-dialog.component';
import { IIndiceSerieDi } from 'app/shared/model/indice-serie-di.model';

@Injectable({ providedIn: 'root' })
export class IndiceSerieDiResolve implements Resolve<IIndiceSerieDi> {
    constructor(private service: IndiceSerieDiService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIndiceSerieDi> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<IndiceSerieDi>) => response.ok),
                map((indiceSerieDi: HttpResponse<IndiceSerieDi>) => indiceSerieDi.body)
            );
        }
        return of(new IndiceSerieDi());
    }
}

export const indiceSerieDiRoute: Routes = [
    {
        path: '',
        component: IndiceSerieDiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.indiceSerieDi.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: IndiceSerieDiDetailComponent,
        resolve: {
            indiceSerieDi: IndiceSerieDiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.indiceSerieDi.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: IndiceSerieDiUpdateComponent,
        resolve: {
            indiceSerieDi: IndiceSerieDiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.indiceSerieDi.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: IndiceSerieDiUpdateComponent,
        resolve: {
            indiceSerieDi: IndiceSerieDiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.indiceSerieDi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const indiceSerieDiPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: IndiceSerieDiDeletePopupComponent,
        resolve: {
            indiceSerieDi: IndiceSerieDiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.indiceSerieDi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
