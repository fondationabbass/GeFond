import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ExperienceCandidatComponent } from './experience-candidat.component';
import { ExperienceCandidatDetailComponent } from './experience-candidat-detail.component';
import { ExperienceCandidatPopupComponent } from './experience-candidat-dialog.component';
import { ExperienceCandidatDeletePopupComponent } from './experience-candidat-delete-dialog.component';

export const experienceCandidatRoute: Routes = [
    {
        path: 'experience-candidat',
        component: ExperienceCandidatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.experienceCandidat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'experience-candidat/:id',
        component: ExperienceCandidatDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.experienceCandidat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const experienceCandidatPopupRoute: Routes = [
    {
        path: 'experience-candidat-new',
        component: ExperienceCandidatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.experienceCandidat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'experience-candidat/:id/edit',
        component: ExperienceCandidatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.experienceCandidat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'experience-candidat/:id/delete',
        component: ExperienceCandidatDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.experienceCandidat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
