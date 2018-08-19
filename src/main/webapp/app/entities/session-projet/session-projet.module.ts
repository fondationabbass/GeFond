import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    SessionProjetService,
    SessionProjetPopupService,
    SessionProjetComponent,
    SessionProjetDetailComponent,
    SessionProjetDialogComponent,
    SessionProjetPopupComponent,
    SessionProjetDeletePopupComponent,
    SessionProjetDeleteDialogComponent,
    sessionProjetRoute,
    sessionProjetPopupRoute,
} from '.';

const ENTITY_STATES = [
    ...sessionProjetRoute,
    ...sessionProjetPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SessionProjetComponent,
        SessionProjetDetailComponent,
        SessionProjetDialogComponent,
        SessionProjetDeleteDialogComponent,
        SessionProjetPopupComponent,
        SessionProjetDeletePopupComponent,
    ],
    entryComponents: [
        SessionProjetComponent,
        SessionProjetDialogComponent,
        SessionProjetPopupComponent,
        SessionProjetDeleteDialogComponent,
        SessionProjetDeletePopupComponent,
    ],
    providers: [
        SessionProjetService,
        SessionProjetPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondSessionProjetModule {}
