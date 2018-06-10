import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { GarantieComponent } from './garantie.component';
import { GarantieDetailComponent } from './garantie-detail.component';
import { GarantiePopupComponent } from './garantie-dialog.component';
import { GarantieDeletePopupComponent } from './garantie-delete-dialog.component';

export const garantieRoute: Routes = [
    {
        path: 'garantie',
        component: GarantieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.garantie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'garantie/:id',
        component: GarantieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.garantie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const garantiePopupRoute: Routes = [
    {
        path: 'garantie-new',
        component: GarantiePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.garantie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'garantie/:id/edit',
        component: GarantiePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.garantie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'garantie/:id/delete',
        component: GarantieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.garantie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
