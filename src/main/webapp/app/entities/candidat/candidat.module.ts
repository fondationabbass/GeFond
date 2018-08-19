import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    CandidatService,
    CandidatPopupService,
    CandidatComponent,
    CandidatDetailComponent,
    CandidatDialogComponent,
    CandidatPopupComponent,
    CandidatDeletePopupComponent,
    CandidatDeleteDialogComponent,
    candidatRoute,
    candidatPopupRoute,
} from '.';

const ENTITY_STATES = [
    ...candidatRoute,
    ...candidatPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CandidatComponent,
        CandidatDetailComponent,
        CandidatDialogComponent,
        CandidatDeleteDialogComponent,
        CandidatPopupComponent,
        CandidatDeletePopupComponent,
    ],
    entryComponents: [
        CandidatComponent,
        CandidatDialogComponent,
        CandidatPopupComponent,
        CandidatDeleteDialogComponent,
        CandidatDeletePopupComponent,
    ],
    providers: [
        CandidatService,
        CandidatPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondCandidatModule {}
