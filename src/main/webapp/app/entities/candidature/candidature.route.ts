import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CandidatureComponent } from './candidature.component';
import { CandidatureDetailComponent } from './candidature-detail.component';
import { CandidaturePopupComponent } from './candidature-dialog.component';
import { CandidatureDeletePopupComponent } from './candidature-delete-dialog.component';

@Injectable()
export class CandidatureResolvePagingParams implements Resolve<any> {

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

export const candidatureRoute: Routes = [
    {
        path: 'candidature',
        component: CandidatureComponent,
        resolve: {
            'pagingParams': CandidatureResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'candidature/:id',
        component: CandidatureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candidaturePopupRoute: Routes = [
    {
        path: 'candidature-new',
        component: CandidaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidature/:id/edit',
        component: CandidaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidature/:id/delete',
        component: CandidatureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
