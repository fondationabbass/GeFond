import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    EcheanceService,
    EcheancePopupService,
    EcheanceComponent,
    EcheanceDetailComponent,
    EcheanceDialogComponent,
    EcheancePopupComponent,
    EcheanceDeletePopupComponent,
    EcheanceDeleteDialogComponent,
    echeanceRoute,
    echeancePopupRoute,
    EcheanceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...echeanceRoute,
    ...echeancePopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EcheanceComponent,
        EcheanceDetailComponent,
        EcheanceDialogComponent,
        EcheanceDeleteDialogComponent,
        EcheancePopupComponent,
        EcheanceDeletePopupComponent,
    ],
    entryComponents: [
        EcheanceComponent,
        EcheanceDialogComponent,
        EcheancePopupComponent,
        EcheanceDeleteDialogComponent,
        EcheanceDeletePopupComponent,
    ],
    providers: [
        EcheanceService,
        EcheancePopupService,
        EcheanceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondEcheanceModule {}
