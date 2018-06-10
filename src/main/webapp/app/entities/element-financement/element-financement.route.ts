import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ElementFinancementComponent } from './element-financement.component';
import { ElementFinancementDetailComponent } from './element-financement-detail.component';
import { ElementFinancementPopupComponent } from './element-financement-dialog.component';
import { ElementFinancementDeletePopupComponent } from './element-financement-delete-dialog.component';

export const elementFinancementRoute: Routes = [
    {
        path: 'element-financement',
        component: ElementFinancementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.elementFinancement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'element-financement/:id',
        component: ElementFinancementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.elementFinancement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const elementFinancementPopupRoute: Routes = [
    {
        path: 'element-financement-new',
        component: ElementFinancementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.elementFinancement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'element-financement/:id/edit',
        component: ElementFinancementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.elementFinancement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'element-financement/:id/delete',
        component: ElementFinancementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'geFondApp.elementFinancement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
