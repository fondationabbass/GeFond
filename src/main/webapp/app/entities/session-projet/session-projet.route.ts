import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SessionProjetComponent } from './session-projet.component';
import { SessionProjetDetailComponent } from './session-projet-detail.component';
import { SessionProjetPopupComponent } from './session-projet-dialog.component';
import { SessionProjetDeletePopupComponent } from './session-projet-delete-dialog.component';

export const sessionProjetRoute: Routes = [
    {
        path: 'session-projet',
        component: SessionProjetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.sessionProjet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'session-projet/:id',
        component: SessionProjetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.sessionProjet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sessionProjetPopupRoute: Routes = [
    {
        path: 'session-projet-new',
        component: SessionProjetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.sessionProjet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'session-projet/:id/edit',
        component: SessionProjetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.sessionProjet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'session-projet/:id/delete',
        component: SessionProjetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.sessionProjet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
