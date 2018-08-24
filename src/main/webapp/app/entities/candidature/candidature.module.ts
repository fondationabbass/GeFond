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
} from './';

import { CandidatureWzCandidatComponent } from './candidature-wz-candidat.component';
import { CandWzFormDataService} from './cand-wz-form-data.service';
import { CandidatureWzCandidatureComponent } from './candidature-wz-candidature.component';
import { CandidatureWzExpComponent } from './candidature-wz-exp.component';
import { CandidatureWzProjetComponent } from './candidature-wz-projet.component';
import { CandidatureWzDocumentComponent } from './candidature-wz-document.component';
import { CandidatureWzResultComponent } from './candidature-wz-result.component';


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
        CandidatureWzCandidatComponent,
        CandidatureWzCandidatureComponent,
        CandidatureWzExpComponent,
        CandidatureWzProjetComponent,
        CandidatureWzDocumentComponent,
        CandidatureWzResultComponent,
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
        CandWzFormDataService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondCandidatureModule {}
