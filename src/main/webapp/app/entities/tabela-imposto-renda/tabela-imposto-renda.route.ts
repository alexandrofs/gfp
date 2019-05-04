import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TabelaImpostoRenda } from 'app/shared/model/tabela-imposto-renda.model';
import { TabelaImpostoRendaService } from './tabela-imposto-renda.service';
import { TabelaImpostoRendaComponent } from './tabela-imposto-renda.component';
import { TabelaImpostoRendaDetailComponent } from './tabela-imposto-renda-detail.component';
import { TabelaImpostoRendaUpdateComponent } from './tabela-imposto-renda-update.component';
import { TabelaImpostoRendaDeletePopupComponent } from './tabela-imposto-renda-delete-dialog.component';
import { ITabelaImpostoRenda } from 'app/shared/model/tabela-imposto-renda.model';

@Injectable({ providedIn: 'root' })
export class TabelaImpostoRendaResolve implements Resolve<ITabelaImpostoRenda> {
    constructor(private service: TabelaImpostoRendaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITabelaImpostoRenda> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TabelaImpostoRenda>) => response.ok),
                map((tabelaImpostoRenda: HttpResponse<TabelaImpostoRenda>) => tabelaImpostoRenda.body)
            );
        }
        return of(new TabelaImpostoRenda());
    }
}

export const tabelaImpostoRendaRoute: Routes = [
    {
        path: '',
        component: TabelaImpostoRendaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tabelaImpostoRenda.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TabelaImpostoRendaDetailComponent,
        resolve: {
            tabelaImpostoRenda: TabelaImpostoRendaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tabelaImpostoRenda.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TabelaImpostoRendaUpdateComponent,
        resolve: {
            tabelaImpostoRenda: TabelaImpostoRendaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tabelaImpostoRenda.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TabelaImpostoRendaUpdateComponent,
        resolve: {
            tabelaImpostoRenda: TabelaImpostoRendaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tabelaImpostoRenda.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tabelaImpostoRendaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TabelaImpostoRendaDeletePopupComponent,
        resolve: {
            tabelaImpostoRenda: TabelaImpostoRendaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.tabelaImpostoRenda.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
