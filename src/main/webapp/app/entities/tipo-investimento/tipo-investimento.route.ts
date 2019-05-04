import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoInvestimento } from 'app/shared/model/tipo-investimento.model';
import { TipoInvestimentoService } from './tipo-investimento.service';
import { TipoInvestimentoComponent } from './tipo-investimento.component';
import { TipoInvestimentoDetailComponent } from './tipo-investimento-detail.component';
import { TipoInvestimentoUpdateComponent } from './tipo-investimento-update.component';
import { TipoInvestimentoDeletePopupComponent } from './tipo-investimento-delete-dialog.component';
import { ITipoInvestimento } from 'app/shared/model/tipo-investimento.model';

@Injectable({ providedIn: 'root' })
export class TipoInvestimentoResolve implements Resolve<ITipoInvestimento> {
    constructor(private service: TipoInvestimentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoInvestimento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TipoInvestimento>) => response.ok),
                map((tipoInvestimento: HttpResponse<TipoInvestimento>) => tipoInvestimento.body)
            );
        }
        return of(new TipoInvestimento());
    }
}

export const tipoInvestimentoRoute: Routes = [
    {
        path: '',
        component: TipoInvestimentoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tipoInvestimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TipoInvestimentoDetailComponent,
        resolve: {
            tipoInvestimento: TipoInvestimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tipoInvestimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TipoInvestimentoUpdateComponent,
        resolve: {
            tipoInvestimento: TipoInvestimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tipoInvestimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TipoInvestimentoUpdateComponent,
        resolve: {
            tipoInvestimento: TipoInvestimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tipoInvestimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoInvestimentoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TipoInvestimentoDeletePopupComponent,
        resolve: {
            tipoInvestimento: TipoInvestimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tipoInvestimento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
