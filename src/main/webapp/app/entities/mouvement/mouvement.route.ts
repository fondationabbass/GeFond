import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MouvementComponent } from './mouvement.component';
import { MouvementDetailComponent } from './mouvement-detail.component';
import { MouvementPopupComponent } from './mouvement-dialog.component';
import { MouvementDeletePopupComponent } from './mouvement-delete-dialog.component';

@Injectable()
export class MouvementResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const mouvementRoute: Routes = [
    {
        path: 'mouvement',
        component: MouvementComponent,
        resolve: {
            'pagingParams': MouvementResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.mouvement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mouvement/:id',
        component: MouvementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.mouvement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mouvementPopupRoute: Routes = [
    {
        path: 'mouvement-new',
        component: MouvementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.mouvement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mouvement/:id/edit',
        component: MouvementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.mouvement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mouvement/:id/delete',
        component: MouvementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.mouvement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
