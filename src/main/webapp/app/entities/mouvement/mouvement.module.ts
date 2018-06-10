import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    MouvementService,
    MouvementPopupService,
    MouvementComponent,
    MouvementDetailComponent,
    MouvementDialogComponent,
    MouvementPopupComponent,
    MouvementDeletePopupComponent,
    MouvementDeleteDialogComponent,
    mouvementRoute,
    mouvementPopupRoute,
    MouvementResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mouvementRoute,
    ...mouvementPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MouvementComponent,
        MouvementDetailComponent,
        MouvementDialogComponent,
        MouvementDeleteDialogComponent,
        MouvementPopupComponent,
        MouvementDeletePopupComponent,
    ],
    entryComponents: [
        MouvementComponent,
        MouvementDialogComponent,
        MouvementPopupComponent,
        MouvementDeleteDialogComponent,
        MouvementDeletePopupComponent,
    ],
    providers: [
        MouvementService,
        MouvementPopupService,
        MouvementResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondMouvementModule {}
