import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HistoricoCotas } from 'app/shared/model/historico-cotas.model';
import { HistoricoCotasService } from './historico-cotas.service';
import { HistoricoCotasComponent } from './historico-cotas.component';
import { HistoricoCotasDetailComponent } from './historico-cotas-detail.component';
import { HistoricoCotasUpdateComponent } from './historico-cotas-update.component';
import { HistoricoCotasDeletePopupComponent } from './historico-cotas-delete-dialog.component';
import { IHistoricoCotas } from 'app/shared/model/historico-cotas.model';

@Injectable({ providedIn: 'root' })
export class HistoricoCotasResolve implements Resolve<IHistoricoCotas> {
    constructor(private service: HistoricoCotasService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHistoricoCotas> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HistoricoCotas>) => response.ok),
                map((historicoCotas: HttpResponse<HistoricoCotas>) => historicoCotas.body)
            );
        }
        return of(new HistoricoCotas());
    }
}

export const historicoCotasRoute: Routes = [
    {
        path: '',
        component: HistoricoCotasComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.historicoCotas.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HistoricoCotasDetailComponent,
        resolve: {
            historicoCotas: HistoricoCotasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.historicoCotas.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HistoricoCotasUpdateComponent,
        resolve: {
            historicoCotas: HistoricoCotasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.historicoCotas.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HistoricoCotasUpdateComponent,
        resolve: {
            historicoCotas: HistoricoCotasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.historicoCotas.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const historicoCotasPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HistoricoCotasDeletePopupComponent,
        resolve: {
            historicoCotas: HistoricoCotasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.historicoCotas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
