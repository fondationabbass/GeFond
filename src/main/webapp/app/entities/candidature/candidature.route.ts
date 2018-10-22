import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CandidatureComponent } from './candidature.component';
import { CandidaturePopupComponent } from './candidature-dialog.component';
import { CandidatureDeletePopupComponent } from './candidature-delete-dialog.component';
import { CandidatureWzDocumentComponent } from './wz/candidature-wz-document.component';
import { CandidatureWzProjetComponent } from './wz/candidature-wz-projet.component';
import { CandidatureWzResultComponent } from './wz/candidature-wz-result.component';
import { CandidatureWzComponent } from './wz/candidature-wz.component';
import { CandidatureWzVisiteComponent } from './wz/candidature-wz-visite.component';
import { CandidatureWzEntretienComponent } from './wz/candidature-wz-entretien.component';
import { CandidatureAggregateDetailComponent } from './candidature-aggregate-detail.component';
import { CandidatureWzGuardService } from './candidature-wz.guard';

@Injectable()
export class CandidatureResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) { }

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
        path: 'candidature-wz',
        component: CandidatureWzComponent,
        data: { authorities: ['ROLE_USER'], },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'candidature-wz-document',
        component: CandidatureWzDocumentComponent,
        data: { authorities: ['ROLE_USER'], },
        canActivate: [UserRouteAccessService, CandidatureWzGuardService]
    },
    {
        path: 'candidature-wz-projet',
        component: CandidatureWzProjetComponent,
        data: { authorities: ['ROLE_USER'], },
        canActivate: [UserRouteAccessService,
            CandidatureWzGuardService]
    },
    {
        path: 'candidature-wz-entretien',
        component: CandidatureWzEntretienComponent,
        data: { authorities: ['ROLE_USER'], },
        canActivate: [UserRouteAccessService,
            CandidatureWzGuardService]
    },
    {
        path: 'candidature-wz-visite',
        component: CandidatureWzVisiteComponent,
        data: { authorities: ['ROLE_USER'], },
        canActivate: [UserRouteAccessService, CandidatureWzGuardService]
    },
    {
        path: 'candidature-wz-result',
        component: CandidatureWzResultComponent,
        data: { authorities: ['ROLE_USER'], },
        canActivate: [UserRouteAccessService, CandidatureWzGuardService]
    },
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
    },
    {
        path: 'candidature/:id',
        component: CandidatureAggregateDetailComponent,
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
