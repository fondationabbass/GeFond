import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    ParametrageService,
    ParametragePopupService,
    ParametrageComponent,
    ParametrageDetailComponent,
    ParametrageDialogComponent,
    ParametragePopupComponent,
    ParametrageDeletePopupComponent,
    ParametrageDeleteDialogComponent,
    parametrageRoute,
    parametragePopupRoute,
} from './';

const ENTITY_STATES = [
    ...parametrageRoute,
    ...parametragePopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ParametrageComponent,
        ParametrageDetailComponent,
        ParametrageDialogComponent,
        ParametrageDeleteDialogComponent,
        ParametragePopupComponent,
        ParametrageDeletePopupComponent,
    ],
    entryComponents: [
        ParametrageComponent,
        ParametrageDialogComponent,
        ParametragePopupComponent,
        ParametrageDeleteDialogComponent,
        ParametrageDeletePopupComponent,
    ],
    providers: [
        ParametrageService,
        ParametragePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondParametrageModule {}
