import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    GarantieService,
    GarantiePopupService,
    GarantieComponent,
    GarantieDetailComponent,
    GarantieDialogComponent,
    GarantiePopupComponent,
    GarantieDeletePopupComponent,
    GarantieDeleteDialogComponent,
    garantieRoute,
    garantiePopupRoute,
} from './';

const ENTITY_STATES = [
    ...garantieRoute,
    ...garantiePopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GarantieComponent,
        GarantieDetailComponent,
        GarantieDialogComponent,
        GarantieDeleteDialogComponent,
        GarantiePopupComponent,
        GarantieDeletePopupComponent,
    ],
    entryComponents: [
        GarantieComponent,
        GarantieDialogComponent,
        GarantiePopupComponent,
        GarantieDeleteDialogComponent,
        GarantieDeletePopupComponent,
    ],
    providers: [
        GarantieService,
        GarantiePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondGarantieModule {}
