import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PretComponent } from './pret.component';
import { PretDetailComponent } from './pret-detail.component';
import { PretPopupComponent } from './pret-dialog.component';
import { PretDeletePopupComponent } from './pret-delete-dialog.component';
import { PretCreateComponent} from './pret-create.component';
import { PretWzComponent } from './pret-wz.component';
import { PretWzEcheanceComponent } from './pret-wz-echeance.component';
import { PretWzResultComponent } from './pret-wz-result.component';
import { PretWzWorkflowGuard } from './pret-wz-workflow-guard.service';
import { PretWzElementFinancementComponent } from './pret-wz-element-financement.component';
import { PretWzGarantieComponent } from './pret-wz-garantie.component';

@Injectable()
export class PretResolvePagingParams implements Resolve<any> {

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

export const pretRoute: Routes = [
    {
        path: 'pret',
        component: PretComponent,
        resolve: {
            'pagingParams': PretResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.pret.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pretcreate',
        component: PretCreateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.pret.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pret/:id',
        component: PretDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.pret.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
     { path: 'pret-wz',  component: PretWzComponent, data: {authorities: ['ROLE_USER']} ,canActivate: [UserRouteAccessService] },
     { path: 'pret-wz-echeance',  component: PretWzEcheanceComponent, data: {authorities: ['ROLE_USER']}, canActivate: [UserRouteAccessService, PretWzWorkflowGuard] },
     { path: 'pret-wz-element-financement',  component: PretWzElementFinancementComponent, data: {authorities: ['ROLE_USER']}, canActivate: [UserRouteAccessService, PretWzWorkflowGuard] },
     { path: 'pret-wz-garantie',  component: PretWzGarantieComponent, data: {authorities: ['ROLE_USER']}, canActivate: [UserRouteAccessService, PretWzWorkflowGuard] },
     { path: 'pret-wz-result',  component: PretWzResultComponent, data: {authorities: ['ROLE_USER']}, canActivate: [UserRouteAccessService, PretWzWorkflowGuard] }
     //,{ path: '',   redirectTo: '/pretwz', pathMatch: 'full' }

];

export const pretPopupRoute: Routes = [
    {
        path: 'pret-new',
        component: PretPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.pret.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pret/:id/edit',
        component: PretPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.pret.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pret/:id/delete',
        component: PretDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.pret.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
