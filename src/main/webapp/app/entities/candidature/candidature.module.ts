import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    CandidatureService,
    CandidaturePopupService,
    CandidatureComponent,
    CandidatureDetailComponent,
    CandidatureDialogComponent,
    CandidaturePopupComponent,
    CandidatureDeletePopupComponent,
    CandidatureDeleteDialogComponent,
    candidatureRoute,
    candidaturePopupRoute,
    CandidatureResolvePagingParams,
} from '.';

const ENTITY_STATES = [
    ...candidatureRoute,
    ...candidaturePopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CandidatureComponent,
        CandidatureDetailComponent,
        CandidatureDialogComponent,
        CandidatureDeleteDialogComponent,
        CandidaturePopupComponent,
        CandidatureDeletePopupComponent,
    ],
    entryComponents: [
        CandidatureComponent,
        CandidatureDialogComponent,
        CandidaturePopupComponent,
        CandidatureDeleteDialogComponent,
        CandidatureDeletePopupComponent,
    ],
    providers: [
        CandidatureService,
        CandidaturePopupService,
        CandidatureResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondCandidatureModule {}
