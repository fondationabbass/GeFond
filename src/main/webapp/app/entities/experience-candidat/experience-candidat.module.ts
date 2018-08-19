import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    ExperienceCandidatService,
    ExperienceCandidatPopupService,
    ExperienceCandidatComponent,
    ExperienceCandidatDetailComponent,
    ExperienceCandidatDialogComponent,
    ExperienceCandidatPopupComponent,
    ExperienceCandidatDeletePopupComponent,
    ExperienceCandidatDeleteDialogComponent,
    experienceCandidatRoute,
    experienceCandidatPopupRoute,
} from '.';

const ENTITY_STATES = [
    ...experienceCandidatRoute,
    ...experienceCandidatPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ExperienceCandidatComponent,
        ExperienceCandidatDetailComponent,
        ExperienceCandidatDialogComponent,
        ExperienceCandidatDeleteDialogComponent,
        ExperienceCandidatPopupComponent,
        ExperienceCandidatDeletePopupComponent,
    ],
    entryComponents: [
        ExperienceCandidatComponent,
        ExperienceCandidatDialogComponent,
        ExperienceCandidatPopupComponent,
        ExperienceCandidatDeleteDialogComponent,
        ExperienceCandidatDeletePopupComponent,
    ],
    providers: [
        ExperienceCandidatService,
        ExperienceCandidatPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondExperienceCandidatModule {}
