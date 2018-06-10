import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    PretService,
    PretPopupService,
    PretComponent,
    PretDetailComponent,
    PretDialogComponent,
    PretPopupComponent,
    PretDeletePopupComponent,
    PretDeleteDialogComponent,
    pretRoute,
    pretPopupRoute,
    PretResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pretRoute,
    ...pretPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PretComponent,
        PretDetailComponent,
        PretDialogComponent,
        PretDeleteDialogComponent,
        PretPopupComponent,
        PretDeletePopupComponent,
    ],
    entryComponents: [
        PretComponent,
        PretDialogComponent,
        PretPopupComponent,
        PretDeleteDialogComponent,
        PretDeletePopupComponent,
    ],
    providers: [
        PretService,
        PretPopupService,
        PretResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondPretModule {}
