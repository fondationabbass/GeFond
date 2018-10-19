import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CandidatComponent } from './candidat.component';
import { CandidatDetailComponent } from './candidat-detail.component';
import { CandidatPopupComponent } from './candidat-dialog.component';
import { CandidatDeletePopupComponent } from './candidat-delete-dialog.component';
import { CandidatWzComponent } from './candidat-wz.component';
import { CandidatWzExpComponent } from './candidat-wz-exp.component';
import { CandidatWzResultComponent } from './candidat-wz-result.component';
import { CandidatWzGuardService } from './candidat-wz.guard';
import { CandidatAggregateDetailComponent } from './candidat-aggregate-detail.component';

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
        path: 'candidat-wz',
        component: CandidatWzComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'candidat-wz-exp',
        component: CandidatWzExpComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidat.home.title'
        },
        canActivate: [UserRouteAccessService, CandidatWzGuardService]
    }, {
        path: 'candidat-wz-result',
        component: CandidatWzResultComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.candidat.home.title'
        },
        canActivate: [UserRouteAccessService, CandidatWzGuardService]
    }, {
        path: 'candidat/:id',
        component: CandidatAggregateDetailComponent,
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
