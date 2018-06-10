import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    CompteService,
    ComptePopupService,
    CompteComponent,
    CompteDetailComponent,
    CompteDialogComponent,
    ComptePopupComponent,
    CompteDeletePopupComponent,
    CompteDeleteDialogComponent,
    compteRoute,
    comptePopupRoute,
    CompteResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...compteRoute,
    ...comptePopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompteComponent,
        CompteDetailComponent,
        CompteDialogComponent,
        CompteDeleteDialogComponent,
        ComptePopupComponent,
        CompteDeletePopupComponent,
    ],
    entryComponents: [
        CompteComponent,
        CompteDialogComponent,
        ComptePopupComponent,
        CompteDeleteDialogComponent,
        CompteDeletePopupComponent,
    ],
    providers: [
        CompteService,
        ComptePopupService,
        CompteResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondCompteModule {}
