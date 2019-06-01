import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LancamentoCartao } from 'app/shared/model/lancamento-cartao.model';
import { LancamentoCartaoService } from './lancamento-cartao.service';
import { LancamentoCartaoComponent } from './lancamento-cartao.component';
import { LancamentoCartaoDetailComponent } from './lancamento-cartao-detail.component';
import { LancamentoCartaoUpdateComponent } from './lancamento-cartao-update.component';
import { LancamentoCartaoDeletePopupComponent } from './lancamento-cartao-delete-dialog.component';
import { ILancamentoCartao } from 'app/shared/model/lancamento-cartao.model';

@Injectable({ providedIn: 'root' })
export class LancamentoCartaoResolve implements Resolve<ILancamentoCartao> {
    constructor(private service: LancamentoCartaoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILancamentoCartao> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LancamentoCartao>) => response.ok),
                map((lancamentoCartao: HttpResponse<LancamentoCartao>) => lancamentoCartao.body)
            );
        }
        return of(new LancamentoCartao());
    }
}

export const lancamentoCartaoRoute: Routes = [
    {
        path: '',
        component: LancamentoCartaoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'gfpApp.lancamentoCartao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LancamentoCartaoDetailComponent,
        resolve: {
            lancamentoCartao: LancamentoCartaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.lancamentoCartao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LancamentoCartaoUpdateComponent,
        resolve: {
            lancamentoCartao: LancamentoCartaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.lancamentoCartao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LancamentoCartaoUpdateComponent,
        resolve: {
            lancamentoCartao: LancamentoCartaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.lancamentoCartao.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lancamentoCartaoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LancamentoCartaoDeletePopupComponent,
        resolve: {
            lancamentoCartao: LancamentoCartaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gfpApp.lancamentoCartao.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
