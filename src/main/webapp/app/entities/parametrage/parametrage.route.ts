import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ParametrageComponent } from './parametrage.component';
import { ParametrageDetailComponent } from './parametrage-detail.component';
import { ParametragePopupComponent } from './parametrage-dialog.component';
import { ParametrageDeletePopupComponent } from './parametrage-delete-dialog.component';

export const parametrageRoute: Routes = [
    {
        path: 'parametrage',
        component: ParametrageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.parametrage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'parametrage/:id',
        component: ParametrageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.parametrage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parametragePopupRoute: Routes = [
    {
        path: 'parametrage-new',
        component: ParametragePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.parametrage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parametrage/:id/edit',
        component: ParametragePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.parametrage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parametrage/:id/delete',
        component: ParametrageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.parametrage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
