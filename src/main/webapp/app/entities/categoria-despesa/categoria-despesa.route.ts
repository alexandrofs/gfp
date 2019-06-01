import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CategoriaDespesa } from 'app/shared/model/categoria-despesa.model';
import { CategoriaDespesaService } from './categoria-despesa.service';
import { CategoriaDespesaComponent } from './categoria-despesa.component';
import { CategoriaDespesaDetailComponent } from './categoria-despesa-detail.component';
import { CategoriaDespesaUpdateComponent } from './categoria-despesa-update.component';
import { CategoriaDespesaDeletePopupComponent } from './categoria-despesa-delete-dialog.component';
import { ICategoriaDespesa } from 'app/shared/model/categoria-despesa.model';

@Injectable({ providedIn: 'root' })
export class CategoriaDespesaResolve implements Resolve<ICategoriaDespesa> {
    constructor(private service: CategoriaDespesaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICategoriaDespesa> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CategoriaDespesa>) => response.ok),
                map((categoriaDespesa: HttpResponse<CategoriaDespesa>) => categoriaDespesa.body)
            );
        }
        return of(new CategoriaDespesa());
    }
}

export const categoriaDespesaRoute: Routes = [
    {
        path: '',
        component: CategoriaDespesaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.categoriaDespesa.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CategoriaDespesaDetailComponent,
        resolve: {
            categoriaDespesa: CategoriaDespesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.categoriaDespesa.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CategoriaDespesaUpdateComponent,
        resolve: {
            categoriaDespesa: CategoriaDespesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.categoriaDespesa.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CategoriaDespesaUpdateComponent,
        resolve: {
            categoriaDespesa: CategoriaDespesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.categoriaDespesa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categoriaDespesaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CategoriaDespesaDeletePopupComponent,
        resolve: {
            categoriaDespesa: CategoriaDespesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.categoriaDespesa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
