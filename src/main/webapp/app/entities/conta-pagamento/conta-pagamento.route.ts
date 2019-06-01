import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { ContaPagamentoService } from './conta-pagamento.service';
import { ContaPagamentoComponent } from './conta-pagamento.component';
import { ContaPagamentoDetailComponent } from './conta-pagamento-detail.component';
import { ContaPagamentoUpdateComponent } from './conta-pagamento-update.component';
import { ContaPagamentoDeletePopupComponent } from './conta-pagamento-delete-dialog.component';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';

@Injectable({ providedIn: 'root' })
export class ContaPagamentoResolve implements Resolve<IContaPagamento> {
    constructor(private service: ContaPagamentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContaPagamento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContaPagamento>) => response.ok),
                map((contaPagamento: HttpResponse<ContaPagamento>) => contaPagamento.body)
            );
        }
        return of(new ContaPagamento());
    }
}

export const contaPagamentoRoute: Routes = [
    {
        path: '',
        component: ContaPagamentoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.contaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ContaPagamentoDetailComponent,
        resolve: {
            contaPagamento: ContaPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.contaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ContaPagamentoUpdateComponent,
        resolve: {
            contaPagamento: ContaPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.contaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ContaPagamentoUpdateComponent,
        resolve: {
            contaPagamento: ContaPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.contaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contaPagamentoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ContaPagamentoDeletePopupComponent,
        resolve: {
            contaPagamento: ContaPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.contaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
