import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';
import { TipoImpostoRendaService } from './tipo-imposto-renda.service';
import { TipoImpostoRendaComponent } from './tipo-imposto-renda.component';
import { TipoImpostoRendaDetailComponent } from './tipo-imposto-renda-detail.component';
import { TipoImpostoRendaUpdateComponent } from './tipo-imposto-renda-update.component';
import { TipoImpostoRendaDeletePopupComponent } from './tipo-imposto-renda-delete-dialog.component';
import { ITipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';

@Injectable({ providedIn: 'root' })
export class TipoImpostoRendaResolve implements Resolve<ITipoImpostoRenda> {
    constructor(private service: TipoImpostoRendaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoImpostoRenda> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TipoImpostoRenda>) => response.ok),
                map((tipoImpostoRenda: HttpResponse<TipoImpostoRenda>) => tipoImpostoRenda.body)
            );
        }
        return of(new TipoImpostoRenda());
    }
}

export const tipoImpostoRendaRoute: Routes = [
    {
        path: '',
        component: TipoImpostoRendaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tipoImpostoRenda.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TipoImpostoRendaDetailComponent,
        resolve: {
            tipoImpostoRenda: TipoImpostoRendaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tipoImpostoRenda.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TipoImpostoRendaUpdateComponent,
        resolve: {
            tipoImpostoRenda: TipoImpostoRendaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tipoImpostoRenda.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TipoImpostoRendaUpdateComponent,
        resolve: {
            tipoImpostoRenda: TipoImpostoRendaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tipoImpostoRenda.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoImpostoRendaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TipoImpostoRendaDeletePopupComponent,
        resolve: {
            tipoImpostoRenda: TipoImpostoRendaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tipoImpostoRenda.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
