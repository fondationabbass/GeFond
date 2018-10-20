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
import { CandidatureWzProjetComponent } from './wz/candidature-wz-projet.component';
import { CandidatureWzEntretienComponent } from './wz/candidature-wz-entretien.component';
import { CandidatureWzVisiteComponent } from './wz/candidature-wz-visite.component';
import { CandidatureWzDocumentComponent } from './wz/candidature-wz-document.component';
import { CandidatureWzResultComponent } from './wz/candidature-wz-result.component';
import { GeFondCandidatModule } from '../candidat/candidat.module';
import { GeFondDocumentModule } from '../document/document.module';
import { GeFondProjetModule } from '../projet/projet.module';
import { GeFondEntretienModule } from '../entretien/entretien.module';
import { GeFondVisiteModule } from '../visite/visite.module';
import { CandidatureWzComponent } from './wz/candidature-wz.component';
import { CandidatureWzGuardService } from './candidature-wz.guard';
import { CandidatureAggregateComponent } from './candidature-aggregate.component';
import { CandidatureWzNavbarComponent } from './wz/candidature-wz-navbar.component';
import { CandidatureAggregateDetailComponent } from './candidature-aggregate-detail.component';


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
        CandidatureAggregateComponent,
        CandidatureAggregateDetailComponent,
        CandidatureDetailComponent,
        CandidatureDialogComponent,
        CandidatureDeleteDialogComponent,
        CandidaturePopupComponent,
        CandidatureDeletePopupComponent,
        CandidatureWzComponent,
        CandidatureWzProjetComponent,
        CandidatureWzDocumentComponent,
        CandidatureWzEntretienComponent,
        CandidatureWzVisiteComponent,
        CandidatureWzResultComponent,
        CandidatureWzNavbarComponent
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
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondCandidatureModule {}
