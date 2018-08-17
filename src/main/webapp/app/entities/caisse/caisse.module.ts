import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import { GeFondAdminModule } from '../../admin/admin.module';
import {
    CaisseService,
    CaissePopupService,
    CaisseComponent,
    CaisseDetailComponent,
    CaisseDialogComponent,
    CaissePopupComponent,
    CaisseDeletePopupComponent,
    CaisseDeleteDialogComponent,
    caisseRoute,
    caissePopupRoute,
    CaisseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...caisseRoute,
    ...caissePopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        GeFondAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CaisseComponent,
        CaisseDetailComponent,
        CaisseDialogComponent,
        CaisseDeleteDialogComponent,
        CaissePopupComponent,
        CaisseDeletePopupComponent,
    ],
    entryComponents: [
        CaisseComponent,
        CaisseDialogComponent,
        CaissePopupComponent,
        CaisseDeleteDialogComponent,
        CaisseDeletePopupComponent,
    ],
    providers: [
        CaisseService,
        CaissePopupService,
        CaisseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondCaisseModule {}
