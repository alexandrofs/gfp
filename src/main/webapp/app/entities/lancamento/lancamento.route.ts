import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Lancamento } from 'app/shared/model/lancamento.model';
import { LancamentoService } from './lancamento.service';
import { LancamentoComponent } from './lancamento.component';
import { LancamentoDetailComponent } from './lancamento-detail.component';
import { LancamentoUpdateComponent } from './lancamento-update.component';
import { LancamentoDeletePopupComponent } from './lancamento-delete-dialog.component';
import { ILancamento } from 'app/shared/model/lancamento.model';
import { ContaPagamentoService } from '../conta-pagamento/conta-pagamento.service';
import { IContaPagamento } from 'app/shared/model/conta-pagamento.model';
import { ContaPagamento } from 'app/shared/model/conta-pagamento.model';

@Injectable({ providedIn: 'root' })
export class LancamentoResolve implements Resolve<ILancamento> {
    constructor(private service: LancamentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILancamento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Lancamento>) => response.ok),
                map((lancamento: HttpResponse<Lancamento>) => lancamento.body)
            );
        }
        return of(new Lancamento());
    }
}

@Injectable({ providedIn: 'root' })
export class ContaPagamentoResolve implements Resolve<IContaPagamento> {
    constructor(private service: ContaPagamentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContaPagamento> {
        const id = route.params['contaPagamentoId'] ? route.params['contaPagamentoId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContaPagamento>) => response.ok),
                map((contaPagamento: HttpResponse<ContaPagamento>) => contaPagamento.body)
            );
        }
        return of(new ContaPagamento());
    }
}

export const lancamentoRoute: Routes = [
    {
        path: '',
        component: LancamentoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':contaPagamentoId/lancamentos',
        component: LancamentoComponent,
        resolve: {
            contaPagamento: ContaPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LancamentoDetailComponent,
        resolve: {
            lancamento: LancamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':contaPagamentoId/new',
        component: LancamentoUpdateComponent,
        resolve: {
            lancamento: LancamentoResolve,
            contaPagamento: ContaPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LancamentoUpdateComponent,
        resolve: {
            lancamento: LancamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lancamentoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LancamentoDeletePopupComponent,
        resolve: {
            lancamento: LancamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
