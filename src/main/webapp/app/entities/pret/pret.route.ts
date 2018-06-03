import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PretComponent } from './pret.component';
import { PretDetailComponent } from './pret-detail.component';
import { PretPopupComponent } from './pret-dialog.component';
import { PretDeletePopupComponent } from './pret-delete-dialog.component';

export const pretRoute: Routes = [
    {
        path: 'pret',
        component: PretComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.pret.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pret/:id',
        component: PretDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.pret.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
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
