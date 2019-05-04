import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Investimento } from 'app/shared/model/investimento.model';
import { InvestimentoService } from './investimento.service';
import { InvestimentoComponent } from './investimento.component';
import { InvestimentoDetailComponent } from './investimento-detail.component';
import { InvestimentoUpdateComponent } from './investimento-update.component';
import { InvestimentoDeletePopupComponent } from './investimento-delete-dialog.component';
import { IInvestimento } from 'app/shared/model/investimento.model';

@Injectable({ providedIn: 'root' })
export class InvestimentoResolve implements Resolve<IInvestimento> {
    constructor(private service: InvestimentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInvestimento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Investimento>) => response.ok),
                map((investimento: HttpResponse<Investimento>) => investimento.body)
            );
        }
        return of(new Investimento());
    }
}

export const investimentoRoute: Routes = [
    {
        path: '',
        component: InvestimentoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.investimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InvestimentoDetailComponent,
        resolve: {
            investimento: InvestimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.investimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: InvestimentoUpdateComponent,
        resolve: {
            investimento: InvestimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.investimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: InvestimentoUpdateComponent,
        resolve: {
            investimento: InvestimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.investimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const investimentoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: InvestimentoDeletePopupComponent,
        resolve: {
            investimento: InvestimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.investimento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
