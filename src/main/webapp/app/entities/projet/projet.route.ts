import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ProjetComponent } from './projet.component';
import { ProjetDetailComponent } from './projet-detail.component';
import { ProjetPopupComponent } from './projet-dialog.component';
import { ProjetDeletePopupComponent } from './projet-delete-dialog.component';

@Injectable()
export class ProjetResolvePagingParams implements Resolve<any> {

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

export const projetRoute: Routes = [
    {
        path: 'projet',
        component: ProjetComponent,
        resolve: {
            'pagingParams': ProjetResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.projet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'projet/:id',
        component: ProjetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.projet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projetPopupRoute: Routes = [
    {
        path: 'projet-new',
        component: ProjetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.projet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'projet/:id/edit',
        component: ProjetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.projet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'projet/:id/delete',
        component: ProjetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.projet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
