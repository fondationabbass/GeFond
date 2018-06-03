import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ChapitreComponent } from './chapitre.component';
import { ChapitreDetailComponent } from './chapitre-detail.component';
import { ChapitrePopupComponent } from './chapitre-dialog.component';
import { ChapitreDeletePopupComponent } from './chapitre-delete-dialog.component';

export const chapitreRoute: Routes = [
    {
        path: 'chapitre',
        component: ChapitreComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.chapitre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'chapitre/:id',
        component: ChapitreDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.chapitre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chapitrePopupRoute: Routes = [
    {
        path: 'chapitre-new',
        component: ChapitrePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.chapitre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chapitre/:id/edit',
        component: ChapitrePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.chapitre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chapitre/:id/delete',
        component: ChapitreDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.chapitre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
