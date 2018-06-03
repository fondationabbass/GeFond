import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CandidatComponent } from './candidat.component';
import { CandidatDetailComponent } from './candidat-detail.component';
import { CandidatPopupComponent } from './candidat-dialog.component';
import { CandidatDeletePopupComponent } from './candidat-delete-dialog.component';

export const candidatRoute: Routes = [
    {
        path: 'candidat',
        component: CandidatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'candidat/:id',
        component: CandidatDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candidatPopupRoute: Routes = [
    {
        path: 'candidat-new',
        component: CandidatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidat/:id/edit',
        component: CandidatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidat/:id/delete',
        component: CandidatDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
