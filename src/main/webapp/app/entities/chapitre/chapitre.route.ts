import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ChapitreComponent } from './chapitre.component';
import { ChapitreDetailComponent } from './chapitre-detail.component';
import { ChapitrePopupComponent } from './chapitre-dialog.component';
import { ChapitreDeletePopupComponent } from './chapitre-delete-dialog.component';

@Injectable()
export class ChapitreResolvePagingParams implements Resolve<any> {

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

export const chapitreRoute: Routes = [
    {
        path: 'chapitre',
        component: ChapitreComponent,
        resolve: {
            'pagingParams': ChapitreResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.chapitre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'chapitre/:id',
        component: ChapitreDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.chapitre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chapitrePopupRoute: Routes = [
    {
        path: 'chapitre-new',
        component: ChapitrePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.chapitre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chapitre/:id/edit',
        component: ChapitrePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.chapitre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chapitre/:id/delete',
        component: ChapitreDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.chapitre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
