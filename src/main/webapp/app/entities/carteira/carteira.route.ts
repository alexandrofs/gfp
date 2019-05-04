import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Carteira } from 'app/shared/model/carteira.model';
import { CarteiraService } from './carteira.service';
import { CarteiraComponent } from './carteira.component';
import { CarteiraDetailComponent } from './carteira-detail.component';
import { CarteiraUpdateComponent } from './carteira-update.component';
import { CarteiraDeletePopupComponent } from './carteira-delete-dialog.component';
import { ICarteira } from 'app/shared/model/carteira.model';

@Injectable({ providedIn: 'root' })
export class CarteiraResolve implements Resolve<ICarteira> {
    constructor(private service: CarteiraService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICarteira> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Carteira>) => response.ok),
                map((carteira: HttpResponse<Carteira>) => carteira.body)
            );
        }
        return of(new Carteira());
    }
}

export const carteiraRoute: Routes = [
    {
        path: '',
        component: CarteiraComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.carteira.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CarteiraDetailComponent,
        resolve: {
            carteira: CarteiraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.carteira.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CarteiraUpdateComponent,
        resolve: {
            carteira: CarteiraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.carteira.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CarteiraUpdateComponent,
        resolve: {
            carteira: CarteiraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.carteira.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const carteiraPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CarteiraDeletePopupComponent,
        resolve: {
            carteira: CarteiraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.carteira.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
