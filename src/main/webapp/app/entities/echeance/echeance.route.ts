import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { EcheanceComponent } from './echeance.component';
import { EcheanceDetailComponent } from './echeance-detail.component';
import { EcheancePopupComponent } from './echeance-dialog.component';
import { EcheanceDeletePopupComponent } from './echeance-delete-dialog.component';

@Injectable()
export class EcheanceResolvePagingParams implements Resolve<any> {

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

export const echeanceRoute: Routes = [
    {
        path: 'echeance',
        component: EcheanceComponent,
        resolve: {
            'pagingParams': EcheanceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.echeance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'echeance/:id',
        component: EcheanceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.echeance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const echeancePopupRoute: Routes = [
    {
        path: 'echeance-new',
        component: EcheancePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.echeance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'echeance/:id/edit',
        component: EcheancePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.echeance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'echeance/:id/delete',
        component: EcheanceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.echeance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
