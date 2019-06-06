import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Despesa } from 'app/shared/model/despesa.model';
import { DespesaService } from './despesa.service';
import { DespesaComponent } from './despesa.component';
import { DespesaDetailComponent } from './despesa-detail.component';
import { DespesaUpdateComponent } from './despesa-update.component';
import { DespesaDeletePopupComponent } from './despesa-delete-dialog.component';
import { IDespesa } from 'app/shared/model/despesa.model';

@Injectable({ providedIn: 'root' })
export class DespesaResolve implements Resolve<IDespesa> {
    constructor(private service: DespesaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDespesa> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Despesa>) => response.ok),
                map((despesa: HttpResponse<Despesa>) => despesa.body)
            );
        }
        return of(new Despesa());
    }
}

export const despesaRoute: Routes = [
    {
        path: '',
        component: DespesaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.despesa.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DespesaDetailComponent,
        resolve: {
            despesa: DespesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.despesa.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DespesaUpdateComponent,
        resolve: {
            despesa: DespesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.despesa.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DespesaUpdateComponent,
        resolve: {
            despesa: DespesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.despesa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const despesaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DespesaDeletePopupComponent,
        resolve: {
            despesa: DespesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.despesa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
