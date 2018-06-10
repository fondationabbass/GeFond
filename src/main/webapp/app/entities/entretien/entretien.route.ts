import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EntretienComponent } from './entretien.component';
import { EntretienDetailComponent } from './entretien-detail.component';
import { EntretienPopupComponent } from './entretien-dialog.component';
import { EntretienDeletePopupComponent } from './entretien-delete-dialog.component';

export const entretienRoute: Routes = [
    {
        path: 'entretien',
        component: EntretienComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.entretien.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'entretien/:id',
        component: EntretienDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.entretien.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entretienPopupRoute: Routes = [
    {
        path: 'entretien-new',
        component: EntretienPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.entretien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entretien/:id/edit',
        component: EntretienPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.entretien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entretien/:id/delete',
        component: EntretienDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.entretien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
