import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DocumentComponent } from './document.component';
import { DocumentDetailComponent } from './document-detail.component';
import { DocumentPopupComponent } from './document-dialog.component';
import { DocumentDeletePopupComponent } from './document-delete-dialog.component';

export const documentRoute: Routes = [
    {
        path: 'document',
        component: DocumentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.document.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'document/:id',
        component: DocumentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.document.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentPopupRoute: Routes = [
    {
        path: 'document-new',
        component: DocumentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.document.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document/:id/edit',
        component: DocumentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.document.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document/:id/delete',
        component: DocumentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.document.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
