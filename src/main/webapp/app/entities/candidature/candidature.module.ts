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
import { CandWzFormDataService} from './wz/cand-wz-form-data.service';
import { CandidatureWzProjetComponent } from './wz/candidature-wz-projet.component';
import { CandidatureWzDocumentComponent } from './wz/candidature-wz-document.component';
import { CandidatureWzResultComponent } from './wz/candidature-wz-result.component';
import { GeFondCandidatModule } from '../candidat/candidat.module';
import { GeFondDocumentModule } from '../document/document.module';
import { GeFondProjetModule } from '../projet/projet.module';
import { GeFondEntretienModule } from '../entretien/entretien.module';
import { GeFondVisiteModule } from '../visite/visite.module';
import { CandidatureWzComponent } from './candidature-wz.component';
import { CandidatureWzGuardService } from './candidature-wz.guard';


const ENTITY_STATES = [
    ...candidatureRoute,
    ...candidaturePopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        GeFondCandidatModule,
        GeFondDocumentModule,
        GeFondProjetModule,
        GeFondEntretienModule,
        GeFondVisiteModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CandidatureComponent,
        CandidatureDetailComponent,
        CandidatureDialogComponent,
        CandidatureDeleteDialogComponent,
        CandidaturePopupComponent,
        CandidatureDeletePopupComponent,
        CandidatureWzComponent,
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
        CandidatureWzGuardService,
        CandidaturePopupService,
        CandidatureResolvePagingParams,
        CandWzFormDataService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondCandidatureModule {}
