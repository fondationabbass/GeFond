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
import { CandidatWzComponent } from './candidat-wz.component';
import { CandidatEditComponent } from './candidat-edit.component';
import { CandidatWzService } from './candidat-wz.service';
import { CandidatWzExpComponent } from './candidat-wz-exp.component';
import { GeFondExperienceCandidatModule } from '../experience-candidat/experience-candidat.module';
import { CandidatWzNavbarComponent } from './candidat-wz-navbar.component';
import { CandidatWzResultComponent } from './candidat-wz-result.component';
import { CandidatWzGuardService } from './candidat-wz.guard';
import { CandidatAggregateComponent } from './candidat-aggregate.component';
import { CandidatAggregateDetailComponent } from './candidat-aggregate-detail.component';
//import { CandidatureWzService } from '../candidature/candidature-wz.b.service';

const ENTITY_STATES = [
    ...candidatRoute,
    ...candidatPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        GeFondExperienceCandidatModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    exports: [
        CandidatDetailComponent
    ],
    declarations: [
        CandidatComponent,
        CandidatAggregateComponent,
        CandidatAggregateDetailComponent,
        CandidatWzNavbarComponent,
        CandidatWzComponent,
        CandidatWzExpComponent,
        CandidatWzResultComponent,
        CandidatEditComponent,
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
        CandidatWzService,
        CandidatWzGuardService,
        CandidatPopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondCandidatModule {}
