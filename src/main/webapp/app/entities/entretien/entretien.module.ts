import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    EntretienService,
    EntretienPopupService,
    EntretienComponent,
    EntretienDetailComponent,
    EntretienDialogComponent,
    EntretienPopupComponent,
    EntretienDeletePopupComponent,
    EntretienDeleteDialogComponent,
    entretienRoute,
    entretienPopupRoute,
} from '.';

const ENTITY_STATES = [
    ...entretienRoute,
    ...entretienPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EntretienComponent,
        EntretienDetailComponent,
        EntretienDialogComponent,
        EntretienDeleteDialogComponent,
        EntretienPopupComponent,
        EntretienDeletePopupComponent,
    ],
    entryComponents: [
        EntretienComponent,
        EntretienDialogComponent,
        EntretienPopupComponent,
        EntretienDeleteDialogComponent,
        EntretienDeletePopupComponent,
    ],
    providers: [
        EntretienService,
        EntretienPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondEntretienModule {}
